<script setup>
  import { ref, watch, onMounted, toRaw, nextTick} from "vue";
  import api from "@/api";
  import { useMemberStore } from "@/stores/memberStore";
  import { useLoginModalStore } from "@/stores/loginModalStore";
  import { useMeetingDetailStore } from "@/stores/meetingDetailStore";
  import { useCommentStore } from '@/stores/commentStore';
  import  CommentBox  from './CommentBox.vue'
  import Comment from "./Comment.vue";
  
  const memberStore = useMemberStore();
  const loginModalStore = useLoginModalStore();
  const mtDetailStore = useMeetingDetailStore();
  const cmtStore = useCommentStore();
  
  var commentId = 0;
  async function checkLoginStatus(){
    const isLoggedIn = await memberStore.isAuthenticated();
    if (!isLoggedIn) {
      loginModalStore.handleModal();
      return;
    }
  }
  var isEditing = false;
  const newComment = ref('')
  const stop = watch(newComment,(newComment)=>{
    if(!isEditing)
      cmtStore.showWriteButton();
    else  
      cmtStore.showEditButton();

    if(newComment===""){
      cmtStore.initButtons();
    }
  });
  async function registerComment() {
    const isLoggedIn = await memberStore.isAuthenticated();
    if(!isLoggedIn){
      loginModalStore.handleModal();
      return 
    }
    const data = {};
    data.meetingId = mtDetailStore.id; //미팅 id
    data.content = newComment.value;
    const dataJSONStr = JSON.stringify(data);
    api.comment.registerComment(dataJSONStr).then((res) => {
      if (res.ok) {
        console.log("댓글 등록됨");
        cmtStore.reloadComment(mtDetailStore,mtDetailStore.id)
        newComment.value = "";
        mtDetailStore.increaseCommentCount();
      } else alert("시스템 장애로 등록이 안되고 있습니다");
    });
  }
  /*****************댓글 수정 ******************/
  cmtStore.initButtons();
  const inputTemplate = ref(null);
  function childFocus(){
    inputTemplate.value.childMethod();
  }
  function onEditComment(e){
   // stop();
    isEditing = true;
    commentId = e.targetId;
    let content = "";
    cmtStore.commentList.forEach(element => {
      if(element.id == commentId){
        content = element.content;
        return 0;
      }
    });
    childFocus();
    newComment.value = content.trim() + " ";
    cmtStore.showEditButton();
  }
  function cancelEdit(){
    newComment.value = "";
    cmtStore.initButtons();
    isEditing = false;
    cmtStore.selectModalStatus[commentId] = true;
  }
  function saveEdit(){
    const data = {};
    data.id = commentId; 
    data.content = newComment.value;
    const dataJSONStr = JSON.stringify(data);
    api.comment.updateComment(commentId, dataJSONStr).then((res) => {
      if (res.ok) {
        console.log("댓글 수정됨");
        cmtStore.reloadComment(mtDetailStore,mtDetailStore.id)
        newComment.value = "";
        isEditing = false;
      } else alert("시스템 장애로 등록이 안되고 있습니다");
    });
  }
</script>

<template>
  <section class="comment">
    <h2 class="comment__num">댓글 {{ mtDetailStore.commentCount }} 개</h2>
    <div class="comment__input-container">
      <CommentBox 
        v-model="newComment"
        class="comment__input"
        placeholder="댓글을 입력하세요."
        ref="inputTemplate"
        @focus="checkLoginStatus"
      ></CommentBox>
      <div class="comment__btn-container">
        <span  v-if="cmtStore.buttons.isB1Active"
          class="comment__btn btn btn-round btn-cancel cancel-btn"
          @click = "cancelEdit"
          >취소하기</span
        >
        <span  v-if="cmtStore.buttons.isB2Active"
          class="comment__btn btn btn-round btn-action"
          @click="registerComment"
          >등록하기</span
        >
        <span  v-if="cmtStore.buttons.isB3Active"
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
