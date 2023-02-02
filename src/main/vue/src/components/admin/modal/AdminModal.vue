<script setup>
const emit = defineEmits(["closeModal", "action"]);
const props = defineProps(["modalType"]);

function closeModal(e) {
  console.log('모달 닫기');
  // X버튼 클릭이라면 닫기
  if (e.target.classList.contains("modal__close-btn")) {
    emit("closeModal", props.modalType);
    return;
  }
  // 모달 배경 클릭이 아니라면 return
  if (!e.target.classList.contains("modal-default-wrap")) return;

  emit("closeModal", props.modalType);
}

function action(){
  emit("action", props.modalType);
}


</script>

<template>
  <section class="modal-default-wrap" @click="closeModal($event)">
    <div class="modal-default">
      <div class="modal__header">
        <slot name="modal-header"></slot>
        <span class="icon icon-x-white pointer modal__close-btn">
        </span>
      </div>
      <div class="modal__body">
        <slot name="modal-body"></slot>
      </div>
      <div class="modal__footer">
        <span class="btn btn-round btn-action" @click="action">확인</span>
        <span class="btn btn-round" @click="closeModal($event)">취소</span>
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
  max-width: 460px;
  min-height: 400px;
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
  height:40px;
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
  background-color: var(--admin-loyal-blue);
}

.modal__body {
  display: flex;
  justify-content: center;
  padding: 30px 16px 24px 16px;
  color: var(--dark-grey1);
  flex-direction: column;
}


.modal__footer{
  display:flex;
  justify-content: center;
  column-gap:10px;
}

.btn-action {
    background-color: var(--admin-loyal-blue);
}
</style>
