<script setup>
    import { defineProps, defineEmits, ref, onMounted } from 'vue';
    import api from "@/api";
    import { useCommentStore} from '@/stores/commentStore'

    const cmtStore = useCommentStore();
    const props = defineProps({
        reply:Object
    });
    const emit = defineEmits([
        'cancelClicked',
        'registerCompleted'
    ]);
    let groupId = 0;
    if(props.reply.groupId == 0)
        groupId = props.reply.id; //parent에서 내려온 객체가 댓글(comment)이면 groupId=0임.
    else 
        groupId = props.reply.groupId;

    const inputs = {f1:ref()}
    onMounted(() => {
        const input = inputs['f1'].value;
        input.focus();
    })
    function cancelWrite(){
        emit('cancelClicked');
    }

    function registerReply(){
        const data = {};
        data.meetingId = props.reply.meetingId;
        data.parentCommentId = props.reply.id;
        data.groupId = groupId;
        const inputBox =  inputs['f1'].value;
        data.content = inputBox.value;
        const dataJSONStr = JSON.stringify(data);
        api.comment.registerReply(dataJSONStr)
        .then(async res=>{
            if(res.ok){
                console.log("답글 등록됨");
                const data = await cmtStore.getReplyList(groupId);
                cmtStore.comment.id = groupId;
                cmtStore.comment.replyList.length=0;
                for(const r of data.resultObject) {
                    cmtStore.comment.replyList.push(r);
                }
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
        placeholder="답글을 입력하세요." :ref="inputs.f1"></textarea>
        <div class="reply__btn-container">
            <span class="reply__btn btn btn-round btn-cancel cancel-btn" id="reply-cancel" @click="cancelWrite">취소하기</span>
            <span class="reply__btn btn btn-round btn-action register-btn" @click="registerReply('f1')">등록하기</span>
        </div>
        </div> 
    </template>
<style>
    @import url(@/assets/css/meeting/component/comment.css);
    @import url(@/assets/css/meeting/component/reply.css);
</style>


