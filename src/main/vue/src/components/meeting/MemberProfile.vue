<script setup>
import { reactive, ref } from "vue";
import ReportUser from "../member/ReportUser.vue";
import api from "@/api";
import { ServerException } from "@/utils/ServerException";

const props = defineProps({
  memberId: Number,
});
const emit = defineEmits(["closeModal"]);

let isResigned = ref(false);
let isReportModalOpened = ref(false);

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
}

function openReportUserModal() {
  isReportModalOpened.value = true;
  //emit('closeModal')
}

function closeModal() {
  isReportModalOpened.value = false;
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
              <img v-bind:src="state.member.profileImg" class="image" alt="" />
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
              @click.prevent="openReportUserModal"
              class="btn btn-semiround report modal__on-btn"
              data-id="member-report"
              data-modal="#modal-report-member"
            >
              <span class="icon icon-siren-white"></span>
              신고하기
            </span>

            <span class="btn btn-semiround kick">
              <span class="icon icon-door"></span>
              내보내기
            </span>
          </div>
        </div>
      </div>
    </div>
  </div>
  <!-- end of sheet -->
  <ReportUser @closeModal="closeModal" v-if="isReportModalOpened" />
</template>

<style scoped>
@import url(../../../../resources/static/css/member/member/profile-modal.css);
</style>
