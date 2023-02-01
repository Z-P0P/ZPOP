<script setup>
  import { nextTick, ref } from 'vue';
  import { useMemberStore } from "@/stores/memberStore";
  import { useLoginModalStore } from "@/stores/loginModalStore";
  import { useReplyStore } from '@/stores/replyStore'
  import { useCommentStore } from '@/stores/CommentStore'
  import ReplyList from "./ReplyList.vue";
  import InputBox from './InputBox.vue';
  import SelectModal from './SelectModal.vue';

  const memberStore = useMemberStore();
  const loginModalStore = useLoginModalStore();
  const cmtStore = useCommentStore();
  const rplyStore = useReplyStore(); 

  async function checkLoginStatus(){
    const isLoggedIn = await memberStore.isAuthenticated();
    if (!isLoggedIn) {
      loginModalStore.handleModal();
      return true;
    }
  }
 
  const props = defineProps({
    comment: Object
  });
  const emit = defineEmits([
    'counterIncreased',
    'onEdit',
  ]);

  const commentId = props.comment.id;
  rplyStore.commentId = commentId;

  cmtStore.addComment(props.comment); //댓글스토어에 댓글 하나씩 채우기
  rplyStore.addComment(props.comment); 

  const countOfReply = ref(props.comment.countOfReply);

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
  const inputTemplate = ref();
  var inputBox = null;
  rplyStore.initButtons();
  async function toggleInputBox() { //'인풋박스' <-> '답글쓰기' 전환
    rplyStore.showWriteButton(); //새 답글 등록용 버튼
    if(await checkLoginStatus())
        return
    isExpanded.value = !isExpanded.value;
    hasBox.value = !hasBox.value //인풋박스 노출
    if(!inputBox){
        inputBox = inputTemplate.value.textInputRef;//자식컴포넌트에서 객체받아옴
        await nextTick();
        inputBox.focus();
      }
      else 
        inputBox = null;
  }

  function increaseCounter() {
    countOfReply.value++;
    emit('counterIncreased');
  }
  //답글 등록버튼에서 올라오는 이벤트 처리기
  function registerFinish() {
    increaseCounter();
    rplyStore.reloadReply(commentId); //AJAX로 화면갱신
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
  function editComment(e){
      emit('onEdit',e)
  }
</script>

<template>
  <div class="profile select-box">
    <span class="profile__image"><img :src="(comment.profileImagePath != '' && comment.profileImagePath != null)
          ? `/image/profile/${comment.profileImagePath}` : '/images/icon/user-profile-grey.svg'"></span>
    <span class="profile__nickname">{{ comment.nickname }}</span>
    <span class="profile__time">{{ comment.updatedAt?comment.elapsedTime+' (수정됨)':comment.elapsedTime }}</span>
    <button @click="toggleSelectModal"></button>
    <!--kebob select box-->
    <SelectModal 
        v-if="!cmtStore.selectModalStatus[commentId]" 
        :commentId="commentId" 
        :groupId="0" 
        :isMyComment="comment.myComment"
        @onEdit="editComment"
    />
  </div>
  <span class="comment__content">{{ comment.content }}</span>
  <div class="comment__replies" :class="{ hidden: hasBox }">
    <span class="pointer underline reply-cnt" 
      v-show="replyCnt"
      @click="toggleReplyCnt" 
    >답글 {{ countOfReply }}개</span>
    <span class="pointer reply-close " 
      v-show="replyClose"
      @click="toggleReplyCnt" 
    >닫기</span>
    <span class="pointer underline reply-write" 
      v-show="replyWrite"
      @click="toggleInputBox"
    >답글 달기</span>
  </div>
  <section class="reply" :class="{ 'no-margin': !isExpanded }">
    <!--여기서 답글 inputBox에 보내는 객체는 댓글(comment)이지만 받는쪽 이름인 답글(reply)에 맞춰줌-->
    <InputBox 
      v-show="hasBox" 
      ref="inputTemplate"
      :reply="comment" 
      @cancelClicked="toggleInputBox"
      @registerCompleted="registerFinish" />
    <ReplyList  :commentId="commentId" 
      @counterIncreased="increaseCounter" />
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