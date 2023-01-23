<script setup>
import { defineProps, defineEmits, reactive, ref, onMounted } from "vue";
import api from "@/api";
import { useMeetingDetailStore } from "@/stores/meetingDetailStore";
import Comment from "./Comment.vue";

const meetingDetalStore = useMeetingDetailStore();

const emit = defineEmits(["newComment"]);
const inputs = { f1: ref() };
onMounted(() => {
  const input = inputs["f1"].value;
  input.focus();
});

function registerComment(refId) {
  const data = {};
  data.meetingId = meetingDetalStore.id; //미팅 id
  data.content = document.querySelector("#comment-text").value;
  const dataJSONStr = JSON.stringify(data);
  api.comment.registerComment(dataJSONStr).then((res) => {
    if (res.ok) {
      console.log("댓글 등록됨");
      emit("newComment");
      const input = inputs[refId].value;
      input.value = "";
    } else alert("시스템 장애로 등록이 안되고 있습니다");
  });
}
</script>

<template>
  <section class="comment">
    <h2 class="comment__num">댓글 {{ meetingDetalStore.commentCount }} 개</h2>
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
      <li v-for="(comment, index) in meetingDetalStore.comments" :key="index">
        <Comment
          :comment="comment"
          @counterIncreased="meetingDetalStore.increaseCommentCount"
        />
      </li>
    </ul>
  </section>
</template>

<style scoped>
@import url(@/assets/css/meeting/component/select.css);
@import url(@/assets/css/meeting/component/profile-box.css);
@import url(@/assets/css/meeting/component/comment.css);
</style>
