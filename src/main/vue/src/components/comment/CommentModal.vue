<script setup>
import { ref } from "vue";
import api from "@/api";
import { useMeetingDetailStore } from "@/stores/meetingDetailStore";
import { useCommentStore } from "@/stores/commentStore";
import { useReplyStore } from '@/stores/ReplyStore'
import ModalDefault from "@/components/modal/Default.vue";
import { ServerException } from "@/utils/ServerException";

const mtDetailStore = useMeetingDetailStore();
const cmtStore = useCommentStore();
const rplyStore = useReplyStore();

const props = defineProps({
  selectType: String,
  commentId: Number,
  groupId: Number
});
const emit = defineEmits(["closeModal", "commentDeleted"]);

let confirmMsg = ref("");
let deleteMsg = ref("");

if(props.groupId)
  deleteMsg.value = "답글"
else 
  deleteMsg.value = "댓글"


async function onClickYes() {
  switch (props.selectType) {
    case "삭제":
      await deleteComment(props.commentId);
      break;
    case "신고":
      //신고는 별도 콤포넌트에서 처리
      break;
  }
}

async function deleteComment(id) {
  try {
    const res = await api.comment.deleteComment(id);
    if (!res.ok) throw new ServerException(res);
    const data = await res.json();
    mtDetailStore.commentCount--;
    confirmMsg.value = "삭제됐습니다.";
    closeModalFooterType();
    if(props.groupId>0)
      rplyStore.reloadReply(props.groupId)
    else
      cmtStore.reloadComment(mtDetailStore, mtDetailStore.id)
  } catch (e) {
    console.log(e)
  }
}



let modalFooterType = ref(0);

function closeModalFooterType() {
  modalFooterType.value = 1;
}
</script>

<template>
  <ModalDefault @closeModal="emit('closeModal')">
    
    <template v-if="props.selectType === '삭제'" #modal-body>
      <div v-if="!confirmMsg">
        <p class="confirm">{{ deleteMsg }}을 삭제하시겠어요?</p>
      </div>
      <div v-else>
        <p class="confirm">{{ confirmMsg }}</p>
      </div>
    </template>

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
