<script>
import { reactive, computed, ref } from "vue";
import { useRoute } from "vue-router";
import api from "@/api/";
import Thumbnail from "@/components/meeting/Thumbnail.vue";
import LoadingRoller from "@/components/LoadingRoller.vue";

export default {
  name: "MeetingList",
  components: {
    Thumbnail,
    LoadingRoller,
  },
  setup() {
    const state = reactive({
      throttling: null,
      meetings: [],
      loadingOn: false,
      // 모임 리스트가 하나도 없음 여부
      isResultNone: computed(() => {
        return state.meetings.length === 0;
      }),
    });

    /* 모임 리스트 요청 */
    const requestThumbnailList = () => {
      try {
        //TODO: 파라미터 옵션
        api.meeting
          .getThumbnailList({})
          .then((res) => res.json())
          .then((data) => {
            for (const m of data) {
              state.meetings.push(m);
            }
          });
      } catch (e) {
        console.log(e);
        // TODO: 에러 모달
      }
    };

    requestThumbnailList();

    /* infinite scroll */
    // 스크롤 방향 정보
    const scrollDirection = ref(0); // 1: up -1 : down
    // 이전 스크롤 방향을 추적
    const scrollTrace = ref(0);

    const loadMore = (e) => {
      // 스크롤 방향 추적해서 방향 업데이트하기
      const currentScrollY = window.scrollY;

      if (currentScrollY > scrollTrace.value) scrollDirection.value = -1;
      else if (currentScrollY < scrollTrace.value) scrollDirection.value = 1;
      scrollTrace.value = currentScrollY;

      const { scrollHeight, scrollTop, clientHeight } =
        document.documentElement;

      // 쓰로틀링 통과, 스크롤이 화면의 아래 위치, 스크롤 방향이 아래일 때 다음 리스트 요청
      if (
        !state.throttling &&
        scrollTop + clientHeight > scrollHeight - 5 &&
        scrollDirection.value < 0
      ) {
        state.loadingOn = true;

        state.throttling = setTimeout(() => {
          state.throttling = null;
          state.loadingOn = false;
          requestThumbnailList();
        }, 300);
      }
    };

    document.addEventListener("scroll", loadMore);

    return { state };
  },
};
</script>

<template>
  <section>
    <ul>
      <li v-for="(meeting, idx) in state.meetings" :key="idx">
        <Thumbnail :meeting="meeting" />
      </li>
      <div v-if="state.isResultNone">
        <p>찾고있는 모임이 없네요!</p>
        <p>교집합을 만들어볼까요?</p>
      </div>
    </ul>
  </section>
  <LoadingRoller :isShow="state.loadingOn" />
</template>

<style scoped></style>
