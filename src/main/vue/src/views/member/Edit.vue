
<script setup>
	import { reactive } from "vue";
	import api from "@/api";
	import ProfileEdit from "@/components/member/ProfileEdit.vue";

	const state = reactive({
      myInfo: null,
     
    });
    // 모임 정보 조회 reactive는 view단과 model단 일치
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

</script>
<template>
  <div class="content-wrap">
  <ProfileEdit :my-info="state.myInfo"/>
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