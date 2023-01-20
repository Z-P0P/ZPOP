<script setup>
import { reactive } from "vue";
import { useMemberStore } from "@/stores/memberStore";
import api from "@/api";
import MyPageProfile from "@/components/member/MyProfile.vue";
  const memberStore = useMemberStore();
  const memberId = memberStore.id;

    const state = reactive({
      myInfo: null,
    });
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