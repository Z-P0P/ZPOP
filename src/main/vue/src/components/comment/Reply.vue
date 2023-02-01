<script setup>
  import { nextTick,ref} from 'vue';
  import api from "@/api";
  import { useMemberStore } from "@/stores/memberStore";
  import { useLoginModalStore } from "@/stores/loginModalStore";
  import { useReplyStore } from '@/stores/ReplyStore'
  import InputBox from './InputBox.vue';
  import SelectModal from './SelectModal.vue';
  
  const memberStore = useMemberStore();
  const loginModalStore = useLoginModalStore();
  const rplyStore = useReplyStore();
  
  async function checkLoginStatus(){
    const isLoggedIn = await memberStore.isAuthenticated();
    if (!isLoggedIn) {
      loginModalStore.handleModal();
      return true;
    }
  }

  const replyId = props.reply.id;
  const groupId = props.reply.groupId;

  const props = defineProps({
    reply:Object
  });
  const emit = defineEmits(['counterIncreased']);

  var hasBox = ref(false);
  var replyWrite = ref(true)
  const inputTemplate = ref(null);
  var inputBox = null;//답글창 초기화
  rplyStore.initButtons();//버튼 초기화

  function hideLinks(){
    replyWrite.value= !replyWrite.value;
    hasBox.value = !hasBox.value;
  }
  async function initInputBox(){
    if(!inputBox){
      inputBox = inputTemplate.value.textInputRef;//자식컴포넌트에서 객체받아옴
      await nextTick();
      inputBox.focus();
     rplyStore.showWriteButton();
    }
    else 
      inputBox = null;
  }
  async function inputBoxToggle() { //'인풋박스' <-> '답글쓰기' 전환
    if(await checkLoginStatus())
        return
    hideLinks();
    initInputBox();
  }
  //답글 등록버튼에서 올라오는 이벤트 처리기
  function registerFinish(){
    inputBoxToggle();
    rplyStore.reloadReply(groupId); //AJAX로 화면갱신
    emit('counterIncreased');
  }
  /*****************답글 수정 ******************/
  const isEditing = ref(false);

  async function onEditReply(){
    isEditing.value = true;
    hideLinks()
    let content = "";
    rplyStore.comments[groupId].replyList.forEach(element => {
      if(element.id == replyId){
        content = element.content;
        return 0;
      }
    });
    initInputBox();
    inputBox.value = content + " ";
    rplyStore.showEditButton();
    
  }
  function cancelEdit(){
    inputBox = inputTemplate.value.textInputRef;
    inputBox.value = null;
    isEditing.value = false;
    rplyStore.comments[groupId].selectModalStatus[replyId] = true;
    inputBoxToggle();
  }
  function saveEdit(){
    const data = {};
    data.id = replyId; 
    data.content = inputBox.value;
    const dataJSONStr = JSON.stringify(data);
    api.comment.updateReply(replyId, dataJSONStr).then((res) => {
      if (res.ok) {
        console.log("답글 수정됨");
        rplyStore.reloadReply(groupId);
        inputBox.value = "";
      } else alert("시스템 장애로 등록이 안되고 있습니다");
    });
  }
  /*****************셀렉트 모달 열고 닫기**********************/
  rplyStore.initSelectModal(groupId);
  function toggleSelectModal(){
    if(rplyStore.comments[groupId].selectModalStatus[replyId]){
      rplyStore.openSelectModal(replyId, groupId)
    }
    else{
      rplyStore.initSelectModal(groupId);
    }
  }
</script>

<template>
     <div class="profile select-box">
        <span class="profile__image"></span>
        <span class="profile__nickname profile__nickname">{{ reply.nickname }}</span>
        <span class="profile__time">{{ reply.updatedAt?reply.elapsedTime+' (수정됨)':reply.elapsedTime }}</span>
        <button @click="toggleSelectModal"></button>
        <SelectModal 
            v-if="!rplyStore.comments[groupId].selectModalStatus[replyId]" 
            :isMyComment="reply.myComment" 
            :commentId="reply.id" 
            :groupId="groupId"
            @onEdit="onEditReply" 
        />
    </div>
    <div v-show="!isEditing" class="reply-container">
        <span class="reply__to">{{reply.parentNickname?'@'+reply.parentNickname:reply.parentNickname }}</span>
        <span class="reply__content">{{reply.content}}</span>
    </div>
    <div class="reply__replies">
        <span class="pointer underline" 
          v-show="replyWrite"
          @click="inputBoxToggle" 
        >답글 달기</span> 
    </div>
    <InputBox 
      v-show="hasBox" 
      ref="inputTemplate"  
      :reply="props.reply" 
      @cancelClicked="cancelEdit" 
      @registerCompleted="registerFinish" 
      @editSaveClicked="saveEdit"
    />
</template>
<style >
  @import url(@/assets/css/component/select.css);
  @import url(@/assets/css/meeting/component/profile-box.css);
  @import url(@/assets/css/meeting/component/reply.css);
 
</style>