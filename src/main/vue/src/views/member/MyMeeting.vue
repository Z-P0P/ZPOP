
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


/***
 * 회원이 참여한 모임의 모든 정보를 요청하는 함수
 */
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
    }
    
    return state.participants;
  }
  catch (e) {
    console.log(e);
  }
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


 /**
  * 
  * @param {*} id 
  * 평가하기 버튼을 누르면, 해당 모임에 참여한 참여자의 정보를 반환해준다.
  * 평가하기 모달을 띄워준다.
  */
async function rateHandeler(id) {
  state.meetingId = id;
  await getParticipant(state.meetingId);
  showModal()
  return state.participants;
}

 /***
  * 사용자의 input값이 바뀌면, 동적으로 input 태그 값이 변경됨과 동시에 배경이 바뀐다.
  */
function changeValue(e) {
  let targetIdx = e.target.getAttribute("idx");
  let value = e.target.value;
  let color ='linear-gradient(90deg, rgb(98, 179, 185)' + value + '% , rgb(235, 235, 235)' + value + '%)';
  e.target.style.background = color;
  state.participants[targetIdx].rateValue = value;
}

/**
 * meetingId를 받아서 해당 모임에 참여한 회원들의 인기도를 평가한다.
 * @param {*} meetingId 
 */
function rateMeeting(meetingId){
  let id = state.meetingId ;
  //사용자가 평가한 값을 담을 배열
  let rateValue = [];
  //평가 대상자가 담길 배열
  let evaluateeId = [];
  //평가한 값과, 대상자가 짝을이뤄 evals에 담기게된다.
  let evals =[];
  
  for(const s of state.participants) {
     let result = 0;
                      if (parseInt(s.rateValue) === 0) result = -1;
                      if (parseInt(s.rateValue) === 50) result = 1;
                      if (parseInt(s.rateValue) === 100) result = 3;
                
    rateValue.push(result);
    evaluateeId.push(s.participantId);

  }
  for (let i = 0; i < rateValue.length; i++){
                      let evaldata = {
                          evaluateeId : evaluateeId[i],
                          result : rateValue[i],
                      }
                      evals.push(evaldata);
                  }
  let rateList = {
      "meetingId" : id,
      "evals" : evals,
  };
  fetch("/api/rate", {
                        method: 'POST',
                        mode: 'cors',
                        cache: 'no-cache',
                        credentials: 'same-origin',
                        headers: {
                            'Content-Type': 'application/json',
                        },
                        redirect: 'follow',
                        referrer: 'no-referrer',
                        body: JSON.stringify(rateList)

                    }).then((response) => response.ok)
                      .then((data) => {
                        if(data==true) {
                        }
                      });             
  
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
          <input :idx="idx" type="range" step="50" min="0" max="100" @input.prevent="changeValue">
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