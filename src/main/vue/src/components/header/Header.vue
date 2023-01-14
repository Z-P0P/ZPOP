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
                <li v-if="!isMemberAuthenticated" class="header__create-meeting"><router-link to="/meeting/register">모임 등록</router-link></li>
                <li class="header__search"><button class="text-hidden">검색</button></li>
                <li v-if="!isMemberAuthenticated" id="header-notification" class="header__notification">
                    <button @click="onNotificationClick" id="notification-btn" class="text-hidden">
                        알림확인
                    </button>
                    <notification-modal v-show="isNotificationOpened" />
                </li>
                <profile v-if="!isMemberAuthenticated" />
                <li v-if="isMemberAuthenticated" class="header__login">
                    <button @click="onLoginClick">로그인</button>
                    <login-modal v-show="isLoginOpened" />
                </li>
            </ul>
        </nav>
    </header>
</template>

<script>
import { computed } from "vue";
import { useHeaderStore } from '../../stores/headerStore';
import { useMemberStore } from '../../stores/memberStore';
import loginModal from './LoginModal.vue';
import notificationModal from './NotificationModal.vue';
import profile from './Profile.vue';

export default {
    name: 'header',
    components: {
        notificationModal,
        loginModal,
        profile,
    },
    setup() {
        const headerStore = useHeaderStore();
        const memberStore = useMemberStore();
        const isNotificationOpened = computed(() => {
            return headerStore.isNotificationOpened;
        });

        const isLoginOpened = computed(() => {
            return headerStore.isLoginOpened;
        });

        const isProfileOpened = computed(() => {
            return headerStore.isProfileOpened;
        });

        const onNotificationClick = () => {
            headerStore.closeAllExcept('Notification')
            headerStore.changeNotificationState();
        }
        const onLoginClick = () => {
            headerStore.closeAllExcept('Login')
            headerStore.changeLoginState();
        }

        const isMemberAuthenticated = computed(() => {
            if (memberStore.memberInfo.constructor === Object
                && Object.keys(memberStore.memberInfo).length === 0) {
                return true;
            }

            return false;
        });
        return {
            isNotificationOpened,
            isLoginOpened,
            isProfileOpened,
            onNotificationClick,
            onLoginClick,
            isMemberAuthenticated
        }
    },
}
</script>

<style scoped>
@import url(../../assets/css/component/header.css);
@import url(../../assets/css/component/modal.css);
</style>