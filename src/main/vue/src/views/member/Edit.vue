
<script setup>
	import { reactive, onMounted } from "vue";
	import ProfileEdit from "@/components/member/ProfileEdit.vue";
  import {useMemberStore} from   "@/stores/memberStore"
</script>
<template>
  <div class="content-wrap">
  
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