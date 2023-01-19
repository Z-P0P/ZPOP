
<!-- detail vue = 화면 / article 화면의 구성 요소중 한 부분-->
<script>
import { reactive,ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import api from "@/api"; //index.js
import Article from "@/components/meeting/Article.vue";
import Participants from "@/components/meeting/Participants.vue";
import CommentList from "@/components/comment/CommentList.vue";

export default {
  name: "MeetingDetail",
  components: {
    'Article':Article,
    'Participants':Participants,
    'CommentList':CommentList,
  },
  setup() {
    const state = reactive({
      detail: {},
    });
    // 모임 정보 조회 reactive는 view단과 model단 일치
    const route = useRoute();
    const getDetail = () => {
      try {
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

    const newComment = () =>{
      getDetail(route.params.id);
    }
    function increaseCounter(){
      state.detail.commentCount++;
    }
    
    return { state,newComment,getDetail, increaseCounter}; //script에서 return된 값이 model
  }

}
</script>
<template>
  <div class="content-wrap">
  <Article :article="state.detail"/>
  <Participants :detail="state.detail"/> 
  <CommentList :detail="state.detail" @newComment="newComment" @counterIncreased="increaseCounter"/> 
  

  </div>
</template>
<style scoped>
       
.content-wrap{
  
  max-width: 783px;
  width: 100%;
  padding: 1.2rem;
 }
  @media (min-width: 576px) {
.content-wrap{
     padding: 4rem;
  }
}
</style>