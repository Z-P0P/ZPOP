<script setup>
import { defineProps,ref} from 'vue';
import InputBox from './InputBox.vue';

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
  emit('counterIncreased');
}
</script>

<template>
     <div class="profile select-box">
        <span class="profile__image"></span>
        <span class="profile__nickname profile__nickname">{{ reply.nickname }}</span>
        <span class="profile__time">{{ reply.elapsedTime }}</span>
        <button></button>
        <div class="modal-select select-box__options comment__kebob z-idx-1">
            <div class="modal-select__contents modal__on-btn" data-id="comment-report" data-modal="#modal-report-comment">답글 신고
                <span class="icon icon-siren-red"></span>
            </div>
            </div>
        </div>
    <div class="reply-container">
        <span class="reply__to">{{reply.parentNickname?'@'+reply.parentNickname:reply.parentNickname }}</span>
        <span class="reply__content">{{reply.content}}</span>
    </div>
        <div class="reply__replies">
        <span class="pointer underline modal__on-btn reply-to-reply" @click="inputBoxToggle('replyWrite')" v-show="replyWrite">답글 달기</span> 
    </div>
    <InputBox :reply="props.reply" v-show="hasBox" @cancelClicked="inputBoxToggle" @registerCompleted="registerFinish"/>
</template>
<style >
  @import url(@/assets/css/meeting/component/select.css);
  @import url(@/assets/css/meeting/component/profile-box.css);
  @import url(@/assets/css/meeting/component/reply.css);
 
</style>