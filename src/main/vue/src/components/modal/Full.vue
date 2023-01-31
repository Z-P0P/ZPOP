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
  <div class="modal-wrapper">
    <div class="modal">
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
  </div>
</template>

<style scoped>
.modal-wrapper {
  position: fixed;
  inset: 0; /*t, l, b, r 0*/
  z-index: 10000;
}
.modal {
  width: 100%;
  height: 100%;
  background-color: var(--modal-white);
  animation-name: modal-mobile;
  animation-duration: 0.3s;
}

.modal__header {
  display: flex;
  padding: 1rem;
}

.modal__close-btn {
  margin-left: auto;
}

.modal__content-container {
  display: flex;
  transition: transform 0.3s;
  height: fit-content;
}
.modal__content-container--content1 {
  transform: translateX(-100%);
  height: 200px;
}

@media (min-width: 576px) {
  .modal-wrapper {
    justify-content: center;
    animation-name: modal-bg;
    animation-duration: 0.3s;
    animation-fill-mode: forwards;
  }

  .modal {
    position: relative;
    max-width: 30rem;
    height: fit-content;
    background-color: transparent;
    box-shadow: 0px 5px 15px rgba(0, 0, 0, 0.15);
    animation-name: modal-desktop;
    animation-duration: 0.5s;
    animation-fill-mode: forwards;
    margin: 0 auto;
  }

  .modal__header {
    align-items: center;
    padding: 0 20px;
    min-height: 3rem;
    background-color: var(--main-color);
    border-radius: 10px 10px 0 0;
  }

  .modal__body {
    min-height: 5rem;
    height: 100%;
    background-color: var(--white);
    border-radius: 0 0 10px 10px;
  }
}

/* modal animation */
@keyframes modal-desktop {
  from {
    top: 0;
    opacity: 0;
  }

  to {
    top: 20%;
    opacity: 1;
  }
}

@keyframes modal-mobile {
  from {
    transform: translateY(100%);
  }

  to {
    transform: translateY(0);
  }
}

@keyframes modal-bg {
  from {
    background: rgba(0, 0, 0, 0);
  }

  to {
    background: rgba(0, 0, 0, 0.3);
  }
}

</style>
