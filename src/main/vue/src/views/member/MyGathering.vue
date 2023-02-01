<script setup>
import { reactive} from "vue";
import api from "@/api";
import MeetingList from "@/components/member/MeetingList.vue";
import ModalChanged from "@/components/modal/Changed.vue";
const state = reactive({
  meetings: [],
});


async function getMyGathering() {
  try {
    console.log(api.member.getMyGathering());
    const res = await api.member.getMyGathering();
    const data =  await res.json();
    state.meetings = data;
  }
  catch(e){
            console.log(e);
          }
}
getMyGathering();
</script>

<template>
  <div class="content-wrap">
    <div class="title-box">
    <span>🖐🏻</span>
  <span class="title">내가 모집한 모임</span>
  </div>
 
  <section class="meetings">
      <ul>
        <li v-for="(meeting, idx) in state.meetings" :key="idx">
          <MeetingList :meeting="meeting" />
        </li>
      </ul>
    </section>
  </div>
</template>
<style scoped>


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

.title-box{
  padding-top: 25px;
    padding-left: 15px;
    display: flex;
    width: fit-content;
    align-items: center;
    justify-content: center;
    padding-bottom: 20px;
}


.title{
    padding-left:1px;
    font-size: 17px;
    font-weight: var(--bold);
}
@media (min-width: 576px ) {

  .title-box{
    padding-top: 50px;
  }
  
}
     
@media (min-width: 768px) {
  .meetings > ul {
  gap: 24px;
  }
  .content-wrap{
    align-items: center;
  }
  .title-box {
    display: flex;
    align-items: center;
    padding-top: 100px;
    padding-bottom: 50px;
    font-size: 20px;
    }
    .title{
      font-size: 20px;
    }

}

@media (min-width:1200px){
  .my-meeting__title{
        padding-top: 74px;
        padding-bottom: 56px;
        font-size: 20px;
    }

    .content-wrap{
      align-items: flex-start;

     
    }

    .title-box{
      font-size: 24px;
      padding-top: 150px;
    }

    .title{
      font-size: 24px;
    }
}


</style>