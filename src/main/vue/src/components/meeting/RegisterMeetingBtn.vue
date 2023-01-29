<template>
  <span class='register-meeting-btn' @click="routeToRegisterMeeting"></span>
</template>

<script setup>
import { useRouter } from 'vue-router';

const router = useRouter();

const routeToRegisterMeeting = () => {
    router.push({name:'registerMeeting'});
}

</script>

<style scoped>
.register-meeting-btn{
    position: fixed;
    height: 46px;
    width:46px;
    background: no-repeat center/contain url(/images/icon/button-plus.svg);
    left: 50%;
    top: 80%;
    margin-left: -23px; /*half the width*/
    cursor: pointer;
    transform:translateX(175px);
}
.register-meeting-btn:hover{
    transition: transform 0.2s;
    transform:translateX(175px) scale(1.03);
}

</style>