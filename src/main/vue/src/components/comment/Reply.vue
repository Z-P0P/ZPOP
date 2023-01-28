<script setup>
  import { defineProps,ref} from 'vue';
  import InputBox from './InputBox.vue';
  import SelectModal from './SelectModal.vue';
  import { useReplyStore } from '@/stores/ReplyStore'

  const replyId = props.reply.id;
  const groupId = props.reply.groupId;
  const rplyStore = useReplyStore();

  const props = defineProps({
    reply:Object
  });
  const emit = defineEmits(['counterIncreased']);

  var hasBox = ref(false);
  var replyWrite = ref(true)

  function inputBoxToggle() { //'인풋박스' <-> '답글쓰기' 전환
    replyWrite.value= !replyWrite.value;
    hasBox.value = !hasBox.value;
  }
  //답글 등록버튼에서 올라오는 이벤트 처리기
  function registerFinish(){
    inputBoxToggle();
    rplyStore.reloadReply(groupId); //AJAX로 화면갱신
    emit('counterIncreased');
  }
  /*****************답글 수정 ******************/
  const inputs = { f1: ref() };
  var isB2Active = ref(true)
  var isB3Active = ref(false)
  function setButtonForEdit(){
        isB2Active.value = !isB2Active.value;
        isB3Active.value = !isB3Active.value;
    }
  function onEditReply(){
    inputBoxToggle()
    setButtonForEdit();
    const inputBoxComponent = inputs["f1"].value;
    console.log(inputBoxComponent.inputs)
    let content = "";
    rplyStore.comments[groupId].replyList.forEach(element => {
      if(element.id == replyId){
        content = element.content;
        return 0;
      }
    });
    //input.focus();
    //input.value = content + " ";
  }
  function cancelEdit(){
    const input = inputs["f1"].value;
    input.focus();
    input.value = "";
    setButtonForEdit();
    cmtStore.selectModalStatus[groupId] = true;
  }
  function saveEdit(){
    const data = {};
    data.id = groupId; 
    const input = inputs['f1'].value;
    data.content = input.value;
    const dataJSONStr = JSON.stringify(data);
    api.comment.updateComment(groupId, dataJSONStr).then((res) => {
      if (res.ok) {
        console.log("댓글 수정됨");
        cmtStore.reloadComment(mtDetailStore,mtDetailStore.id)
        input.value = "";
        input.focus();
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
        <span class="profile__time">{{ reply.elapsedTime }}</span>
        <button @click="toggleSelectModal"></button>
        <SelectModal 
            v-if="!rplyStore.comments[groupId].selectModalStatus[replyId]" 
            :isMyComment="reply.myComment" 
            :commentId="reply.id" 
            :groupId="groupId"
            @onEdit="onEditReply" 
        />
        <!-- <SelectModal :role="member" v-else v-show="!isSelectModalClosed"/> -->
    </div>
    <div class="reply-container">
        <span class="reply__to">{{reply.parentNickname?'@'+reply.parentNickname:reply.parentNickname }}</span>
        <span class="reply__content">{{reply.content}}</span>
    </div>
    <div class="reply__replies">
        <span class="pointer underline" 
          v-show="replyWrite"
          @click="inputBoxToggle('replyWrite')" 
        >답글 달기</span> 
    </div>
    <InputBox 
      v-show="hasBox" 
      :ref="inputs.f1"  
      :reply="props.reply" 
      :isB2Active="isB2Active"
      :isB3Active="isB3Active"
      @cancelClicked="inputBoxToggle" 
      @registerCompleted="registerFinish" 
    />
</template>
<style >
  @import url(@/assets/css/component/select.css);
  @import url(@/assets/css/meeting/component/profile-box.css);
  @import url(@/assets/css/meeting/component/reply.css);
 
</style>