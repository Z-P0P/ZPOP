<script setup>
import { reactive, ref } from "vue";
import ReportUser from "../member/ReportUser.vue";
import { useMeetingDetailStore } from "@/stores/meetingDetailStore";
import { useMemberStore } from "@/stores/memberStore";
import api from "@/api";
import { ServerException } from "@/utils/ServerException";

const meetingDetailStore = useMeetingDetailStore();
const memberStore = useMemberStore();

const props = defineProps({
  memberId: Number,
});
const emit = defineEmits(["closeModal"]);

let isResigned = ref(false);

const state = reactive({
  member: {
    id: 0,
    nickname: null,
    fame: 0,
    participatedCount: 0,
    profileImg: null,
  },
});

async function getMemberProfile(id) {
  try {
    const res = await api.member.getProfile(id);
    if (!res.ok) throw new ServerException(await res.json());
    const data = await res.json();
    setMemberProfile(data);
  } catch (e) {}
}

getMemberProfile(props.memberId);

function setMemberProfile(data) {
  if (data.resigned) console.log("TODO: 구현");
  Object.keys(data).forEach((key) => {
    state.member[key] = data[key];
  });
  setKickOn();
  setReportOn();
}

function closeModal() {
  reportModalOn.value = false;
}

// 신고 --------------------------------------------------------------------------
let reportOn = true;
const reportModalOn = ref(false);

function setReportOn() {
  // 내 프로필 보기라면, 신고하기 비활성화
  if (state.member.id === memberStore.id) {
    reportOn = false;
  }
}

function onClickReport() {
  reportModalOn.value = true;
  //emit('closeModal')
}

// 내보내기 ----------------------------------------------------------------------
let kickOn = false;
const kickModalOn = ref(false);

function setKickOn() {
  // 내 모임 AND 나 외의 참여자라면, 내보내기 활성화
  if (
    meetingDetailStore.myMeeting &&
    meetingDetailStore.regMemberId !== state.member.id
  )
    kickOn = true;
}

async function onClickKick() {
  kickModalOn.value = true;
}
</script>

<template>
  <div id="sheet" class="column items-center justify-end">
    <div class="overlay"></div>
    <div class="contents column">
      <header class="controls">
        <div class="draggable-area">
          <div class="draggable-thumb"></div>
        </div>
        <span
          @click.prevent="$emit('closeModal')"
          class="close-sheet icon icon-x"
        ></span>
      </header>

      <div class="body column">
        <div class="profile-container">
          <div class="image-wrap">
            <div class="image-bg">
              <img :src="state.member.profileImg" class="image" alt="" />
            </div>
          </div>

          <div class="user__info">
            <div class="user__info-fixed">
              <span>닉네임</span>
              <span v-if="!isResigned">집합 참여</span>
              <span v-if="!isResigned">인기도</span>
            </div>

            <div class="user__info-flexible">
              <span>{{ state.member.nickname }}</span>
              <span v-if="isResigned" class="resigned-member"
                >탈퇴한 회원입니다!</span
              >
              <span v-if="!isResigned"
                >{{ state.member.participatedCount }} 회</span
              >
              <span v-if="!isResigned">{{ state.member.fame }} 점</span>
            </div>
          </div>

          <div v-if="!isResigned" class="ban__wrap">
            <span
              v-show="reportOn"
              @click.prevent="onClickReport"
              class="btn btn-semiround report modal__on-btn"
              data-id="member-report"
              data-modal="#modal-report-member"
            >
              <span class="icon icon-siren-white"></span>
              신고하기
            </span>

            <span
              v-show="kickOn"
              @click="onClickKick"
              class="btn btn-semiround kick"
            >
              <span class="icon icon-door"></span>
              내보내기
            </span>
          </div>
        </div>
      </div>
    </div>
  </div>
  <ReportUser @closeModal="closeModal" v-if="reportModalOn" />
</template>

<style scoped>
@import url(../../../../resources/static/css/member/member/profile-modal.css);
</style>
