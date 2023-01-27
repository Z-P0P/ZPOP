<script setup>
import { reactive, ref } from 'vue';
import ReplyList from "./ReplyList.vue";
import { defineProps, onMounted } from 'vue';
import { useReplyStore } from '@/stores/replyStore'
import { useCommentStore } from '@/stores/CommentStore'
import InputBox from './InputBox.vue';
import SelectModal from './SelectModal.vue';

const commentId = props.comment.id;
const cmtStore = useCommentStore();
const rplyStore = useReplyStore(); 
rplyStore.commentId = commentId;

const props = defineProps({
  comment: Object
});
const emit = defineEmits([
  'counterIncreased',
  'onEdit'
]);

cmtStore.addComment(props.comment); //댓글스토어에 댓글 하나씩 채우기
rplyStore.addComment(props.comment); 

const countOfReply = ref(props.comment.countOfReply);
// const dataForReplyList = { //자식컴포넌트에 전달할 값들.
//   id: commentId,
//   replyList: replyList
// }
var hasBox = ref(false); //인풋박스 비노출
var replyClose = ref(false);//'닫기'버튼 비노출
var replyWrite = ref(true);//'답글쓰기'버튼 노출
var replyCnt = ref(false);//'답글4개' 버튼 노출
var isExpanded = ref(false);//답글리스트 비노출

if (props.comment.countOfReply > 0)
  replyCnt.value = true;//원댓글이 답글을 가지고있는지 여부. 없으면 '답글0개' 비노출

function toggleReplyCnt() { //'답글4개' <-> '닫기' 전환
isExpanded.value = !isExpanded.value;
replyClose.value = !replyClose.value; //닫기 노출
replyCnt.value = !replyCnt.value;     //카운트 비노출
if (replyClose.value)
  rplyStore.fillRepliesToComment(commentId);//답글스토어에 답글리스트 채우기
else {
  rplyStore.comments[commentId].replyList.length = 0; //전환시 답글배열 초기화
}
}
function toggleInputBox() { //'인풋박스' <-> '답글쓰기' 전환
  isExpanded.value = !isExpanded.value;
  hasBox.value = !hasBox.value //인풋박스 노출
}

function increaseCounter() {
  countOfReply.value++;
  emit('counterIncreased');
}
//답글 등록버튼에서 올라오는 이벤트 처리기
function registerFinish() {
  increaseCounter();
  rplyStore.comments[commentId].replyList.length = 0; //스토어 초기화
  rplyStore.fillRepliesToComment(commentId); //DB에 저장된 결과를 화면에 뿌려주기
  hasBox.value = false; //완료후 인풋박스 비노출
  replyClose.value = true; //닫기 노출
  replyCnt.value = false;     //카운트 비노출
  isExpanded.value = true;  //답글리스트 외부마진 넣어줌

}
/*****************셀렉트 모달 열고 닫기**********************/
cmtStore.initSelectModal();
function toggleSelectModal(){
  if(cmtStore.selectModalStatus[commentId]){
    cmtStore.openSelectModal(commentId)
  }
  else{
    cmtStore.initSelectModal();
  }
}
</script>

<template>
  <div class="profile select-box">
    <span class="profile__image"></span>
    <span class="profile__nickname">{{ comment.nickname }}</span>
    <span class="profile__time">{{ comment.elapsedTime }}</span>
    <button @click="toggleSelectModal"></button>
    <SelectModal :role="'writer'" :commentId="commentId" :groupId="0"
      v-if="comment.myComment&&!cmtStore.selectModalStatus[commentId]" 
      @onEdit="emit('onEdit')"/>
    <SelectModal :role="'member'" :commentId="commentId" :groupId="0"
      v-if="!comment.myComment&&!cmtStore.selectModalStatus[commentId]"/>
  </div>
  <span class="comment__content">{{ comment.content }}</span>
  <div class="comment__replies" :class="{ hidden: hasBox }">
    <span class="pointer underline reply-cnt" @click="toggleReplyCnt" v-show="replyCnt">답글 {{ countOfReply }}개</span>
    <span class="pointer reply-close " @click="toggleReplyCnt" v-show="replyClose">닫기</span>
    <span class="pointer underline reply-write modal__on-btn" data-modal="#dummy-modal" v-show="replyWrite"
      @click="toggleInputBox">답글 달기</span>
  </div>
  <section class="reply" :class="{ 'no-margin': !isExpanded }">
    <!--여기서 답글 inputBox에 보내는 객체는 댓글(comment)이지만 받는쪽 이름인 답글(reply)에 맞춰줌-->
    <InputBox :reply="comment" v-show="hasBox" @cancelClicked="toggleInputBox" @registerCompleted="registerFinish" />
    <ReplyList  :commentId="commentId" @counterIncreased="increaseCounter" />
  </section>
</template>
<style scoped>
@import url(@/assets/css/component/select.css);
@import url(@/assets/css/meeting/component/profile-box.css);
@import url(@/assets/css/meeting/component/comment.css);

/* 답글리스트가 고유의 마진을 갖고 있어 답글이 없을때도 공간을 차지하므로 
리스트가 닫혀있을때 마진을 빼줌*/
.no-margin {
  margin: 0 auto 0 24px;
  padding: 0;
}

.profile {
  position: relative;
}
</style>