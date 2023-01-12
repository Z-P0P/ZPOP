<template>
    <h1>소셜로그인인증중</h1>
</template>

<script>
import { useRoute, useRouter } from 'vue-router';
import { useMemberStore} from '../../stores/memberStore'
export default {
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
            .catch(err=>{
                alert("로그인에 오류가 발생했음");
            });
    }
}
</script>

<style>

</style>