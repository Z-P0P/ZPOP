<script setup>
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

  const replyList = reactive([]); //반응형 답글리스트
  const countOfReply = ref(props.comment.countOfReply);
  const cmtStore = useCommentStore(); //별도의 저장소
  cmtStore.comment.id = props.comment.id;
  const dataForReplyList= { //자식컴포넌트에 전달할 값들.
    id:props.comment.id,
    replyList:replyList
  }
  var hasBox = ref(false); //인풋박스 노출
  var replyClose = ref(false);//'닫기'버튼 노출
  var replyWrite = ref(true);//'답글쓰기'버튼 노출
  var replyCnt = ref(true);//'답글4개' 버튼 노출
  var isExpanded = ref(false);//답글리스트 노출

  function toggleReplyCnt(){ //'답글4개' <-> '닫기' 전환
    isExpanded.value = !isExpanded.value;
    if(isExpanded.value&&replyCnt.value)
      getReplyList();
    else{
      replyList.length = 0; //전환시 답글배열 초기화
    }
    replyClose.value = !replyClose.value; //닫기 노출
    replyCnt.value = !replyCnt.value;     //카운트 비노출
  }
  function toggleInputBox() { //'인풋박스' <-> '답글쓰기' 전환
    isExpanded.value = !isExpanded.value;
    hasBox.value = !hasBox.value //인풋박스 노출
  }

  function hasReply (comment){  //원댓글이 답글을 가지고있는지 여부. 없으면 '답글0개' 비노출
    let hasReply = false; 
    if(comment.countOfReply>0)
    hasReply = true;
    return hasReply;
  }
 //답글 가져오는 부분
  async function getReplyList(){
      const data = await cmtStore.getReplyList(props.comment.id);
      for(const r of data.resultObject) 
        replyList.push(r);
      cmtStore.comment.replyList = replyList; //별도로 스토어에 저장
  }
  function increaseCounter(){
    countOfReply.value++;
    emit('counterIncreased');
  }
  //답글 등록버튼에서 올라오는 이벤트 처리기
  function registerFinish(){
    increaseCounter();
    cmtStore.comment.replyList.length=0; //스토어 초기화
    getReplyList(); //DB에 저장된 결과를 화면에 뿌려주기
    hasBox.value = false; //완료후 인풋박스 비노출
    replyClose.value = !replyClose.value; //닫기 노출
    replyCnt.value = !replyCnt.value;     //카운트 비노출

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
  <div class="comment__replies" :class="{ hidden:hasBox }">
    <span class="pointer underline reply-cnt"  @click="toggleReplyCnt"  v-if="hasReply(comment)" v-show="replyCnt" >답글 {{ countOfReply }}개</span>
    <span class="pointer reply-close " @click="toggleReplyCnt"  v-if="hasReply(comment)" v-show="replyClose" >닫기</span>
    <span class="pointer underline reply-write modal__on-btn" data-modal="#dummy-modal" v-show="replyWrite" @click="toggleInputBox">답글 달기</span>
  </div>
  <section class="reply" :class="{ 'no-margin':!isExpanded }">
    <!--여기서 답글 inputBox에 보내는 객체는 댓글(comment)이지만 받는쪽 이름인 답글(reply)에 맞춰줌-->
    <InputBox :reply="comment" v-show="hasBox" @cancelClicked="toggleInputBox" @registerCompleted="registerFinish"/> 
    <ReplyList :comment="dataForReplyList" @counterIncreased="increaseCounter"/> 
  </section>
</template>
<style scoped>

 @import url(@/assets/css/meeting/component/select.css);
 @import url(@/assets/css/meeting/component/profile-box.css);
 @import url(@/assets/css/meeting/component/comment.css);
/* 답글리스트가 고유의 마진을 갖고 있어 답글이 없을때도 공간을 차지하므로 
리스트가 닫혀있을때 마진을 빼줌*/
 .no-margin{
  margin: 0;
  padding: 0;
}
</style>