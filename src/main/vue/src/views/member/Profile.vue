<script setup>
import { reactive } from "vue";
import { useMemberStore } from "@/stores/memberStore";
import api from "@/api";
import MyPageProfile from "@/components/member/MyProfile.vue";
  //reactive + ref => proxy 사용
  const memberStore = useMemberStore();
  const memberId = memberStore.id;

  // // store.getInfo(); 
  // console.log(store);
    const state = reactive({
      myInfo: null,
    });
  //   console.log('myPage 설정중');
  //   // 모임 정보 조회 reactive는 view단과 model단 일치
    const getMyProfile = async () => {
      try {
      const res  =  await api.member.getMyProfile();
      
      const data =  await res.json();
      
      state.myInfo = data;
          } catch(e){
            console.log(e);
          }
    };

    getMyProfile();


</script >

<template>
  <div class="content-wrap">
  <MyPageProfile :my-info="state.myInfo"/>
  </div>
</template>
<style scoped>
 .content-wrap{
  padding: 20px;
 }

 @media (min-width: 576px) {
     .content-wrap{
       padding: 4rem;
    }
  }
</style>