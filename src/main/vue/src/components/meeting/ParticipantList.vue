<script setup>
import UserProfile from "@/components/meeting/UserProfile.vue";
import Participant from "@/components/meeting/Participant.vue";
import { useMeetingDetailStore } from "@/stores/meetingDetailStore";
import { computed, ref } from 'vue';

const meetingDetailStore = useMeetingDetailStore();

let isOpen = ref(false);

let clickedParticipantId = ref(null);

// 참여자를 클릭하였을때 참여자 정보 모달을 뒤워주는 함수
function handleModal(event){
      isOpen.value = true;
      clickedParticipantId.value = event.currentTarget.getAttribute('data-id');
      console.log(event.target);
}

const participantNum = computed(() => {
  // 데이터에서 불러온 참여자가 없을 시 0을 리턴
  if (!meetingDetailStore.participants === undefined ||
    !meetingDetailStore.participants) return 0;
  return meetingDetailStore.participants.length;
})

function closeModal(){
  isOpen.value = false;
}
</script>

<template>
  <section class="participants">
    <h2 class="participant__num">참가자 {{ participantNum }} / {{ meetingDetailStore.maxMember }}</h2>
    <ul class="participant__list">
      <li
        v-for="(p, idx) in meetingDetailStore.participants"
        :key="p.participantId"
        :data-id="p.participantId" @click.prevent="handleModal"
      >
        <participant :userDetail="p">
        </participant>
      </li>
    </ul>
  </section>
  <Teleport to="#app">
    <Transition name="slide-fade">
      <UserProfile @closeModal="closeModal" v-if="isOpen" :userDetail="meetingDetailStore.participants" :participantId="clickedParticipantId" :isModalOpened="isOpen"/>
    </Transition>
  </Teleport>
</template>
<style scoped>
.participants {
  display: flex;
  flex-direction: column;
  margin-top: 11px;
}

.participant__num {
  font-weight: 600;
  font-size: 16px;
  color: var(--dark-grey2);
}

@media (min-width: 576px) {
  .participant__num {
    font-size: 18px;
  }
}

.participant__list {
  margin-top: 1rem;
  display: grid;
  grid-auto-rows: 46px;
  row-gap: 8px;
  font-weight: 500;
  font-size: 16px;
}

@media (min-width: 576px) {
  .participant__list {
    display: grid;
    grid-auto-rows: 52px;
    grid-template-columns: repeat(2, 240px);
    row-gap: 16px;
    column-gap: 10px;
  }
}

@media (min-width: 792px) {
  .participant__list {
    grid-template-columns: repeat(3, 240px);
  }
}
</style>
