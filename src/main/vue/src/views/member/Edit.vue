
<script setup>
	import { reactive, onMounted } from "vue";
	import api from "@/api";
	import ProfileEdit from "@/components/member/ProfileEdit.vue";
  import {useMemberStore} from   "@/stores/memberStore"
	// const state = reactive({
  //     myInfo: null,
     
  //   });
  const memberInfo = useMemberStore();
  // console.log( memberInfo.nickname);
 
  // console.log("memberinfo"+memberInfo);
  //   // 모임 정보 조회 reactive는 view단과 model단 일치
  //   const getMyProfile = async () => {
  //     try {
  //     const res  =  await api.member.getMyProfile();
      
  //     const data =  await res.json();
      
  //     state.myInfo = data;
            
      
  //         } catch(e){
  //           console.log(e);
  //         }
  //   };

  //   getMyProfile();
  

</script>
<template>
  <div class="content-wrap">
  <!-- <ProfileEdit :nickname="memberInfo.nickname"/> -->
  <ProfileEdit />
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