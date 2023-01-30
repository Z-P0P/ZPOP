<script setup>
    import { ref } from 'vue';
    import api from "@/api";
    import { useReplyStore} from '@/stores/replyStore'
    import { useMeetingDetailStore } from "@/stores/meetingDetailStore";
    
    const mtDetailStore = useMeetingDetailStore();
    const rplyStore = useReplyStore();
    const props = defineProps({
        reply:Object,
        isB2Active: Boolean,
        isB3Active: Boolean
    });
    const emit = defineEmits([
        'cancelClicked',
        'registerCompleted',
        'editSaveClicked'
    ]);
    let groupId = 0;
    if(props.reply.groupId == 0)
        groupId = props.reply.id; //parent에서 내려온 객체가 댓글(comment)이면 groupId=0임.
    else 
        groupId = props.reply.groupId;

    const textInputRef = ref();
    
    defineExpose({textInputRef});

    function registerReply(){
        const data = {};
        data.meetingId = props.reply.meetingId;
        data.parentCommentId = props.reply.id;
        data.groupId = groupId;
        const inputBox =  textInputRef.value;
        if(inputBox.value===""){
            alert("글을 입력해주세요")
            return 0;
        }
        data.content = inputBox.value;
        const dataJSONStr = JSON.stringify(data);
        api.comment.registerReply(dataJSONStr)
        .then(async res=>{
            if(res.ok){
                console.log("답글 등록됨");
                inputBox.value = "";
                emit('registerCompleted');
            }
            else 
                alert("시스템 장애로 등록이 안되고 있습니다")
        })
    }
    
</script>

<template>
    <div class="reply__input-container"> 
    <textarea
        id="reply-text"
        class="reply__input"
        name="reply-input"
        placeholder="답글을 입력하세요." ref="textInputRef"></textarea>
        <div class="reply__btn-container">
            <span   class="reply__btn btn btn-round btn-cancel cancel-btn" 
                @click="emit('cancelClicked')"
            >취소하기</span>
            <span   v-if="isB2Active"
                class="reply__btn btn btn-round btn-action" 
                @click="registerReply"
            >등록하기</span>
            <span   v-if="isB3Active"
                class="reply__btn btn btn-round btn-action"
                @click="emit('editSaveClicked')"
            >저장하기</span
        >
        </div>
        </div> 
    </template>
<style>
    @import url(@/assets/css/meeting/component/comment.css);
    @import url(@/assets/css/meeting/component/reply.css);
</style>


