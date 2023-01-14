<template>
    <li class="header__profile select-box">
        <button><img alt="프로필" src="/images/icon/user-profile.svg" @click="onProfileClick"></button>
        <ul class="select-box__options select-box__options--right select-box__options--header" v-show="isOpened">
            <!--li 말고 div 박스 전체로 -->
            <li><router-link to="/my-profile">내 프로필</router-link></li>
            <li><router-link to="/my-gathering">내가 모집한 모임</router-link></li>
            <li><router-link to="/my-meeting">내가 참여한 모임</router-link></li>
            <li><router-link to="/my-meeting">모임 평가하기</router-link></li>
            <li><router-link to="/logout">로그아웃</router-link></li>
        </ul>
    </li>
</template>

<script>
import { computed } from 'vue'
import { useHeaderStore } from '../../stores/headerStore';

export default {
    setup(){
        const store = useHeaderStore();

        const isOpened = computed(()=>{
            return store.isProfileOpened;
        })

        const onProfileClick = () => {
            store.$reset();
            store.changeProfileState();
        }
        return{
            isOpened,
            onProfileClick,   
        }
    }
}
</script>

<style>
@import url(../../assets/css/component/select.css);

</style>