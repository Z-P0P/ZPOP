<template>
    <Transition>
        <Modal v-if="route.query.login" class="loginModal">
            <template #modal-body>
                <div>
                    <div class="modal__content-container">
                        <div class="modal__content">
                            <loading-roller :isShow="true" />
                            <div class="register-status__text">
                                <div class="register-status__message">로그인 중</div>
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
import LoadingRoller from '@/components/LoadingRoller.vue'

const memberStore = useMemberStore();

const route = useRoute();
const router = useRouter();

const emit = defineEmits(['memberRegisterRequired','close']);

const requestOAuth = () => {
    const oauthLoginUrl = `/api/login/oauth/${route.query.login}?code=${route.query.code}&state=${route.query.state}`;

    fetch(oauthLoginUrl)
    .then(res => res.json())
    .then(data => {
        console.log("성공 : fetch oauthLoginUrl ");

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
            router.push(redirectRoute);
        }
        else{
            router.push({path: '/'});
        }
    })
    .catch((err) => {
        console.info("Login.vue err", err)
        alert("로그인에 오류가 발생했음");
        router.push({path: '/'});
    });
}

if (route.query.login){
    requestOAuth();
}


</script>

<style scoped>
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

.loginModal{
    z-index:10000;
}
</style>