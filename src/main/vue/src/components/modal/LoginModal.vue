<template>
    <div v-show="store.isOn" class="modal-wrapper">
        <div class="modal">
            <div class="modal__header">
                <span class="modal__close-btn icon icon-x" @click="close">닫기</span>
            </div>
            <div class="modal__body">
                <div class="logo-img">
                    <img src="/images/logo/logo-modal.svg" alt="" class="src">
                </div>
                <div class="login-btn-container">
                    <a class="login-btn login-btn--kakao text-hidden" id="kakao-login-btn"
                        href="javascript:Kakao.Auth.authorize({redirectUri: 'http://localhost:5173/login/oauth/kakao'})">카카오
                        로그인 버튼</a>
                    <a @click="onNaverLoginClick" class="login-btn login-btn--naver text-hidden" id="naver-login-btn"
                        href="javascript:void(0)">네이버
                        로그인 버튼</a>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { useLoginModalStore } from '@/stores/loginModalStore';

const store = useLoginModalStore();

function close() {
    store.close();
}

// 소셜 로그인 시작
const generateRandomString = (num) => {
    const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz';
    let result = '';
    const charactersLength = characters.length;
    for (let i = 0; i < num; i++) {
        result += characters.charAt(Math.floor(Math.random() * charactersLength));
    }

    return result;
}

const serviceURL = "https://nid.naver.com/oauth2.0/authorize";
const clientId = "fXPYssaX_SOB2qThvRtF";
const redirectUrl = encodeURI("http://localhost:5173/login/oauth/naver");
const responseType = "code";
const state = encodeURI(generateRandomString(10));
const requestUrl = `${serviceURL}?response_type=${responseType}&client_id=${clientId}&state=${state}&redirect_uri=${redirectUrl}`;
const onNaverLoginClick = () => {
    location.href = requestUrl;
}
</script>

<style scoped>
@import url(../../assets/css/component/login.css);
@import url(../../assets/css/component/modal.css);

.modal__body {
    display: flex;
    flex-direction: column;
    align-items: center;
}


@media (min-width: 576px) {
    .modal {
        max-width: none;
        width: 588px;
        height: 557px;
    }
}
</style>