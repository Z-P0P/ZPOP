
<!-- detail vue = 화면 / article 화면의 구성 요소중 한 부분-->
<script>
import { reactive } from "vue";
import { useRoute } from "vue-router";
import api from "@/api"; //index.js
import Article from "@/components/meeting/Article.vue";
import Participants from "@/components/meeting/Participants.vue";
import Comments from "@/components/meeting/Comments.vue";

export default {
  name: "MeetingDetail",
  components: {
    Article,
    Participants,
    Comments
  },
  setup() {
    const state = reactive({
      detail: {},
    });
    // 모임 정보 조회 reactive는 view단과 model단 일치
    const getDetail = () => {
      try {
        const route = useRoute();
        api.meeting
            .getDetail(route.params.id)
            .then(res => res.json())
            .then(data => {state.detail = data});
      } catch (e) {
        console.log(e);
        alert("잠시 후에 다시 시도해주세요");
      }
    };
    getDetail();
    // 참여자 정보 조회
    // 댓글 정보 조회
    return { state }; //script에서 return된 값이 model
  },
}
</script>
<template>
  <div class="content-wrapper">
  <Article :article="state.detail"/>
  <Participants :detail="state.detail"/> 
  <Comments :detail="state.detail"/> 
  </div>
</template>
<style scoped>
   @import url(@/assets/css/component/select.css);
   @import url(@/assets/css/meeting/detail.css);
</style>