<template>
    <!-- 댓글 (작성자)-->
    <div v-if="role === 'writer'" v-show="selectModalOn" class="modal-select select-box__options comment__kebob">
        <div class="modal-select__contents" data-id="comment-edit">수정
            <span class="icon icon-edit"></span>
        </div>
        <div class="modal-select__contents modal-select__contents--delete"
        @click="onClickDeleteSelectOption">삭제
            <span class="icon icon-trash"></span>
        </div>
    </div>

    <!-- 댓글 (기본유저)-->
    <div v-if="role === 'member'" class="modal-select select-box__options comment__kebob">

        <div class="modal-select__contents mocal-select__contents--report" data-id="comment-report">
            댓글 신고
            <span class="icon icon-siren-red"></span>
        </div>
    </div>
    <CommentModal
        v-if="commentModalOn"
        :selectType="currentSelectType"
        :commentId="commentId"
        @closeModal="closeCommentModal"
      ></CommentModal>
</template>

<script setup>
    import {ref} from 'vue';
    import CommentModal from './CommentModal.vue';
    import { useCommentStore } from '@/stores/commentStore.js';
    
    const cmtStore = useCommentStore();
    const props = defineProps(['role', 'commentId', 'meetingId']);
    
    console.log(props.role + " : "+ props.commentId)
    // 셀렉트 모달 on/off
    let selectModalOn = ref(true);
    // 댓글 모달 on/off
    let commentModalOn = ref(false);

    const selectType = ["삭제", "신고"];
    let currentSelectType = ref("");

    function closeCommentModal() {
        commentModalOn.value = false;
    }

    async function onClickDeleteSelectOption() {
        currentSelectType.value = selectType[0];
        commentModalOn.value = !commentModalOn.value;
        selectModalOn.value = !selectModalOn.value;
    }


</script>

<style scoped>
.select-box__options {
    max-height: max-content;
    overflow: visible;
    top: 0%;
}
</style>