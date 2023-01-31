<script setup>
import MemberProfile from "@/components/participant/MemberProfile.vue";
import Participant from "@/components/participant/Participant.vue";
import { useMeetingDetailStore } from "@/stores/meetingDetailStore";
import { useMemberStore } from "@/stores/memberStore";
import { useLoginModalStore } from "@/stores/loginModalStore";
import { computed, ref } from "vue";
import { useRoute } from "vue-router";
import DefaultModal from "../modal/Default.vue";
import api from "@/api";
import { ServerException } from "@/utils/ServerException";

const route = useRoute();

const meetingDetailStore = useMeetingDetailStore();
const memberStore = useMemberStore();
const loginModalStore = useLoginModalStore();

const participantNum = computed(() => {
  // 데이터에서 불러온 참여자가 없을 시 0을 리턴
  if (!meetingDetailStore.participants === undefined || !meetingDetailStore.participants)
    return 0;
  return meetingDetailStore.participants.length;
});

const profileModalOn = ref(false);
const clickedParticipantId = ref(null);

// 참여자를 클릭하였을때 참여자 정보 모달 ON
async function showProfileModal(participantId) {
  // 로그인해야 사용자 프로필 확인 가능
  if (!(await memberStore.isAuthenticated())) {
    loginModalStore.show();
    return;
  }
  clickedParticipantId.value = participantId;
  profileModalOn.value = true;
}

function closeProfileModal() {
  profileModalOn.value = false;
}

// 내보내기 ----------------------------------------------------------------------
const kickModalOn = ref(false);
const kickMember = ref({});
const kickStep = ref(1); // 1: 확인, 2: 결과
const kickResultMsg = ref("");

function onKickModal(member) {
  kickStep.value = 1;
  kickMember.value.id = member.id;
  kickMember.value.nickname = member.nickname;
  kickModalOn.value = true;
}

function closeKickModal() {
  kickStep.value = 1;
  kickModalOn.value = false;
}

async function kick() {
  try {
    const res = await api.meeting.kick(route.params.id, kickMember.value.id);
    if (!res.ok) throw new ServerException(await res.json());
    kickResultMsg.value = "모임에서 내보냈어요";
    kickStep.value = 2;
    meetingDetailStore.removeParticipant(kickMember.value.id);
  } catch (e) {
    if (e.res.message === "이미 강퇴한 참여자입니다") kickResultMsg.value = e.res.message;
    else if (e.res.status === 400) kickResultMsg.value = "잘못된 요청이에요";
    else if (e.res.message === "참여하지 않은 모임입니다")
      kickResultMsg.value = "참여하지 않은 사용자에요";
    else if (e.res.status === 403) kickResultMsg.value = "권한이 없어요";
    kickStep.value = 2;
  }
}
</script>

<template>
  <section class="participants">
    <h2 class="participant__num">
      참가자 {{ participantNum }} / {{ meetingDetailStore.maxMember }}
    </h2>
    <ul class="participant__list">
      <li v-for="(p, idx) in meetingDetailStore.participants" :key="p.participantId">
        <participant :userDetail="p" @onClickParticipant="showProfileModal">
        </participant>
      </li>
    </ul>
  </section>
  <Teleport to="#app">
    <MemberProfile
      v-if="profileModalOn"
      @closeModal="closeProfileModal"
      @onKickModal="onKickModal"
      :memberId="clickedParticipantId"
    />
    <DefaultModal v-if="kickModalOn">
      <template #modal-body v-if="kickStep === 1">
        <p>{{ kickMember.nickname }}님을</p>
        <p>정말로 내보내시겠어요?</p>
      </template>
      <template #modal-footer v-if="kickStep === 1">
        <div @click="closeKickModal">아니오</div>
        <div @click="kick" class="yes">예</div>
      </template>
      <template #modal-body v-if="kickStep === 2">
        <p>{{ kickResultMsg }}</p>
      </template>
      <template #modal-footer v-if="kickStep === 2">
        <div @click="closeKickModal">닫기</div>
      </template>
    </DefaultModal>
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

:deep(.modal__body div) {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

:deep(.modal__footer) {
  border-top: 1px solid var(--light-grey1);
}

:deep(.modal__footer div) {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  padding: 16px 8px;
  cursor: pointer;
}

:deep(.modal__footer div:hover) {
  background-color: var(--light-grey1);
}
</style>
