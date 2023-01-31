<script setup>
import { useMemberStore } from "@/stores/memberStore";
import MyPageProfile from "@/components/member/MyProfile.vue";
  const memberStore = useMemberStore();
  const memberId = memberStore.id;
  const nickname = memberStore.nickname;
  const fame = memberStore.fame;

</script >

<template>
  <div class="content-wrap">
    <MyPageProfile />
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