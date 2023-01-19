<script setup>
import { setMapStoreSuffix } from 'pinia';
import { computed,defineProps,ref } from 'vue';
import UserProfile from "@/components/meeting/UserProfile.vue";

const props = defineProps({
  detail: Object
})

let isOpen = ref(false);

let clickedParticipantId = ref(null);

// 참여자를 클릭하였을때 참여자 정보 모달을 뒤워주는 함수
function handleModal(event){
      isOpen.value = true;
      clickedParticipantId.value = event.target.getAttribute('data-id');
}

const participantNum = computed(() => {
  // 데이터에서 불러온 참여자가 없을 시 0을 리턴
  if (props.detail.participants === undefined) return 0;
  const length = props.detail.participants.length;
  return length;
})

</script>

<template>
    <section class="participant">
        <div class="participant__num">
            <span>참가자</span>
            <span id="participant-count">{{ participantNum }}</span>
            <span>/</span>
            <span>{{ props.detail.maxMember }}</span>
            <span class="icon pointer icon-arrow-up"></span>
            <span class="icon pointer hidden icon-arrow-down"></span>
        </div>
        <ul class="participant__list">
            <li v-for="(participant, idx) in detail.participants" :key = "participant.participantId">
              <div class="participant__info">
                <img src="/images/icon/user-profile-grey.svg" v-bind:data-id="participant.participantId" @click.prevent="handleModal">
                <span v-bind:data-id="participant.participantId" @click.prevent="handleModal">{{ participant.nickname }}</span>
              </div>
            </li>
        </ul>
        <UserProfile v-if="isOpen" :userDetail="detail" :participantId="clickedParticipantId"/>
    </section>
</template>

<style>

.main {
    margin: 2rem auto 4rem auto;
    
    max-width: 792px;
}

.main > section {
    margin: 1rem 20px;
}

.participant {
  display: flex;
  flex-direction: column;
  font-size: 16px;
}

.participant__num {
  display: flex;
}

.participant__num > span {
  margin-right: .5rem;
  font-weight: bold;
}

.participant__list {
  margin-top: 1rem;
  display: grid;
  grid-auto-rows: 46px;
  row-gap: 8px;
}

.participant__info {
  width: 220px;
  height: 46px;
  border-radius: 1.625rem;
  display: flex; 
  flex-grow: 0;
  cursor: pointer;
  align-items: center;
  padding-left: 6px;
}

.participant__info:hover{
  background-color: var(--main-color);
  color: var(--white);
}

.participant__info > img{
  width: 36px;
  height: 36px;
  margin-right: 15px;
}

@media (min-width:576px){
  .participant__list {
    display: grid;
    grid-auto-rows: 52px;
    grid-template-columns: repeat(2, 240px);
    row-gap: 16px;
    column-gap: 10px;
  }

  .participant {
    font-size: 18px;
    font-weight: 500;
  }
  
  .participant__info {
    width: 240px;
    height: 52px;
  }
  
  .participant__info > img{
    width: 42px;
    height: 42px;
  }

  .participant__num {
    font-size: 18px;
  }

  .main{
        margin: 5rem auto;
  }

  .main > section {
        margin: 2rem 20px;
  }
}

@media (min-width:792px){
  .participant__list {
    grid-template-columns: repeat(3, 240px);
  }
}

</style>