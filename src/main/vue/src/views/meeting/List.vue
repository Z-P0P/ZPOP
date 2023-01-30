<script setup>
import { reactive, computed, ref, watch, onUnmounted } from "vue";
import { useRouter, useRoute } from "vue-router";
import api from "@/api/";
import { useMeetingListStore } from "@/stores/meetingListStore";
import Thumbnail from "@/components/meeting/Thumbnail.vue";
import OptionControll from "@/components/meeting/option-control/OptionControl.vue";
import SearchBar from "@/components/meeting/option-control/SearchBar.vue";
import ModalDefault from "@/components/modal/Default.vue";
import Banner from "../../components/meeting/Banner.vue"
import { ServerException } from "@/utils/ServerException";
import LoginProc from "../LoginProc.vue";
import ScrollUpBtn from "@/components/meeting/ScrollUpBtn.vue";
import RegisterMeetingBtn from "@/components/meeting/RegisterMeetingBtn.vue";
import PageLoader from "../../components/PageLoader.vue";

const emit = defineEmits(["throw"]);
const route = useRoute();
// 검색을 위한 router
const router = useRouter();

const meetingListStore = useMeetingListStore();
meetingListStore.$reset(); // 이 view로 오면 상태 초기화

const firstLoaded = ref(false);
const isBannerLoaded = ref(false);

const state = reactive({
  meetings: [],
  lastId: null,
  throttling: null,
  loadingOn: false,
  isResultNone: computed(() => {
    return state.meetings.length === 0;
  }),
});

// meetingListStore의 상태가 변경될 때마다 모임 리스트를 새로 조회한다 --------------
watch(meetingListStore, async () => {
  state.lastId = null;
  const list = await requestList();
  resetMeetigns();
  if (!list || list.length === 0) return;
  addMeetings(list);
});

// 첫 meeting list page 조회시 호출
requestList().then((data) => {
  if (!data || data.length === 0) return;
  addMeetings(data);
  firstLoaded.value = true;
});

// 무한 스크롤 -----------------------------------------------------------
const scrollDirection = ref(0); // 스크롤 방향 정보 1: up -1 : down
const scrollTrace = ref(0); // 이전 스크롤 방향을 추적

function onScrollDown(e) {
  if (state.meetings === 0) return;
  // 스크롤 방향 추적해서 방향 업데이트하기
  const currentScrollY = window.scrollY;

  if (currentScrollY > scrollTrace.value) scrollDirection.value = -1;
  else if (currentScrollY < scrollTrace.value) scrollDirection.value = 1;
  scrollTrace.value = currentScrollY;

  const { scrollHeight, scrollTop, clientHeight } = document.documentElement;

  // 쓰로틀링 통과, 스크롤이 화면의 아래 위치, 스크롤 방향이 아래일 때 다음 리스트 요청
  if (
    !state.throttling &&
    scrollTop + clientHeight > scrollHeight - 5 &&
    scrollDirection.value < 0
  ) {
    state.loadingOn = true;
    state.throttling = setTimeout(async () => {
      state.throttling = null;
      state.loadingOn = false;
      state.lastId = state.meetings[state.meetings.length - 1].id;

      const list = await requestList();

      if (!list || list.length === 0) return;
      addMeetings(list);
    }, 300);
  }
}

document.addEventListener("scroll", onScrollDown);
onUnmounted(() => {
  document.removeEventListener("scroll", onScrollDown);
})

// 모임 리스트 요청 후 데이터 리턴 ----------------------------------------------
async function requestList() {
  try {
    const res = await api.meeting.getThumbnailList(
      generateParamsWithStore(meetingListStore)
    );
    if (!res.ok) throw new ServerException(res);
    return await res.json();
  } catch (e) {
    if (e.res.status === 500) emit("throw");
  }
}

function resetMeetigns() {
  state.meetings = [];
}

function addMeetings(data) {
  for (const m of data) {
    state.meetings.push(m);
  }
}

function generateParamsWithStore(meetingListStore) {
  const params = {};

  // 토글 checked === true -> 모집 중인 모임만 보기
  if (meetingListStore.isToggleOn) params.isClosed = false;
  // 카테고리
  if (meetingListStore.category && meetingListStore.category != 0)
    params.category = meetingListStore.category;
  // 지역 옵션
  if (meetingListStore.regions && meetingListStore.regions.length !== 0)
    params.regions = meetingListStore.regions;
  // 스크롤 이벤트시 설정되는 리스트 중 마지막 요소 id
  if (state.lastId) params.start = state.lastId;

  return params;
}

// 검색 -----------------------------------------------------------
let isSearchKeywordNone = ref(false);

/**
 * {@link SearchBar} 컴포넌트 searchFromSearchBar 에 의해 실행되는 검색 함수
 */
function search(keyword) {
  if (!keyword) {
    isSearchKeywordNone.value = true;
    return;
  }
  router.push(`/search?q=${keyword}`);
}

function closeRequiredKeywordModal() {
  isSearchKeywordNone.value = false;
}

const isLoginProcOpened = ref(false);
const isNicknameRegisterOpened = ref(false);

if (route.query.login){
  isLoginProcOpened.value=true;
}

function changeLoginProcStatus (){
  isLoginProcOpened.value = !isLoginProcOpened.value;
}

function changeNicknameRegisterStatus(){
  isNicknameRegisterOpened.value = !isNicknameRegisterOpened.value;
}

function showBanner () {
  isBannerLoaded.value = true;
}

</script>

<template>
  <banner @bannerLoaded="showBanner" v-show="isBannerLoaded"/>
  <div class="content-wrap">
    <SearchBar @searchFromSearchBar="search" />
    <ModalDefault
      v-if="isSearchKeywordNone"
      @closeModal="closeRequiredKeywordModal"
    >
      <template #modal-body>검색어를 입력해주세요.</template>
    </ModalDefault>
    <OptionControll />
    <section class="meetings">
      <ul>
        <li v-for="(meeting, idx) in state.meetings" :key="idx">
          <Thumbnail :meeting="meeting" />
        </li>
      </ul>
      <div v-if="state.isResultNone" class="result-none">
        <p>찾고있는 모임이 없네요!</p>
        <p>교집합을 만들어볼까요?</p>
      </div>
    </section>
    <div class="loading-wrap">
      <PageLoader v-show="state.loadingOn"/>
    </div>
  </div>
  <LoginProc v-show="isLoginProcOpened" @close="changeLoginProcStatus" @memberRegisterRequired="changeNicknameRegisterStatus"/>
  <NicknameRegisterVue v-show="isNicknameRegisterOpened" @close="changeNicknameRegisterStatus"/>
  <ScrollUpBtn/>
  <RegisterMeetingBtn/>
  <PageLoader v-show="!firstLoaded" :isWholePage="true"/>
</template>

<style scoped>
@import url(@/assets/css/meeting/thumbnail-list.css);

.content-wrap {
  max-width: 1200px;
}

.result-none {
  padding: 80px 0;
  text-align: center;
}

.loading-wrap {
  display: flex;
  justify-content: center;
  margin: 2rem auto;
}

.scroll-up-btn{
  display:none;
}

@media (min-width:576px) {
  .scroll-up-btn{
    display:block;
  }
}

@media (min-width:576px) {
  .register-meeting-btn{
    display:none;
  }
}

.meetings{
  position:relative;
}

</style>
