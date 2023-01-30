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
  if (!e.target.classList.contains("modal-default-wrap")) return;

  emit("closeModal");
}
</script>

<template>
  <section class="modal-default-wrap" @click="closeModal($event)">
    <div class="modal-default">
      <div class="modal__header">
        <!--     <span class="icon icon-x pointer modal__close-btn"> </span> -->
      </div>
      <div class="modal__body">
        <slot name="modal-body"></slot>
      </div>
      <div class="modal__footer">
        <slot name="modal-footer"></slot>
      </div>
    </div>
  </section>
</template>

<style scoped>
.modal-default-wrap {
  background-color: rgba(0, 0, 0, 0.15);
  position: fixed;
  inset: 0; /*t, l, b, r 0*/
}

.modal-default {
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
  overflow: hidden;
}

.modal__header {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  padding: 16px;
}

.modal__body {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  padding: 0 16px 24px 16px;
  color: black;
}

.modal__footer {
  display: flex;
}

/* ----------------- */
.modal-default-wrap {
  background-color: rgba(84, 83, 83, 0.16);
  position: fixed;
  inset: 0; /*t, l, b, r 0*/
}

.modal-default {
  position: relative;
  top: 25%;
  max-width: 360px;
  height: fit-content;
  background-color: white;
  margin: 0 auto;
  border: 1px solid #c4c4c4;
  box-shadow: 0px, 3px rgba(0, 0, 0, 0.25);
  font-weight: 600;
  border-radius: 10px;
  font-size: 18px;
  z-index: 999;
  overflow: hidden;
}

.modal__header {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  padding: 24px;
}

.modal__body {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  padding: 0 35px 35px 35px;
  color: var(--dark-grey1);
}

.modal__footer {
  display: flex;
}

.modal-default-wrap {
  z-index: 3;
}
.yes {
  color: var(--main-color);
  border-left: 1px solid var(--light-grey1);
}

:deep(.modal__body p) {
  margin: 4px 0;
}
:deep(.modal__body span.confirm) {
  margin-top: 10px;
  display: flex;
  flex-direction: column;
  align-items: center;
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
