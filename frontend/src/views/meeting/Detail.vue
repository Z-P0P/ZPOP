<template>
  <Article :article="state.article" />
</template>

<script>
import { reactive } from "vue";
import { useRoute } from "vue-router";
import api from "@/api/";
import Article from "@/components/meeting/Article.vue";

export default {
  name: "MeetingDetail",
  components: {
    Article,
  },

  setup() {
    const state = reactive({
      article: {},
    });

    // 모임 정보 조회
    const getDetail = () => {
      try {
        const route = useRoute();
        api.meeting
          .getDetail(route.params.id)
          .then((res) => res.json())
          .then((data) => (state.article = data));
      } catch (e) {
        console.log(e);
        alert("잠시 후에 다시 시도해주세요");
      }
    };

    getDetail();

    // 참여자 정보 조회

    // 댓글 정보 조회

    return { state };
  },
};
</script>

<style scoped></style>
