<script setup>
    import {ref} from 'vue';
    import CommentModal from './CommentModal.vue';
    import { useCommentStore } from '@/stores/commentStore.js';
    import CommentReportModal from '../report/ReportComment.vue';
    import { useMemberStore } from "@/stores/memberStore";
    import { useLoginModalStore } from "@/stores/loginModalStore";
    
    const memberStore = useMemberStore();
    const loginModalStore = useLoginModalStore();
    const cmtStore = useCommentStore();
    const props = defineProps(['isMyComment', 'commentId', 'groupId']);
    const emit = defineEmits(['onEdit']);

    let reportMsg = ref("");
    if(props.groupId)
        reportMsg.value = "답글"
    else 
        reportMsg.value = "댓글"
    
    // 셀렉트 모달 on/off
    let selectModalOn = ref(true);
    // 댓글 모달 on/off
    let commentModalOn = ref(false);

    let reportModalOn = ref(false);

    const selectType = ["삭제", "수정", "신고"];
    let currentSelectType = ref("");

    function closeCommentModal() {
        commentModalOn.value = false;
        cmtStore.selectModalStatus[props.commentId] = true;
    }

    function closeReportModal(){
        reportModalOn.value = false;
    }

     function onClickDeleteSelectOption() {
        currentSelectType.value = selectType[0];
        selectModalOn.value = !selectModalOn.value;
        commentModalOn.value = !commentModalOn.value;
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
        const isLoggedIn = await memberStore.isAuthenticated();
        if (!isLoggedIn) {
          loginModalStore.handleModal();
          return;
        } else {
            reportModalOn.value = true;
        }
    }

</script>

<template>
    <div v-if="isMyComment" v-show="selectModalOn" class="modal-select select-box__options comment__kebob">
        <div class="modal-select__contents" 
                @click="onClickEditSelectOption">수정
            <span class="icon icon-edit"></span>
        </div>
        <div class="modal-select__contents modal-select__contents--delete"
                @click="onClickDeleteSelectOption">삭제
            <span class="icon icon-trash"></span>
        </div>
    </div>

    <div v-if="!isMyComment" v-show="selectModalOn" class="modal-select select-box__options comment__kebob">
        <div class="modal-select__contents modal-select__contents--report"
                @click="onClickReportSelectOption">{{ reportMsg }} 신고
            <span class="icon icon-siren-red"></span>
        </div>
    </div>
    <CommentModal
        v-show="commentModalOn"
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