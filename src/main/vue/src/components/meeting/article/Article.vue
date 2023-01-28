<template>
  <article>
    <header>
      <div class="category-wrap">
        <span class="category">{{ meetingDetailStore.categoryName }}</span>
      </div>
      <div class="title-container">
        <h2 class="title">{{ meetingDetailStore.title }}</h2>
        <span @click="closeSelectModal" class="kebab icon icon-kebab">
          <!-- role에 writer를 바인딩하면 작성자 기준 모달, participant를 바인딩하면 참여자 기준 모달, member를 바인딩하면 일반 로그인 사용자 모달이 나온다. -->
          <SelectModal 
            @on-click-delete="onDelete"
            :role="selectModalRole"
            
            v-if="!isSelectModalClosed"
          />
        </span>
        <img src="" alt="" />
      </div>

      <div class="start-datetime-container">
        <span :class="calenderClass"></span>
        <span>{{ meetingDetailStore.textStartedAt }}</span>
      </div>
      <div class="region-container">
        <span :class="locationClass"></span>

        <span>{{ meetingDetailStore.region }}</span>
        <span>{{ meetingDetailStore.regionName }}</span>
      </div>
      <div class="region-detail-wrap">
        <span>{{ meetingDetailStore.detailRegion }}</span>
      </div>
    </header>

    <div class="content-container">
      <span v-html="meetingDetailStore.content"></span>
      <ul class="tags">
        <li>#{{ meetingDetailStore.ageRange }} 선호</li>
        <li>#{{ meetingDetailStore.maxMember }} 명</li>
        <li>#{{ meetingDetailStore.genderCategory }}</li>
      </ul>
      <div class="views">조회수 {{ meetingDetailStore.viewCount }}회</div>
      <div class="control-btn-wrap">
        <RoundDisabled v-if="meetingDetailStore.closed">
          <template #content> 모집완료 </template>
        </RoundDisabled>
        <Round
          v-else-if="meetingDetailStore.myMeeting && !meetingDetailStore.closed"
          @click.prevent="onClickCloseBtn"
        >
          <template #content> 마감하기 </template>
        </Round>
        <Round
          v-else-if="
            !meetingDetailStore.myMeeting &&
            !meetingDetailStore.hasParticipated &&
            !meetingDetailStore.closed
          "
          @click.prevent="onClickParticipationBtn"
        >
          <template #content> 참여하기 </template>
        </Round>
        <Round
          v-else-if="
            !meetingDetailStore.myMeeting && meetingDetailStore.hasParticipated
          "
          @click.prevent="onClickLeaveBtn"
        >
          <template #content> 참여취소 </template>
        </Round>
      </div>
      <ControlModal
        v-if="controlModalOn"
        :controlType="currentControlType"
        :articleTitle="meetingDetailStore.title"
        @closeModal="closeControlModal"
      ></ControlModal>
    </div>
  </article>
</template>

<script setup>
import { ref, computed } from "vue";
import { useMemberStore } from "@/stores/memberStore";
import { useLoginModalStore } from "@/stores/loginModalStore";
import { useMeetingDetailStore } from "@/stores/meetingDetailStore";
import Round from "@/components/button/Round.vue";
import RoundDisabled from "@/components/button/RoundDisabled.vue";
import ControlModal from "./ControlModal.vue";
import SelectModal from "./SelectModal.vue";


const memberStore = useMemberStore();
const loginModalStore = useLoginModalStore();
const meetingDetailStore = useMeetingDetailStore();

const width = ref(window.innerWidth)

const calenderClass = computed(() => {
      return {
        'icon icon-calender16': width.value <= 575,
        'icon icon-calendar20': width.value > 576
      }
    })
    const locationClass = computed(() => {
      return {
        'icon icon-location16': width.value <= 575,
        'icon icon-location20': width.value > 576
      }
    })
    
    window.addEventListener("resize", () => {
      width.value = window.innerWidth
    })



// 셀렉트 모달
const isSelectModalClosed = ref(true);

const selectModalRole = computed(() => {
  let role = "member";
  if(meetingDetailStore.myMeeting)
    role = "writer";
  else if(meetingDetailStore.hasParticipated)
    role = "participant"
  return role;
})

function closeSelectModal() {
  isSelectModalClosed.value = !isSelectModalClosed.value;
}

// 참여, 마감, 참여취소, 링크 모달 on/off
const controlModalOn = ref(false);

const controlType = ["참여", "참여취소", "마감", "참여링크", "삭제"];
const currentControlType = ref("");

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

async function onClickCloseBtn() {
  const isLoggedIn = await memberStore.isAuthenticated();
  if (!isLoggedIn) {
    loginModalStore.handleModal();
    return;
  }

  currentControlType.value = controlType[2];
  controlModalOn.value = !controlModalOn.value;
}

function onDelete() {
  currentControlType.value = controlType[4];
  controlModalOn.value = !controlModalOn.value;
}
</script>

<style scoped>
@import url(@/assets/css/meeting/article.css);
@import url(@/assets/css/icon.css);
.title-container .kebab{
  position:relative;
}

.title-container .icon{
  overflow: visible;
  text-indent: 0px;
}
.start-datetime-container{
  color: var(--dark-grey2);
}

@media (min-width: 576px) {
  .start-datetime-container{
  align-items: center;
}  
.region-container{
  color: var(--dark-grey2);
  align-items: center;
  display: flex;

}

.region-container > span {
  margin-right: 4px;
}

.region-detail-wrap {
  margin-left: 29px;
}
}


</style>
