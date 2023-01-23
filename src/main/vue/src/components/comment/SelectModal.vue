<template>
    <!-- 💚 댓글 (작성자)-->
    <div v-if="role==='writer'" class="modal-select select-box__options">
        <div class="modal-select__contents" data-id="comment-edit">수정
            <span class="icon icon-edit"></span>
        </div>
        <div class="modal-select__contents modal-select__contents--delete" data-id="comment-delete">삭제
            <span class="icon icon-trash"></span>
        </div>
    </div>

    <!-- 💚 댓글 (기본유저)-->
    <div v-if="role==='member'" class="modal-select select-box__options">

        <div class="modal-select__contents mocal-select__contents--report" data-id="comment-report">
            댓글 신고
            <span class="icon icon-siren-red"></span>
        </div>
    </div>
    <!--------------------------  케밥 모달 추가 --------------------------->
</template>

<script setup>
const props = defineProps(['role']);

</script>

<style scoped>
.select-box__options{
    max-height:max-content;
    overflow: visible;
}
</style>