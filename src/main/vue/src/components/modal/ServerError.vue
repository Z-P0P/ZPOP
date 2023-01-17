<script setup>
import { defineEmits } from "vue";

const emit = defineEmits(["closeModal"]);

function closeModal(e) {
  // X버튼 클릭이라면 닫기
  if (e.target.classList.contains("modal__close-btn")) {
    emit("closeModal");
    return;
  }
  // 모달 배경 클릭이 아니라면 return
  if (!e.target.classList.contains("modal-server-error-wrap")) return;

  emit("closeModal");
}
</script>

<template>
  <section class="modal-server-error-wrap" @click="closeModal($event)">
    <div class="modal-server-error">
      <div class="modal__header">
        <span
          class="icon icon-x pointer modal__close-btn"
          @click="closeModal($event)"
        ></span>
      </div>
      <div class="modal__body">
        <p>서버에 문제가 생겼어요 🤒</p>
        <p>잠시후에 다시 시도해주세요</p>
      </div>
    </div>
  </section>
</template>

<style scoped>
.modal-server-error-wrap {
  background-color: rgba(0, 0, 0, 0.15);
  position: fixed;
  inset: 0; /*t, l, b, r 0*/
}

.modal-server-error {
  position: relative;
  top: 25%;
  max-width: 360px;
  height: fit-content;
  background-color: white;
  margin: 0 auto;
  border: 1px solid #c4c4c4;
  box-shadow: 0px 3px 8px rgba(0, 0, 0, 0.25);
  border-radius: 10px;
  font-size: 18px;
  z-index: 999;
}

.modal__header {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  padding: 16px;
}

.modal__body {
  display: flex;
  align-items: center;
  flex-direction: column;
  padding: 0 16px 24px 16px;
  color: var(--dark-grey1);
}
</style>
