<template>
    <li class="header__profile select-box">
        <button><img alt="프로필" src="/images/icon/user-profile.svg" @click="onProfileClick"></button>
        <ul class="select-box__options select-box__options--right select-box__options--header" v-show="isOpened">
            <!--li 말고 div 박스 전체로 -->
            <li><router-link to="/my-profile">내 프로필</router-link></li>
            <li><router-link to="/my-gathering">내가 모집한 모임</router-link></li>
            <li><router-link to="/my-meeting">내가 참여한 모임</router-link></li>
            <li><router-link to="/my-meeting">모임 평가하기</router-link></li>
            <li><a href="#" @click="onLogoutClick">로그아웃</a></li>
        </ul>
    </li>
</template>

<script setup>
import { getLogout } from '../../api/login'
import { computed } from 'vue'
import { useHeaderStore } from '../../stores/headerStore';
import { useMemberStore } from '../../stores/memberStore';
import { useRouter } from 'vue-router';

const router = useRouter();
const headerStore = useHeaderStore();
const memberStore = useMemberStore();
const isOpened = computed(()=>{
    return headerStore.isProfileOpened;
})
const onProfileClick = () => {
    headerStore.closeAllExcept('Profile')
    headerStore.changeProfileState();
}

const onLogoutClick = () => {
    const request = getLogout();
    request.then(()=>{
        memberStore.clearInfo();
        router.push('/');
    })
    .catch(err=>console.log('로그인에 실패했습니다.',err));
}
</script>

<style scoped>
@import url(../../assets/css/component/header.css);

.select-box::before{
    display:none;
}
</style>