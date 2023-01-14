<script setup>
import { reactive, computed, watch, ref } from "vue";
import { useRoute } from "vue-router";
import api from "@/api/";
import Thumbnail from "@/components/meeting/Thumbnail.vue";
import LoadingRoller from "@/components/LoadingRoller.vue";

const route = useRoute();

const keyword = route.query.q; // 검색어

const state = reactive({
  meetings: [],
  lastId: null,
  throttling: null,
  loadingOn: false,
  isResultNone: computed(() => {
    return state.meetings.length === 0;
  }),
  isToggleOn: true,
});

// isToggleOn의 상태가 변경될 때마다 모임 리스트를 새로 조회한다 --------------
watch(state.isToggleOn, async () => {
  state.lastId = null;
  const list = await requestList();
  resetMeetigns();
  if (!list || list.length === 0) return;
  addMeetings(list);
});

// 첫 meeting search page 조회시 호출
requestList().then((data) => {
  if (!data || data.length === 0) return;
  addMeetings(data);
});

// 모임 리스트 요청 후 데이터 리턴 ----------------------------------------------
async function requestList() {
  try {
    const res = await api.meeting.getThumbnailList(generateSearchParams(state));
    return await res.json();
  } catch (e) {
    // TODO: 에러 모달
    console.log(e);
    alert("잠시후에 다시 시도해주세요");
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

function generateSearchParams(state) {
  const params = {};

  // 토글 checked === true -> 모집 중인 모임만 보기
  if (state.isToggleOn) params.isClosed = false;

  // 스크롤 이벤트시 설정되는 리스트 중 마지막 요소 id
  if (state.lastId) params.start = state.lastId;

  params.keyword = keyword;

  return params;
}

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
</script>

<template>
  <div class="content-wrap">
    <div class="search-result">
      <h2>
        <strong>{{ keyword }}</strong> 검색결과
      </h2>
    </div>
    <div>
      <!-- TODO: 토글 -->
    </div>
    <section class="meetings">
      <ul>
        <li v-for="(meeting, idx) in state.meetings" :key="idx">
          <Thumbnail :meeting="meeting" />
        </li>
      </ul>
      <div v-if="state.isResultNone" class="result-none">
        <p>
          '<strong class="search-keyword">{{ keyword }}</strong
          >'에 대한 검색 결과가 없어요 !
        </p>
        <p>검색할 단어를 변경하거나, 검색어를 확인해주세요.</p>
      </div>
    </section>

    <div class="loading-wrap">
      <LoadingRoller :isShow="state.loadingOn" />
    </div>
  </div>
</template>

<style scoped>
@import url(@/assets/css/meeting/thumbnail-list.css);

.content-wrap {
  max-width: 1200px;
}

.search-result {
  width: 100%;
  padding-top: 2rem;
}

.search-result > h2 {
  font-size: 20px;
}

.search-result > h2 > strong {
  font-weight: bold;
}

.result-none {
  display: flex;
  align-items: center;
  flex-direction: column;
  color: var(--dark-grey2);
  padding: 80px 0;
}

.result-none strong {
  font-weight: bold;
}

@media (min-width: 768px) {
  .result-none {
    font-size: 20px;
  }
}

.loading-wrap {
  display: flex;
  justify-content: center;
  margin: 2rem auto;
}
</style>
