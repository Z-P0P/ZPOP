<script setup>
const emit = defineEmits(["closeDetailModal", "action"]);
const props = defineProps(["modalType","title"]);

function closeModal(e) {
  // X버튼 클릭이라면 닫기
  if (e.target.classList.contains("modal__close-btn")) {
    emit("closeDetailModal");
    return;
  }
  // 모달 배경 클릭이 아니라면 return
  if (!e.target.classList.contains("modal-default-wrap")) return;

  emit("closeDetailModal");
}

function action(){
  emit("action", props.modalType);
}


</script>

<template>
  <section class="modal-default-wrap" @click="closeModal">
    <div class="modal-default">
      <div class="modal__header">
        <span class="modal__title">{{ title }}</span>
        <span class="icon icon-x-white pointer modal__close-btn">
        </span>
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
  top: 5%;
  max-width: 1060px;
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
  background-color:var(--admin-loyal-blue);
  top:0;
  display: flex;
  justify-content: flex-end;
  align-items: center;
  padding: 16px;
  height:40px;
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
}

.modal__close-btn{
  margin-left:auto;
}

.modal__body {
  display: flex;
  padding: 30px 16px 24px 16px;
  color: var(--dark-grey1);
  flex-direction: column;
  max-height: 800px;
  overflow-y: scroll;
}

.modal__footer{
  display:flex;
  justify-content: center;
  column-gap:10px;
}

.modal__title{
  color:white;
  font-weight:var(--bold);
}
</style>
