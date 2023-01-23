<script setup>
import UserProfile from "@/components/meeting/UserProfile.vue";
import Participant from "@/components/meeting/Participant.vue";
import { computed, defineProps, ref } from 'vue';

const props = defineProps({
  detail: Object
})

let isOpen = ref(false);

let clickedParticipantId = ref(null);

// 참여자를 클릭하였을때 참여자 정보 모달을 뒤워주는 함수
function handleModal(event){
  console.log(event.target);
      isOpen.value = true;
      clickedParticipantId.value = event.currentTarget.getAttribute('data-id');
      console.log(event.target);
}

const participantNum = computed(() => {
  // 데이터에서 불러온 참여자가 없을 시 0을 리턴
  if (props.detail.participants === undefined) return 0;
  const length = props.detail.participants.length;
  return length;
})

function closeModal(){
  isOpen.value = false;
}
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
              <Participant :userDetail="participant" :data-id="participant.participantId" @click.prevent="handleModal"/>
            </li>
        </ul>
        <Transition name="slide-fade">
          <UserProfile @closeModal="closeModal" v-if="isOpen" :userDetail="detail" :participantId="clickedParticipantId" :isModalOpened="isOpen"/>
        </Transition>
    </section>
</template>

<style>
.slide-fade-enter-active {
  transition: all 0.3s ease-out;
}

.slide-fade-leave-active {
  transition: all 0.8s cubic-bezier(1, 0.5, 0.8, 1);
}

.slide-fade-enter-from,
.slide-fade-leave-to {
  transform: translateY(20px);
  opacity: 0;
}

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