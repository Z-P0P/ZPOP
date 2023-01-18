
<!-- detail vue = 화면 / article 화면의 구성 요소중 한 부분-->
<script setup>
import { reactive, computed, ref  } from "vue";
import api from "@/api";
import MeetingList from "@/components/member/MeetingList.vue";
import ModalDefault from "@/components/modal/Full.vue";

const state = reactive({
  meetings: [],
  meetingId: null,
  participants: [],
  userId : null,
  participantsId: [],
  //참여인원
  // rateValue: [],


  // isResultNone: computed(() => {
  //   return state.meetings.length === 0;
  // }),
  isInput: computed((changeColor) => {

  })

});

const emit = defineEmits([
  'rate'
]);


let modalOn = ref(false);

function showModal() {
  modalOn.value = true;
}

function closeMyModal() {
  modalOn.value = false;
}


async function getMyMeeting() {
  try {
    const res = await api.member.getMyMeeting();
    const data = await res.json();
    state.meetings = data;
    state.userId = state.meetings[0].participantId;

    if(state.userId == null) {
      console.log("교집합을 만들어주세요");
      console.log("예외던지기");
    }
  }
  catch (e) {
    console.log(e);
  }
}
getMyMeeting();




/***
 * 얻게된 meeetingId를 가지고 서버에 API 정보를 요청하는 함수
 */
async function getParticipant(meetingId) {
  try {
    const res = await api.member.getParticipant(state.meetingId);
    const data = await res.json();
    for (const p of data) {
      if(state.userId === p.participantId)
      continue;
      p.rateValue=50;
      state.participants.push(p);
      // console.log(p);
    }
    // state.participants = data;
    // console.log(data);
    //data for문
    
    return state.participants;
  }
  catch (e) {
    console.log(e);
  }
}


/***
 * 평가하기 버튼을 클릭했을때 state의 click값을 meetingId로 변경해주는 함수
 * 모달창 OPEN 해주어야함
 */
async function updateIdByClick(id) {
  state.clicked = id
  return await getParticipant(state.clicked);
  console.log(state.participants);



}


async function rateHandeler(id) {
  state.meetingId = id;
  await getParticipant(state.meetingId);
  console.log(state.participants);
  console.log(state.participants.length + "길이");

  showModal()
  return state.participants;
//   console.log("clicked from parent");
//  getParticipant(id);
//  console.log( getParticipant(id));

//   return getParticipant(id);
  
  // isProxy(await getParticipant(id)) ? 'yup' : 'nope'
  // const rawObjectOrArray = toRaw(await getParticipant(id))
  // console.log(rawObjectOrArray);
  // console.log(rawObjectOrArray[0].id);
  // showModal(rawObjectOrArray);
  
}

/***
 * 
  Given
  사용자가 내가 참여한 모임에서 평가하기 버튼을 눌렀을 경우

  When
  서버에서 평가자에 대한 데이터를 가져와서 평가하기 모달창에 뿌려준다. 
  사용자는 해당 데이터에 대해서 평가를 마친다

  Then
  모임에 참여한 참여자들의 인기도가 업데이트 된다.
  평가하기 버튼이 평가완료 버튼으로 닫힌다.
 * 
 */
function changeColor(e) {
  // state.participants.rateValue = e.target.value;
  let targetIdx = e.target.getAttribute("idx");
  // state.participants[targetIdx].push(e.target.value);
  // console.log(e.target.getAttribute("idx"));
  // console.log(e.currentTarget);
  // console.log(e.target);
  // console.log(state.participants.rateValue);
  let value = e.target.value;
  let color ='linear-gradient(90deg, rgb(98, 179, 185)' + value + '% , rgb(235, 235, 235)' + value + '%)';
  e.target.style.background = color;

  // state.participants[targetIdx].push(value);
  // console.log(state.participants[targetIdx]);
  console.log(state.participants[targetIdx].rateValue = value);
  // console.log(state.participants[targetIdx].rateValue.push(value));
  // console.log(state.participants[targetIdx].rateValue);
  // console.log(state.participants[targetIdx].rateValue);

  state.participants[targetIdx].rateValue = value;

}

function rateMeeting(e){
  console.log(e);
  console.log("done");
  console.log(state.participants);
  
}

</script>

<template>
  <ModalDefault v-if="modalOn" @closeModal="closeMyModal"><template #modal-body>
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
          <input :idx="idx" type="range" step="50" min="0" max="100" @input.prevent="changeColor">
        </div>
        </li>
      </ul>
      </div>
    </template>
    <template #modal-footer><div class="btn-box">
        <div class="btn btn-semiround" @click.prevent="rateMeeting">완료</div>
      </div> </template>
  </ModalDefault>


  <div class="content-wrap">
    <div class="title-box">
      <span>🤝🏻</span>
      <span class="title">내가 참여한 모임</span>
    </div>

    <section class="meetings">
      <ul>
        <li v-for="(meeting, idx) in state.meetings" :key="idx">
          <MeetingList @rate="rateHandeler" :meeting="meeting" />
        </li>
      </ul>
    </section>
  </div>
</template>
<style scoped>
@import url(../../assets/css/member/rate.css);

.rate-container>li{
  display: flex;
  justify-content: center;
}

.meetings>ul {
  display: grid;
  grid-template-columns: repeat(1, 1fr);
  gap: 0px;
}

@media (min-width: 768px) {
  .meetings>ul {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (min-width: 1200px) {
  .meetings>ul {
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
  .meetings>ul {
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

@media (min-width:1200px) {
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