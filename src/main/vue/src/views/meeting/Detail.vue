<!-- detail vue = 화면 / article 화면의 구성 요소중 한 부분-->
<script setup>
import { useRoute, useRouter } from "vue-router";
import { useMeetingDetailStore } from "@/stores/meetingDetailStore";
import api from "@/api"; //index.js
import Article from "@/components/meeting/article/Article.vue";
import ParticipantList from "@/components/meeting/ParticipantList.vue";
import CommentList from "@/components/comment/CommentList.vue";
import { ServerException } from "@/utils/ServerException";

// 모임 정보 조회 reactive는 view단과 model단 일치
const route = useRoute();
const router = useRouter();
const detailStore = useMeetingDetailStore();
detailStore.$reset();

const getDetail = async () => {
  try {
    const res = await api.meeting.getDetail(route.params.id);
    if (!res.ok) throw new ServerException(res);
    const data = await res.json();
    detailStore.init(data);
  } catch (e) {
    console.log(e);
    // 존재하지 않는 meeting id
    if (e.res.status === 404) router.push("/404");
  }
};

getDetail();

</script>
<template>
  <div class="content-wrap">
    <Article />
    <Participants />
    <CommentList />
  </div>
</template>
<style scoped>
.content-wrap {
  max-width: 783px;
  width: 100%;
  padding: 1.2rem;
}
@media (min-width: 576px) {
  .content-wrap {
    padding: 4rem;
  }
}
</style>
