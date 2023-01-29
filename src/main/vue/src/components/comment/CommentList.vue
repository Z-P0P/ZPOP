<script setup>
  import { ref } from "vue";
  import api from "@/api";
  import { useMemberStore } from "@/stores/memberStore";
  import { useLoginModalStore } from "@/stores/loginModalStore";
  import { useMeetingDetailStore } from "@/stores/meetingDetailStore";
  import { useCommentStore } from '@/stores/commentStore';
  import Comment from "./Comment.vue";
  
  const memberStore = useMemberStore();
  const loginModalStore = useLoginModalStore();
  const mtDetailStore = useMeetingDetailStore();
  const cmtStore = useCommentStore();
  
  var commentId = 0;

  const inputBox = ref();
  async function checkLoginStatus(){
    const isLoggedIn = await memberStore.isAuthenticated();
    if (!isLoggedIn) {
      inputBox.value.blur();
      loginModalStore.handleModal();
      return;
    }
  }
  var isB1Active = ref(false)
  var isB2Active = ref(true)
  var isB3Active = ref(false)
  function setButtonForEdit(){
    isB1Active.value = !isB1Active.value;
    isB2Active.value = !isB2Active.value;
    isB3Active.value = !isB3Active.value;
  }
  async function registerComment() {
    const isLoggedIn = await memberStore.isAuthenticated();
    if(!isLoggedIn){
      loginModalStore.handleModal();
      return 
    }
    const data = {};
    data.meetingId = mtDetailStore.id; //미팅 id
    data.content = inputBox.value.value;
    if(data.content===""){
      alert("글을 입력해주세요");
      return
    }
    const dataJSONStr = JSON.stringify(data);
    api.comment.registerComment(dataJSONStr).then((res) => {
      if (res.ok) {
        console.log("댓글 등록됨");
        cmtStore.reloadComment(mtDetailStore,mtDetailStore.id)
        inputBox.value.value = "";
      } else alert("시스템 장애로 등록이 안되고 있습니다");
    });
  }
  /*****************댓글 수정 ******************/
  function onEditComment(e){
    commentId = e.targetId;
    let content = "";
    cmtStore.commentList.forEach(element => {
      if(element.id == commentId){
        content = element.content;
        return 0;
      }
    });
    inputBox.value.focus();
    inputBox.value.value = content + " ";
    setButtonForEdit();
  }
  function cancelEdit(){
    inputBox.value.value = "";
    setButtonForEdit();
    cmtStore.selectModalStatus[commentId] = true;
  }
  function saveEdit(){
    const data = {};
    data.id = commentId; 
    data.content = inputBox.value.value;
    const dataJSONStr = JSON.stringify(data);
    api.comment.updateComment(commentId, dataJSONStr).then((res) => {
      if (res.ok) {
        console.log("댓글 수정됨");
        cmtStore.reloadComment(mtDetailStore,mtDetailStore.id)
        inputBox.value.value = "";
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
        placeholder="댓글을 입력하세요."
        ref="inputBox"
        @focus="checkLoginStatus"
      ></textarea>
      <div class="comment__btn-container">
        <span  v-if="isB1Active"
          class="comment__btn btn btn-round btn-cancel cancel-btn"
          @click = "cancelEdit"
          >취소하기</span
        >
        <span  v-if="isB2Active"
          class="comment__btn btn btn-round btn-action"
          @click="registerComment"
          >등록하기</span
        >
        <span  v-if="isB3Active"
          class="comment__btn btn btn-round btn-action"
          @click="saveEdit()"
          >저장하기</span
        >
      </div>
    </div>
    <ul class="comment__list">
      <li v-for="(comment, index) in mtDetailStore.comments" :key="index">
        <Comment
          :comment="comment"
          @counterIncreased="mtDetailStore.increaseCommentCount"
          @onEdit="onEditComment"
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
