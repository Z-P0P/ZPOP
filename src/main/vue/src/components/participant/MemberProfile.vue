<script setup>
import { reactive, ref } from "vue";
import ReportUser from "@/components/report/ReportUser.vue";
import DefaultModal from "@/components/modal/Default.vue";
import { useMeetingDetailStore } from "@/stores/meetingDetailStore";
import { useMemberStore } from "@/stores/memberStore";
import api from "@/api";
import { ServerException } from "@/utils/ServerException";

const meetingDetailStore = useMeetingDetailStore();
const memberStore = useMemberStore();

const props = defineProps({
  memberId: Number,
});
const emit = defineEmits(["closeModal", "onKickModal"]);

const state = reactive({
  member: {
    id: 0,
    nickname: null,
    fame: 0,
    participatedCount: 0,
    profileImg: null,
    resigned: false,
  },
});

async function getMemberProfile(id) {
  try {
    const res = await api.member.getProfile(id);
    if (!res.ok) throw new ServerException(await res.json());
    const data = await res.json();
    setMemberProfile(data);
  } catch (e) {
    if (e.res.status === 404) router.push("/404");
  }
}

getMemberProfile(props.memberId);

function setMemberProfile(data) {
  if (data.resigned) {
    state.member.resigned = true;
    setKickOn();
    return;
  }
  Object.keys(data).forEach((key) => {
    state.member[key] = data[key];
  });
  // 내보내기, 신고 렌더링 여부 설정
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

function setKickOn() {
  // 모임이 활성화 되어있고, 내 모임이고, 나 외의 참여자라면
  // 내보내기 활성화한다
  if (
    !meetingDetailStore.closed &&
    meetingDetailStore.myMeeting &&
    meetingDetailStore.regMemberId !== state.member.id
  )
    kickOn = true;
}

async function onClickKick() {
  emit("onKickModal", {
    id: state.member.id,
    nickname: state.member.nickname
  });
  emit("closeModal");
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
        <span @click.prevent="$emit('closeModal')" class="close-sheet icon icon-x"></span>
      </header>

      <div class="body column">
        <!-- resigend profile -->
        <div v-if="state.member.resigned" class="profile-container">
          <div class="image-wrap">
            <div class="image-bg">
              <img src="../../../public/images/no-rusult-ghost.svg" class="image" alt="resigned" />
            </div>
          </div>
          <div class="resigned">
            <span>탈퇴한 회원이에요</span>
            <div class="ban__wrap">
              <span v-show="kickOn" @click="onClickKick" class="btn btn-semiround kick">
                <span class="icon icon-door"></span>
                내보내기
              </span>
            </div>
          </div>
        </div>
        <!-- nomal profile -->
        <div v-else="state.member.resigned" class="profile-container">
          <div class="image-wrap">
            <div class="image-bg">
              <img
                :src="
                  (state.member.profileImg != null && state.member.profileImg != '')
                    ? `/image/profile/${state.member.profileImg}`
                    : '/images/icon/user-icon-white.svg'
                "
                class="image"
                alt="member-image"
              />
            </div>
          </div>

          <div class="user__info">
            <div class="user__info-fixed">
              <span>닉네임</span>
              <span v-if="!state.member.resigned">집합 참여</span>
              <span v-if="!state.member.resigned">인기도</span>
            </div>
            <div class="user__info-flexible">
              <span>{{ state.member.nickname }}</span>
              <span>{{ state.member.participatedCount }} 회</span>
              <span>{{ state.member.fame }} 점</span>
            </div>
          </div>
          <div class="ban__wrap">
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
            <span v-show="kickOn" @click="onClickKick" class="btn btn-semiround kick">
              <span class="icon icon-door"></span>
              내보내기
            </span>
          </div>
        </div>
      </div>
    </div>
  </div>
  <ReportUser @closeModal="closeModal" v-if="reportModalOn" :reportedMemberId="props.memberId"/>
</template>

<style scoped>
@import url(@/assets/css/member/profile-modal.css);
.resigned {
  margin-top: 10px;
  display: flex;
  flex-direction: column;
  align-items: center;
}
</style>
