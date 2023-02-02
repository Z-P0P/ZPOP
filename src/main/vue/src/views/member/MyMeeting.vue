<script setup>
import { reactive, computed, ref, onMounted } from "vue";
import { useMemberStore } from "@/stores/memberStore";
import api from "@/api";
import MeetingList from "@/components/member/MeetingList.vue";
import ModalRate from "@/components/modal/Full.vue";
import ModalChanged from "@/components/modal/Changed.vue";

const state = reactive({
  meetings: [],
  meetingId: null,
  participants: [],
  userId: null,
  participantsId: [],
});

const user = useMemberStore();
const nickname = user.nickname;
const emit = defineEmits(["rate"]);
const memberStore = useMemberStore();

let rateModalOn = ref(false);
let errModalOn = ref(false);
function showRateModal() {
  rateModalOn.value = true;
}

function closeRateModal() {
  state.participants = [];
  rateModalOn.value = false;
}

function showRateErr() {
  errModalOn.value = true;
}

/***
 * 회원이 참여한 모임의 모든 정보 요청
 */
async function getMyMeeting() {
  try {
    const res = await api.member.getMyMeeting();
    const data = await res.json();
    state.meetings = data;
    if (data == null) {
   // TODO : 참여한 모임이 하나도 없을경우 "아직 교집합이 없어요- 멘트 "
    }
  } catch (e) {
    console.log(e);
  }
}
getMyMeeting();

/***
 * meeetingId를 가지고 해당모임 참여자정보 요청
 */
async function getParticipant(meetingId) {
  try {
    const res = await api.member.getParticipant(state.meetingId);
    return await res.json();
  } catch (e) {
    console.log(e);
  }
}

/**
 * state에서 현재 클릭한 미팅을 찾는다
 */
 function findCurrentClickedMeeting() {
  return state.meetings.find((m) => m.meetingId === state.meetingId);
}

/**
 *
 * @param {*} id
 * 평가하기 버튼을 누르면, 해당 모임에 참여한 참여자의 정보를 반환해준다.
 * 평가하기 모달을 띄워준다.
 */
async function rateHandler(id) {
  state.meetingId = id;
  const participants = await getParticipant(state.meetingId);

  // 참여자가 없다면, 평가 완료로 바꾼 후 참여자 없음 에러 모달을 띄운다
  if (isExistsOnlyHost(participants)) {
    const meeting = findCurrentClickedMeeting();
    meeting.evaluated = true;
    errModalOn.value = true;
    /* TODO: 서버 hasEvaluated = true로 변경 */
    return;
  }

  // 평가 bar를 위한 세팅
  for (const p of participants) {
    if (memberStore.id === p.participantId) {
      continue;
    }
    p.rateValue = 50;
    state.participants.push(p);
  }

  showRateModal();
}

/**
 * 다른 참여자 없이 host만 존재하는지 확인한다
 */
function isExistsOnlyHost(participants) {
  if (participants.length === 1 && participants[0].participantId === memberStore.id) { 
    return true;
  }
  return false;
}

/***
 * 사용자의 평가하기input값이 바뀌면, 
 * 동적으로 input 태그 값 + 배경색 변경
 */
function changeValue(e) {
  let targetIdx = e.target.getAttribute("idx");
  let value = e.target.value;
  let color =
    "linear-gradient(90deg, rgb(98, 179, 185)" +
    value +
    "% , rgb(235, 235, 235)" +
    value +
    "%)";
  e.target.style.background = color;
  state.participants[targetIdx].rateValue = value;
}

/**
 * meetingId를 받아서 해당 모임에 참여한 회원들의 인기도를 평가한다.
 * @param {*} meetingId
 */
function rateMeeting(meetingId) {
  let id = state.meetingId;
  //사용자가 평가한 값을 담을 배열
  let rateValue = [];
  //평가 대상자가 담길 배열
  let evaluateeId = [];
  //평가한 값과, 대상자가 짝을이뤄 evals에 담기게된다.
  let evals = [];

  for (const s of state.participants) {
    let result = 0;
    if (parseInt(s.rateValue) === 0) result = -1;
    if (parseInt(s.rateValue) === 50) result = 1;
    if (parseInt(s.rateValue) === 100) result = 3;

    rateValue.push(result);
    evaluateeId.push(s.participantId);
  }
  for (let i = 0; i < rateValue.length; i++) {
    let evaldata = {
      evaluateeId: evaluateeId[i],
      result: rateValue[i],
    };
    evals.push(evaldata);
  }
  let rateList = {
    meetingId: id,
    evals: evals,
  };

  fetch("/api/rate", {
    method: "POST",
    mode: "cors",
    cache: "no-cache",
    credentials: "same-origin",
    headers: {
      "Content-Type": "application/json",
    },
    redirect: "follow",
    referrer: "no-referrer",
    body: JSON.stringify(rateList),
  })
    .then((response) => response.ok)
    .then((data) => {
      const m = findCurrentClickedMeeting();
      m.evaluated = true;
    })
    .then(
      closeRateModal()
    );
}


</script>

<template>
  <ModalChanged v-if="errModalOn">
    <template #modal-body>
      <p>{{ user.nickname }}님 😖</p>
      <span class="confirm"
        >주최한 모임에 참여자가 없어<span>평가할 수 없어요.</span>
      </span>
    </template>
    <template #modal-footer>
      <div @click="errModalOn = false">닫기</div>
    </template>
  </ModalChanged>

  <ModalRate v-if="rateModalOn" @closeModal="closeRateModal"
    ><template #modal-body>
      <div class="rate-container">
        <h1 class="rate__title">{{ state.participants[0].title }}</h1>
        <div class="rate__emoji-list">
          <div class="rate__emoji">
            <span class="icon icon-bad"> bad</span>
            <p>인기도 -1</p>
          </div>

          <div class="rate__emoji">
            <span class="icon icon-good"> good</span>
            <p>인기도 +1</p>
          </div>

          <div class="rate__emoji">
            <span class="icon icon-excellent"> excellent</span>
            <p>인기도 +3</p>
          </div>
        </div>

        <ul>
          <li v-for="(participant, idx) in state.participants" :key="idx">
            {{ participant.nickname }}
            <div>
              <input
                :idx="idx"
                type="range"
                step="50"
                min="0"
                max="100"
                @input.prevent="changeValue"
              />
            </div>
          </li>
        </ul>
        <div class="btn-box">
        <div class="btn btn-semiround" @click.prevent="rateMeeting">완료</div>
      </div>
      </div>
     
    </template>
  </ModalRate>

  <div class="content-wrap">
    <div class="title-box">
      <span>🤝🏻</span>
      <span class="title">내가 참여한 모임</span>
    </div>

    <section class="meetings">
      <ul>
        <li v-for="(meeting, idx) in state.meetings" :key="idx">
          <MeetingList @rate="rateHandler" :meeting="meeting" />
        </li>
      </ul>
    </section>
  </div>
</template>
<style scoped>
@import url(../../assets/css/member/rate.css);
.rate-container > li {
  display: flex;
  justify-content: center;
}
.meetings > ul {
  display: grid;
  grid-template-columns: repeat(1, 1fr);
  gap: 0px;
}
@media (min-width: 768px) {
  .meetings > ul {
    grid-template-columns: repeat(2, 1fr);
  }
}
@media (min-width: 1200px) {
  .meetings > ul {
    grid-template-columns: repeat(3, 1fr);
  }
}
.content-wrap {
  max-width: 1200px;
  display: flex;
  flex-direction: column;
}
.title-box {
  padding-top: 25px;
  padding-left: 15px;
  display: flex;
  width: fit-content;
  align-items: center;
  justify-content: center;
  padding-bottom: 20px;
}
.title {
  padding-left: 1px;
  font-size: 17px;
  font-weight: var(--bold);
}
@media (min-width: 576px) {
  .title-box {
    padding-top: 50px;
  }
}
@media (min-width: 768px) {
  .meetings > ul {
    gap: 24px;
  }
  .content-wrap {
    align-items: center;
  }
  .title-box {
    display: flex;
    align-items: center;
    padding-top: 100px;
    padding-bottom: 50px;
    font-size: 20px;
  }
  .title {
    font-size: 20px;
  }
}
@media (min-width: 1200px) {
  .my-meeting__title {
    padding-top: 74px;
    padding-bottom: 56px;
    font-size: 20px;
  }
  .content-wrap {
    align-items: flex-start;
  }
  .title-box {
    font-size: 24px;
    padding-top: 150px;
  }
  .title {
    font-size: 24px;
  }
}
</style>
