<script setup>
import { defineProps, defineEmits, reactive, ref, onMounted,onUpdated } from "vue";
import api from "@/api";
import { useMeetingDetailStore } from "@/stores/meetingDetailStore";
import { useCommentStore } from '@/stores/commentStore';
import Comment from "./Comment.vue";

const mtDetailStore = useMeetingDetailStore();
const cmtStore = useCommentStore();

const inputs = { f1: ref() };
onMounted(() => {
  const input = inputs["f1"].value;
  input.focus();
});
const commentList = reactive([]);
function registerComment(refId) {
  const data = {};
  data.meetingId = mtDetailStore.id; //미팅 id
  const input = inputs[refId].value;
  data.content = input.value;
  const dataJSONStr = JSON.stringify(data);
  api.comment.registerComment(dataJSONStr).then((res) => {
    if (res.ok) {
      console.log("댓글 등록됨");
      cmtStore.reloadComment(mtDetailStore,mtDetailStore.id)
      input.value = "";
      input.focus();
    } else alert("시스템 장애로 등록이 안되고 있습니다");
  });
}

</script>

<template>
  <section class="comment">
    <h2 class="comment__num">댓글 {{ mtDetailStore.commentCount }} 개</h2>
    <div class="comment__input-container">
      <textarea
        class="comment__input"
        name="comment-text"
        id="comment-text"
        placeholder="댓글을 입력하세요."
        :ref="inputs.f1"
      ></textarea>
      <div class="comment__btn-container">
        <span class="reply__btn btn btn-round btn-cancel cancel-btn hidden"
          >취소하기</span
        >
        <span
          class="comment__btn btn btn-round btn-action modal__on-btn"
          id="register-btn"
          data-modal="#dummy-modal"
          @click="registerComment('f1')"
          >등록하기</span
        >
        <span
          class="hidden comment__btn btn btn-round btn-action"
          id="edit-save-btn"
          >저장하기</span
        >
      </div>
    </div>
    <ul class="comment__list">
      <li v-for="(comment, index) in mtDetailStore.comments" :key="index">
        <Comment
          :comment="comment"
          @counterIncreased="mtDetailStore.increaseCommentCount"
        />
      </li>
    </ul>
  </section>
</template>

<style scoped>
@import url(@/assets/css/component/select.css);
@import url(@/assets/css/meeting/component/profile-box.css);
@import url(@/assets/css/meeting/component/comment.css);
 #register-btn{
  width: fit-content;
  height: fit-content;
  user-select: none;
  color: var(--white);
  cursor: pointer;
  display: inline-flex;
  justify-content: center;
  align-items: center;
  padding-left: 16px;
  padding-right: 16px;
  padding-top: 6.5px;
  padding-bottom: 6.5px;
 }

 @media  (min-width: 576px){
  #register-btn {
    padding-left: 32px;
    padding-right: 32px;
    padding-top: 6.9px;
    padding-bottom: 6.9px;
 }
 }
</style>
