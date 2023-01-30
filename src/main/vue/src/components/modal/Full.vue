<script setup>
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
        <span
          class="icon icon-x pointer modal__close-btn"
          @click="closeModal($event)"
        ></span>
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
  position: fixed;
  bottom:0;
  height: 100vh;
  width: 100%;
  z-index: 10000;
}

.modal-default {
  position: relative;
  height: 100%;
  max-width: 100%;
  background-color: #fbfbfd;
  margin: 0 auto;
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
  justify-content: center;
  padding: 20px;
  color: var(--dark-grey1);
}

@media screen {
  
}

</style>

