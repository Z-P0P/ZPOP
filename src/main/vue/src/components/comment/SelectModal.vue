<script setup>
    import {ref} from 'vue';
    import CommentModal from './CommentModal.vue';
    import { useCommentStore } from '@/stores/commentStore.js';
    import CommentReportModal from '../report/ReportComment.vue';
    
    const cmtStore = useCommentStore();
    const props = defineProps(['role', 'commentId', 'groupId']);
    const emit = defineEmits(['onEdit']);
    
    console.log(props.role + " : "+ props.commentId)
    // 셀렉트 모달 on/off
    let selectModalOn = ref(true);
    // 댓글 모달 on/off
    let commentModalOn = ref(false);

    let reportModalOn = ref(false);

    const selectType = ["삭제", "수정", "신고"];
    let currentSelectType = ref("");

    function closeCommentModal() {
        commentModalOn.value = false;
    }

    function closeReportModal(){
        reportModalOn.value = false;
    }

    async function onClickDeleteSelectOption() {
        currentSelectType.value = selectType[0];
        commentModalOn.value = !commentModalOn.value;
        selectModalOn.value = !selectModalOn.value;
    }

    async function onClickEditSelectOption(e){
        e.targetId = props.commentId
        currentSelectType.value = selectType[1];
        selectModalOn.value = !selectModalOn.value;
        emit('onEdit',e);
    }
    
    async function onClickReportSelectOption(){
        currentSelectType.value = selectType[2];
        selectModalOn.value = !selectModalOn.value;
        reportModalOn.value = !reportModalOn.value;
    }

</script>

<template>
    <!-- 댓글 (작성자)-->
    <div v-if="role === 'writer'" v-show="selectModalOn" class="modal-select select-box__options comment__kebob">
        <div class="modal-select__contents" 
                @click="onClickEditSelectOption">수정
            <span class="icon icon-edit"></span>
        </div>
        <div class="modal-select__contents modal-select__contents--delete"
                @click="onClickDeleteSelectOption">삭제
            <span class="icon icon-trash"></span>
        </div>
    </div>

    <!-- 댓글 (기본유저)-->
    <div v-if="role === 'member'" v-show="selectModalOn" class="modal-select select-box__options comment__kebob">
        <div class="modal-select__contents modal-select__contents--report"
                @click="onClickReportSelectOption">댓글 신고
            <span class="icon icon-siren-red"></span>
        </div>
    </div>
    <CommentModal
        v-if="commentModalOn"
        :selectType="currentSelectType"
        :commentId="commentId"
        :groupId="groupId"
        @closeModal="closeCommentModal"
    ></CommentModal>
    <CommentReportModal 
    v-if="reportModalOn"
    :selectType="currentSelectType"
    :commentId="commentId"
    :groupId="groupId"
    @closeModal="closeReportModal"
    />
</template>

<style scoped>
.select-box__options {
    max-height: max-content;
    overflow: visible;
    top: 0%;
}
</style>