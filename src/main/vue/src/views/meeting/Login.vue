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

<script>
import { useRoute, useRouter } from 'vue-router';
import { useMemberStore } from '../../stores/memberStore';
import Modal from '../../components/modal/Default.vue';
import LoadingRoller from '../../components/LoadingRoller.vue'
export default {
    components:{ Modal, LoadingRoller},
    setup() {
        console.log('소셜로그인');
        const route = useRoute();
        const router = useRouter();
        const memberStore = useMemberStore();
        const url = '/api' + route.fullPath;
        console.log(url);
        fetch(url)
            .then(res => res.json())
            .then(data => {
                console.log(data);
                const memberInfo = {
                    nickname: data.nickname,
                    profileImagePath: data.profileImagePath,
                }
                memberStore.setInfo(memberInfo);
                router.push(data.redirectUrl);
            })
            .catch(err => {
                alert("로그인에 오류가 발생했음");
            });
    }
}
</script>

<style scoped>
.register-status__text{
    margin-top: 60px;
    margin-bottom: 10px;
    display:flex;
    justify-content: center;
}
</style>