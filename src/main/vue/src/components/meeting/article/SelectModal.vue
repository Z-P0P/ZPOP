<template>
    <!-- 작성자 -->
    <div class="modal-select select-box__options" v-if="role === 'writer'">
        <div class="modal-select__contents">복사 하기
            <span class="icon icon-copy"></span>
        </div>
        <div class="modal-select__contents">수정
            <span class="icon icon-edit"></span>
        </div>
        <div class="modal-select__contents modal-select__contents--delete">삭제
            <span class="icon icon-trash"></span>
        </div>
    </div>
    <!-- 참여자 -->
    <div class="modal-select select-box__options" v-if="role === 'participant'" id="meeting__article-tool">
        <div class="modal-select__contents">복사하기
            <span class="icon icon-copy"></span>
        </div>
        <div class="modal-select__contents">참여링크
            <span class="icon icon-link"></span>
        </div>
        <div class="modal-select__contents modal-select__contents--report">글 신고
            <span class="icon icon-siren-red"></span>
        </div>
    </div>
    <!-- 기본 유저 -->
    <div class="modal-select select-box__options" v-if="role === 'member'" id="meeting__article-tool">
        <div class="modal-select__contents">복사 하기
            <span class="icon icon-copy"></span>
        </div>
        <div class="modal-select__contents modal-select__contents--report">글 신고
            <span class="icon icon-siren-red"></span>
        </div>
    </div>
</template>

<script setup>
const props = defineProps(['role']);

</script>

<style scoped>
.modal-select{
    right:20px;
}
</style>