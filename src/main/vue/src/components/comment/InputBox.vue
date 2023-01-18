<script setup>
    import { defineProps, defineEmits, ref, onMounted } from 'vue';
    import api from "@/api";

    const props = defineProps({
      reply:Object
    });
    const emit = defineEmits(['cancelClicked',
                                'newReply']);
    function cancelWrite(){
        emit('cancelClicked');
    }

    function registerReply(groupId){
        const data = {};
        data.groupId = groupId;
        data.content = document.querySelector("#reply-text").value;
        const dataJSONStr = JSON.stringify(data);
        api.comment.registerReply(dataJSONStr)
        .then(res=>{
            if(res.ok){
            console.log("답글 등록됨");
            emit('newReply');
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
            placeholder="답글을 입력하세요."></textarea>
        <div class="reply__btn-container">
            <span class="reply__btn btn btn-round btn-cancel cancel-btn" id="reply-cancel" @click="cancelWrite">취소하기</span>
            <span class="reply__btn btn btn-round btn-action register-btn" @click="registerReply(reply.groupId)">등록하기</span>
        </div>
    </div> 
</template>
<style>
     @import url(@/assets/css/meeting/component/comment.css);
     @import url(@/assets/css/meeting/component/reply.css);
</style>