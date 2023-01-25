<script setup>
import { ref } from "vue";
import { useRoute } from "vue-router";
import api from "@/api";
import { useMemberStore } from "@/stores/memberStore";
import { useMeetingDetailStore } from "@/stores/meetingDetailStore";
import ModalDefault from "@/components/modal/Default.vue";
import { ServerException } from "@/utils/ServerException";

const meetingDetailStore = useMeetingDetailStore();
const memberStore = useMemberStore();

const route = useRoute();

const props = defineProps({
  controlType: String,
});

const emit = defineEmits(["closeModal"]);

let confirmMsg = ref("");

async function onClickYes() {
  switch (props.controlType) {
    case "참여":
      await participate();
      break;
    case "참여취소":
      await leave();
      break;
    case "마감":
      await close();
      break;
  }
}

async function participate() {
  try {
    const res = await api.meeting.participate(route.params.id);
    if (!res.ok) throw new ServerException(res);
    const data = await res.json();
  
    confirmMsg.value = data.contact;
    closeModalFooterType();
    meetingDetailStore.hasParticipated = true;

    // 참여자 리스트 최신화
    const participantsResult = await api.meeting.getParticipant(route.params.id);
    if (!participantsResult.ok) throw new ServerException(res);
    const participants = await participantsResult.json();
    meetingDetailStore.participants = participants;
  } catch (e) {
    console.log(e)
  }
}

async function leave() {
  try {
    const res = await api.meeting.leave(route.params.id);
    if (!res.ok) throw new ServerException(res);
    const data = await res.json();

    confirmMsg.value = "모임 참여를 취소했어요 !";
    closeModalFooterType();
    meetingDetailStore.removeParticipant(memberStore.id);
    meetingDetailStore.hasParticipated = false;
  } catch (e) {
    console.log(e)
  }
}

async function close() {
  try {
    const res = await api.meeting.close(route.params.id);
    if (!res.ok) throw new ServerException(res);
    const data = await res.json();

    confirmMsg.value = "모임을 마감했어요 !";
    closeModalFooterType();
    meetingDetailStore.closed = true;
  } catch (e) {}
}

let modalFooterType = ref(0);

function closeModalFooterType() {
  modalFooterType.value = 1;
}
</script>

<template>
  <ModalDefault @closeModal="emit('closeModal')">
    <template v-if="props.controlType === '참여'" #modal-body>
      <div v-if="!confirmMsg">
        <p>🤝 {{ meetingDetailStore.title }}</p>
        <p class="confirm">모임에 참여하시겠어요?</p>
      </div>
      <div v-else>
        <p>모임에 참여했습니다!</p>
        <p>다음 링크로 모임원들에게 인사해요! 👋</p>
        <p class="confirm">{{ confirmMsg }}</p>
      </div>
    </template>
    <template v-else-if="props.controlType === '참여취소'" #modal-body>
      <div v-if="!confirmMsg">
        <p>🤝 {{ meetingDetailStore.title }}</p>
        <p class="confirm">참여를 취소하시겠어요?</p>
      </div>
      <div v-else>
        <p>🤝 {{ meetingDetailStore.title }}</p>
        <p class="confirm">{{ confirmMsg }}</p>
      </div>
    </template>
    <template v-else-if="props.controlType === '마감'" #modal-body>
      <div v-if="!confirmMsg">
        <p class="confirm">모임을 마감하시겠어요?</p>
      </div>
      <div v-else>
        <p class="confirm">{{ confirmMsg }}</p>
      </div>
    </template>
    <template v-else="props.controlType === '참여링크'" #modal-body
      >참여링크</template
    >

    <template v-if="modalFooterType === 0" #modal-footer>
      <div @click="emit('closeModal')">아니오</div>
      <div class="yes" @click="onClickYes">예</div>
    </template>

    <template v-else-if="modalFooterType === 1" #modal-footer>
      <div @click="emit('closeModal')">닫기</div>
    </template>
  </ModalDefault>
</template>

<style scoped>
.yes {
  color: var(--main-color);
  border-left: 1px solid var(--light-grey1);
}

:deep(.modal__body p) {
  margin: 4px 0;
}
:deep(.modal__body p.confirm) {
 margin-top: 10px;
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
