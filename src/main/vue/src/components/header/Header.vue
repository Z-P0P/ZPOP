<template>
    <header class="header">
        <h1 class="text-hidden">ZPOP</h1>
        <nav>
            <ul>
                <li class="header__main"><router-link class="text-hidden" to="/">홈으로 이동</router-link></li>
                <li class="header__search-bar--pc-wrap hidden text-hidden">
                    <span class="icon icon-search">돋보기</span>
                    <input class="header__search-content--pc" type="text" />
                    데스크톱검색
                </li>
                <li v-if="isMemberAuthenticated" class="header__create-meeting"><router-link to="/meeting/register">모임 등록</router-link></li>
                <li class="header__search"><button class="text-hidden">검색</button></li>
                <li v-if="isMemberAuthenticated" id="header-notification" class="header__notification" :class="{'header__notification-on': hasNotification}">
                    <button @click="onNotificationClick" id="notification-btn" class="text-hidden">
                        알림확인
                    </button>
                      <notification-modal @checkNotification="checkNotification" v-show="isNotificationOpened"/>
                </li>
                <profile v-if="isMemberAuthenticated" />
                <li v-if="!isMemberAuthenticated" class="header__login">
                    <button @click="onLoginClick">로그인</button>
                </li>
            </ul>
        </nav>
    </header>
</template>

<script setup>
import { computed, onBeforeMount,ref } from "vue";
import { useHeaderStore } from '../../stores/headerStore';
import { useMemberStore } from '../../stores/memberStore';
import {useLoginModalStore} from "../../stores/loginModalStore"

import notificationModal from './NotificationModal.vue';
import profile from './Profile.vue';

const headerStore = useHeaderStore();
const memberStore = useMemberStore();
const loginModalStore = useLoginModalStore();
let hasNotification = ref(true);
// refresh 마다 세션 스토어를 이용해 로그인 상태 확인
onBeforeMount(() => {
    memberStore.isAuthenticated();
})

const isNotificationOpened = computed(() => {
    return headerStore.isNotificationOpened;
});

const onNotificationClick = () => {
    headerStore.closeAllExcept('Notification')
    headerStore.changeNotificationState();
}

const onLoginClick = () => {
    loginModalStore.show();
}

const isMemberAuthenticated = computed(() => {
    return memberStore.id !== 0
});

function checkNotification(){
    hasNotification.value = false;
}

</script>

<style scoped>
@import url(../../assets/css/component/header.css);
@import url(../../assets/css/component/modal.css);
</style>