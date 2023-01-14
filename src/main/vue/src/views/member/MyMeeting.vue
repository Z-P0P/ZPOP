
<!-- detail vue = 화면 / article 화면의 구성 요소중 한 부분-->
<script setup>
import { reactive, computed } from "vue";
import api from "@/api";
import MeetingList from "@/components/member/MeetingList.vue";

const state = reactive({
  meetings: [],
  // isResultNone: computed(() => {
  //   return state.meetings.length === 0;
  // }),
});

async function getMyMeeting() {
  try {
    console.log(api.member.getMyMeeting());
    const res = await api.member.getMyMeeting();
    const data =  await res.json();
    state.meetings = data;
    console.log(state.meetings);
  }
  catch(e){
            console.log(e);
          }
}
getMyMeeting();
</script>

<template>
    <div class="title-box">
    <span>🤝🏻</span>
  <span class="title">내가 참여한 모임</span>
  </div>
 
  <section class="meetings">
      <ul>
        <li v-for="(meeting, idx) in state.meetings" :key="idx">
          <MeetingList :meeting="meeting" />
        </li>
      </ul>
    </section>
</template>
<style scoped>


.title-box{
  padding: 20px;
    display: flex;
    width: fit-content;
    
    min-width: 368px;
    justify-content: flex-start;
    margin-top: 20px;
    padding-bottom: 25px;
}


.title{
    padding-left:1px;
    font-size: 17px;
    font-weight: var(--bold);
}

     
@media (min-width: 768px) {


}

@media (min-width:1200px){

}

</style>