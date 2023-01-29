<template>
    <Transition>
        <Modal v-if="route.query.login" class="login-modal">
            <template #modal-body>
                <div class="overflow-x-hidden">
                    <div class="modal__content-container" :class="{'modal__content-container--content1' : isRequesting}">
                        <div class="modal__content">
                            <div class="icon-cancel"></div>
                            <div class="login-modal__message">
                                <div>{{ error.reason }} </div>
                                <div v-for="(details, index) in error.details" :key="index">{{ details }}</div>
                            </div>
                            <router-link :to="{path: '/'}" class="btn btn-round">확인</router-link>
                        </div>
                        <div class="modal__content">
                            <PageLoader class="page-loader"/>
                            <div class="login-modal__message">
                                <div>로그인 중</div>
                            </div>
                        </div>
                    </div>
                </div>
            </template>
            <template #modal-footer>
            </template>
        </Modal>
    </Transition>   
</template>

<script setup>
import { useRoute, useRouter } from 'vue-router';
import { useMemberStore } from "@/stores/memberStore";
import Modal from '@/components/modal/Default.vue';
import PageLoader from '@/components/PageLoader.vue'
import {ServerException} from '@/utils/ServerException'
import { ref,reactive } from 'vue';

const memberStore = useMemberStore();

const route = useRoute();
const router = useRouter();
const isRequesting = ref(true);
const error = reactive({
    reason : "",
    details : "",
})

const emit = defineEmits(['memberRegisterRequired','close']);
const requestOAuth = () => {
    const oauthLoginUrl = `/api/login/oauth/${route.query.login}?code=${route.query.code}&state=${route.query.state}`;

    fetch(oauthLoginUrl)
    .then(res => res.json())
    .then(data => {
        console.log("성공 : fetch oauthLoginUrl ");

        if(data.status === 403){
            console.log(data);
            throw new ServerException(data);
        }

        if(data.code==="REGISTER_REQUIRED"){
            emit('memberRegisterRequired');
            emit('close');
            router.push({path: '/'});
            return;
        }


        memberStore.setInfo(data);

        // router에 있던 리다이렉트 기능이 이쪽으로 옮겨짐
        // 동일 view에서는 push를 해도 beforeEnter기능이 동작하지 않음
        // 예를 들면 '/login?naver...' 에서 '/'로 push해도 beforeEneter는 활성화되지 않음
        const redirectRoute = localStorage.getItem("redirect-route");
        if (redirectRoute) {
            localStorage.removeItem("redirect-route");
            emit('close');
            router.push(redirectRoute);
        }
        else{
            emit('close');
            router.push({path: '/'});
        }
    })
    .catch((err) => {
        console.log(err);
        const errInfo = err.res;
        isRequesting.value = false;
        error.reason = errInfo.message;
        error.details = errInfo.details;
    });
}

if (route.query.login){
    requestOAuth();
}


</script>

<style scoped>
@import url('@/assets/css/component/modal.css');

.overflow-x-hidden{
    overflow-y:hidden;
}

.page-loader{
    margin-top:40px;
}

.register-status__text{
    margin-top: 60px;
    margin-bottom: 10px;
    display:flex;
    justify-content: center;
}

.v-enter-active,
.v-leave-active {
  transition: opacity 0.3s 0.5s ease;
}

.v-enter-from,
.v-leave-to {
  opacity: 0;
}

.login-modal{
    z-index:10000;
}

.modal__content {
    display: grid;
    justify-items: center;
    row-gap: 20px;
    flex: 0 0 100%;
    grid-template-rows: 100px 100px;
    grid-template-columns: auto;
}

.login-modal__message{
    display:flex;
    flex-direction: column;
    row-gap: 10px;
}


.modal__content .btn{
    margin-top: 20px;
}
</style>