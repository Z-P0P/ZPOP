<script setup>
import { reactive } from "vue";
import api from "@/api";
import MyPageProfile from "@/components/member/MyProfile.vue";
  //reactive + ref => proxy 사용
    const state = reactive({
      myInfo: null,
    });
    console.log('myPage 설정중');
    // 모임 정보 조회 reactive는 view단과 model단 일치
    const getMyPage = async () => {
      try {
      const res  =  await api.member.getMyPage();
      
      const data =  await res.json();
      
      state.myInfo = data;
            
      
          } catch(e){
            console.log(e);
          }
    };

    getMyPage();


</script >

<template>
  <MyPageProfile :my-info="state.myInfo"/>
</template>
<style scoped>

</style>