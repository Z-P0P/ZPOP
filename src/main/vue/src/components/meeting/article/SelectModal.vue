<template>
    <!-- 작성자 -->
    <div class="modal-select select-box__options" v-if="role === 'writer'">
        <div @click="onClickCopy" class="modal-select__contents">복사 하기
            <span class="icon icon-copy"></span>
        </div>
        <div @click="onClickUpdate" class="modal-select__contents">수정
            <span class="icon icon-edit"></span>
        </div>
        <div @click="onClickDelete" class="modal-select__contents modal-select__contents--delete">삭제
            <span class="icon icon-trash"></span>
        </div>
    </div>
    <!-- 참여자 -->
    <div class="modal-select select-box__options" v-if="role === 'participant'" id="meeting__article-tool">
        <div @click="onClickCopy" class="modal-select__contents">복사하기
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
        <div @click="onClickCopy" class="modal-select__contents">복사 하기
            <span class="icon icon-copy"></span>
        </div>
        <div class="modal-select__contents modal-select__contents--report">글 신고
            <span class="icon icon-siren-red"></span>
        </div>
    </div>
</template>

<script setup>
import { ref, reactive, nextTick } from "vue";
import { useRoute, useRouter } from 'vue-router';
import { useMemberStore } from "@/stores/memberStore";
import { useMeetingDetailStore } from "@/stores/meetingDetailStore";
import { useLoginModalStore } from "@/stores/loginModalStore"
import api from "@/api";
import { ServerException } from "@/utils/ServerException";

const route = useRoute();
const router = useRouter();

const memberStore = useMemberStore();
const meetingDetailStore = useMeetingDetailStore();
const loginModalStore = useLoginModalStore();

const props = defineProps(['role']);
const emit = defineEmits(["on-click-delete", "on-click-copy"]);

function onClickCopy() {
    navigator.clipboard.writeText(window.location.href);
    emit("on-click-copy");
}

function onClickUpdate() {
    // 권한 확인
    if(memberStore.id !== meetingDetailStore.regMemberId
        || !meetingDetailStore.myMeeting) {
        loginModalStore.show();
        return;
    }
    
    router.push(`/meeting/update/${route.params.id}`)
}

function onClickDelete() {
    emit("on-click-delete");
}
</script>

<style scoped>
.modal-select{
    right:20px;
}
</style>