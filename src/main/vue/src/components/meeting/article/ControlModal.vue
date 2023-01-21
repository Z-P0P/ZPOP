<script setup>
import { useRoute } from "vue-router";
import api from "@/api";
import ModalDefault from "@/components/modal/Default.vue";
import { ServerException } from "@/utils/ServerException";

const route = useRoute();

const props = defineProps({
  controlType: String,
  articleTitle: String,
});

// refresh : 모임 상세 조회 최신화
const emit = defineEmits(["closeModal", "refresh"]);

async function onClickYes() {
  switch (props.controlType) {
    case "참여":
      await participate();
      break;
  }
}

async function participate() {
  try {
    const res = await api.meeting.participate(route.params.id);
    if (!res.ok) throw new ServerException(res);
    const data = await res.json();
    //TODO: 참여 링크 모달
    emit("refresh");
  } catch (e) {}
}
</script>

<template>
  <ModalDefault @closeModal="emit('closeModal')">
    <template v-if="props.controlType === '참여'" #modal-body>
      <p>🤝 {{ props.articleTitle }}</p>
      <p class="confirm">모임에 참여하시겠어요?</p>
    </template>
    <template v-else-if="props.controlType === '참여취소'" #modal-body
      >참여취소</template
    >
    <template v-else-if="props.controlType === '마감'" #modal-body
      >마감</template
    >
    <template v-else="props.controlType === '참여링크'" #modal-body
      >참여링크</template
    >

    <template #modal-footer>
      <div @click="emit('closeModal')">아니오</div>
      <div class="yes" @click="onClickYes">예</div>
    </template>
  </ModalDefault>
</template>

<style scoped>
.confirm {
  margin-top: 8px;
}

.yes {
  color: var(--main-color);
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
