<template>
    <Modal>
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
</template>

<script setup>
import { useRoute, useRouter } from 'vue-router';
import { useMemberStore } from "@/stores/memberStore";
import Modal from '@/components/modal/Default.vue';
import LoadingRoller from '@/components/LoadingRoller.vue'

const memberStore = useMemberStore();

const route = useRoute();
const router = useRouter();
const oauthLoginUrl = '/api' + route.fullPath;

fetch(oauthLoginUrl)
    .then(res => res.json())
    .then(data => {
        console.log("성공 : fetch oauthLoginUrl ")
        memberStore.setInfo(data);
        router.push("/");
    })
    .catch((err) => {
        console.info("Login.vue err", err)
        alert("로그인에 오류가 발생했음");
        router.push("/");
    });

</script>

<style scoped>
.register-status__text{
    margin-top: 60px;
    margin-bottom: 10px;
    display:flex;
    justify-content: center;
}
</style>