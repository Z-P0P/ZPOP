<template>
    <div class="participant">
      <img
        class="participant__image"
        :src="userDetail.profileImagePath 
          ? userDetail.profileImagePath
          : '/images/icon/user-profile-grey.svg'">
      <span>{{ props.userDetail.nickname }}</span>
    </div>
</template>

<script setup>
import { defineProps } from 'vue';

const props = defineProps({
  userDetail: Object
})

console.log(props.userDetail);
</script>

<style scoped>
.participant {
  width: 220px;
  height: 46px;
  border-radius: 1.625rem;
  display: flex; 
  flex-grow: 0;
  cursor: pointer;
  align-items: center;
  padding-left: 6px;
}

@media (min-width:576px){ 
  .participant {
    width: 240px;
    height: 52px;
  }
 }

.participant:hover{
  background-color: var(--main-color);
  color: var(--white);
}

.participant__image {
  width: 36px;
  height: 36px;
  margin-right: 15px;
}

@media (min-width:576px){  
  .participant__image {
    width: 42px;
    height: 42px;
  }
}
</style>