<script setup>
import { useMemberStore } from "@/stores/memberStore";
import MyPageProfile from "@/components/member/MyProfile.vue";
  const memberStore = useMemberStore();
  const memberId = memberStore.id;
  const nickname = memberStore.nickname;
  const fame = memberStore.fame;

    // const state = reactive({
    //   nickname: memberStore.nickname,
    //   fame : memberStore.fame
    // });
    // const getMyProfile = async () => {
    //   try {
    //   const res  =  await api.member.getMyProfile();
      
    //   const data =  await res.json();
      
    //   state.myInfo = data;
    //       } catch(e){
    //         console.log(e);
    //       }
    // };

    // getMyProfile();


</script >

<template>
  <div class="content-wrap">
    <MyPageProfile />
  <!-- <MyPageProfile :my-info="state.myInfo"/> -->
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