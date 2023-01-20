<script setup>
  import { setMapStoreSuffix } from "pinia";
  import api from "@/api";
  import { reactive,ref } from 'vue';
  import ReplyList from "./ReplyList.vue";
  import { defineProps } from 'vue';
  import { useCommentStore} from '@/stores/commentStore'
  import InputBox from './InputBox.vue';

  const props = defineProps({
    comment:Object
  });
  const emit = defineEmits([
    'counterIncreased'
  ]);

  const replyList = reactive([]);
  const countOfReply = ref(props.comment.countOfReply);
  const cmtStore = useCommentStore();
  cmtStore.comment.id = props.comment.id;
  const dataForReplyList= {
    id:props.comment.id,
    replyList:replyList
  }
  var replySection = reactive({on:false});
  var hasBox = reactive({on:false});
  var replyClose = reactive({on:false});
  var replyWrite = reactive({on:true});
  var replyCnt = reactive({on:true});

  function toggleReplyCnt(){
    getReplyList(replyCnt.on);
    replyClose.on = !replyClose.on;
    replyCnt.on = !replyCnt.on;
  }
  function toggleInputBox() {
    if(hasBox.on){
      getReplyList(hasBox.on);
    }
    replyWrite.on= !replyWrite.on;
    hasBox.on = !hasBox.on;
  }


  async function getReplyList(needToGetList){
    if(needToGetList){
      console.log("함수콜")
      const data = await cmtStore.getReplyList(props.comment.id);

      for(const r of data.resultObject) 
        replyList.push(r);
      cmtStore.comment.replyList = replyList;
    }
    else {
      replyList.length = 0;
    }  
  }
  function hasReply (comment){
    let hasReply = false;
    if(comment.countOfReply>0)
    hasReply = true;
    return hasReply;
  }
 
  function increaseCounter(){
    countOfReply.value++;
    emit('counterIncreased');
  }
  function registerFinish(){
    increaseCounter();
    cmtStore.comment.replyList.length=0;
    getReplyList(hasBox.on);
    replyWrite.on= !replyWrite.on;
    hasBox.on = !hasBox.on;

  }
</script>

<template>
    <div class="profile select-box">
      <span class="profile__image"></span>
      <span class="profile__nickname">{{ comment.nickname }}</span>
      <span class="profile__time">{{comment.elapsedTime}}</span>
      <button></button>
      <th:block th:if="${comment.isMyComment}">
              <div sec:authorize="isAuthenticated()" th:replace="~{inc/meeting-detail-modal::writer}"></div>
      </th:block>
      <th:block th:if="${!comment.isMyComment}">
              <div sec:authorize="isAuthenticated()" th:replace="~{inc/meeting-detail-modal::reader}"></div>
      </th:block>
          </div>
    <span class="comment__content">{{ comment.content }}</span>
    <div class="comment__replies">
      <span class="pointer underline reply-cnt"  @click="toggleReplyCnt"  v-if="hasReply(comment)" v-show="replyCnt.on" >답글 {{ countOfReply }}개</span>
      <span class="pointer reply-close " @click="toggleReplyCnt"  v-if="hasReply(comment)" v-show="replyClose.on" >닫기</span>
      <span class="pointer underline reply-write modal__on-btn" data-modal="#dummy-modal" v-show="replyWrite.on" @click="toggleInputBox">답글 달기</span>
    </div>
    <section class="reply" v-show="!replyCnt.on">
      <!--여기서 답글 inputBox에 보내는 객체는 댓글(comment)이지만 받는쪽 이름인 답글(reply)에 맞춤-->
      <InputBox :reply="comment" v-show="hasBox.on" @cancelClicked="toggleInputBox" @registerCompleted="registerFinish"/> 
      <ReplyList :comment="dataForReplyList" @counterIncreased="increaseCounter"/> 
    </section>
</template>
<style scoped>

 @import url(@/assets/css/meeting/component/select.css);
 @import url(@/assets/css/meeting/component/profile-box.css);
 @import url(@/assets/css/meeting/component/comment.css);

</style>