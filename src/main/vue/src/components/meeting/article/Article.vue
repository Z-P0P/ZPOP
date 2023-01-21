<template>
  <article>
    <header>
      <div class="category-wrap">
        <span class="category">{{ article.categoryName }}</span>
      </div>
      <div class="title-container">
        <h2 class="title">{{ article.title }}</h2>
        <span class="kebab icon icon-kebab"></span>
        <img src="" alt="" />
      </div>

      <div class="start-datetime-container">
        <span class="icon icon-calender16"></span>
        <span>{{ article.textStartedAt }}</span>
      </div>
      <div class="region-container">
        <span class="icon-location16"></span>

        <span>{{ article.region }}</span>
        <span>{{ article.regionName }}</span>
      </div>
      <div class="region-detail-wrap">
        <span>{{ article.detailRegion }}</span>
      </div>
    </header>

    <div class="content-container">
      <span v-html="article.content"></span>
      <ul class="tags">
        <li>#{{ article.ageRange }} 선호</li>
        <li>#{{ article.maxMember }} 명</li>
        <li>#{{ article.genderCategory }}</li>
      </ul>
      <div class="views">조회수 {{ article.viewCount }}회</div>
      <div class="control-btn-wrap">
        <Round v-if="article.myMeeting && !article.closed">
          <template #content> 마감하기 </template>
        </Round>
        <Round
          v-else-if="
            !article.myMeeting && !article.hasParticipated && !article.closed
          "
          @click.prevent="onClickParticipationBtn"
        >
          <template #content> 참여하기 </template>
        </Round>
        <Round
          v-else-if="!article.myMeeting && article.hasParticipated"
          @click.prevent="onClickLeaveBtn"
        >
          <template #content> 참여취소 </template>
        </Round>
        <RoundDisabled v-else-if="article.closed">
          <template #content> 모집완료 </template>
        </RoundDisabled>
      </div>
      <ControlModal
        v-if="controlModalOn"
        :controlType="currentControlType"
        :articleTitle="article.title"
        @closeModal="closeControlModal"
        @refresh="emit('refresh')"
      ></ControlModal>
    </div>
  </article>
</template>

<script setup>
import { ref } from "vue";
import { useMemberStore } from "@/stores/memberStore";
import { useLoginModalStore } from "@/stores/loginModalStore";
import Round from "@/components/button/Round.vue";
import RoundDisabled from "@/components/button/RoundDisabled.vue";
import ControlModal from "./ControlModal.vue";

const memberStore = useMemberStore();
const loginModalStore = useLoginModalStore();

const props = defineProps({
  article: Object,
});
// refresh : 모임 상세 조회 최신화
const emit = defineEmits(["refresh"]);

// 참여, 마감, 참여취소, 링크 모달 on/off
let controlModalOn = ref(false);

const controlType = ["참여", "참여취소", "마감", "참여링크"];
let currentControlType = ref("");

function closeControlModal() {
  controlModalOn.value = false;
}

async function onClickParticipationBtn() {
  const isLoggedIn = await memberStore.isAuthenticated();
  if (!isLoggedIn) {
    loginModalStore.handleModal();
    return;
  }
  currentControlType.value = controlType[0];
  controlModalOn.value = !controlModalOn.value;
}

async function onClickLeaveBtn() {
  const isLoggedIn = await memberStore.isAuthenticated();
  if (!isLoggedIn) {
    loginModalStore.handleModal();
    return;
  }

  currentControlType.value = controlType[1];
  controlModalOn.value = !controlModalOn.value;
}
</script>

<style>
@import url(@/assets/css/meeting/article.css);
</style>
