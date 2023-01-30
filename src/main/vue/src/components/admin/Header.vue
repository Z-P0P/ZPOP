<template>
  <aside @click="clickHandler" class="left-side-bar" :class="{'expended' : isExpanded}">
        <router-link to="/"><img src="/images/logo/logo-with-text.svg" alt="로고"></router-link>
        <nav>
            <ul>
                <li :class="{'selected':(props.status==0)}">
                    <router-link class="add-deco-img-left deco-img-home" 
                    :class="{'deco-img-home-white':(props.status==0)}"
                    to="/admin/home">대시보드</router-link>
                </li>
                <li :class="{'selected':(props.status==1)}">
                    <router-link class="add-deco-img-left deco-img-notification" 
                    :class="{'deco-img-notification-white':(props.status==1)}"
                    to="/admin/banner/list">공지 관리</router-link>
                </li>
                <li :class="{'selected':(props.status==2)}">
                    <router-link class="add-deco-img-left deco-img-meeting" 
                    :class="{'deco-img-meeting-white':(props.status==2)}"
                    to="/admin/meeting/list">모임 관리</router-link>
                </li>
                <li :class="{'selected':(props.status==3)}">
                    <router-link class="add-deco-img-left deco-img-user" 
                    :class="{'deco-img-user-white':(props.status==3)}" 
                    to="/admin/member/list">회원 관리</router-link>
                </li>
                <li :class="{'selected':(props.status==4)}">
                    <router-link class="add-deco-img-left deco-img-comment" 
                    :class="{'deco-img-comment-white':(props.status==4)}" 
                    to="/admin/comment/list">댓글 관리</router-link>
                </li>
                <li :class="{'selected':(props.status==5)}">
                    <router-link class="add-deco-img-left deco-img-report" 
                    :class="{'deco-img-report-white':(props.status==5)}" 
                    to="/admin/report/member">신고 관리</router-link>
                </li>
                <li>
                    <li><a class="add-deco-img-left deco-img-logout" href="#" @click="onLogoutClick">로그아웃</a></li>
                </li>
            </ul>
        </nav>
    </aside>
</template>

<script setup>
import { getLogout } from '@/api/login';
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { useMemberStore } from '../../stores/memberStore';
const memberStore = useMemberStore();
const isExpanded = ref(false);
const props = defineProps(['status']);
const router = useRouter();


const clickHandler = (event) => {
    const target = event.target;
    if (target.tagName == 'A' || target.tagName == 'LI'){
        return;
    }
    isExpanded.value = !isExpanded.value;
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
.left-side-bar, .left-side-bar ul{
    display: flex;
    flex-direction: column;
    overflow: hidden;
    white-space: nowrap;
    transition: all 0.2s;
}

.left-side-bar{
    background-color: var(--white);
    position: fixed;
    left: 0;
    top: 0;
    width:3.75rem;
    height: 100vh;
    row-gap:2.188rem;  
    box-shadow: 0px 3px 10px rgb(0 0 0 / 0.2);
    padding: 0.938rem 0.375rem;
    text-overflow: clip;
    z-index: 10;
}

.left-side-bar > img{
    width: fit-content;
    flex:0 0 2.5rem;
}

.left-side-bar .deco-img-left::before{
    background-size: 50%;
}

/* 네비게이션 영역은 이미지를 제외한 나머지 전부 */

.left-side-bar > nav{
    flex-grow: 1;
}

.left-side-bar ul{
    height:100%;
    row-gap: 0.5rem;
    width:3rem;
}

.left-side-bar li{
    color: var(--admin-icon-theme);
    font-size: 1.5rem;
    width:100%;
    height:3rem;
    border-radius: 0.625rem;
    transition: background-color 0.3s;
}

.left-side-bar li.selected{
    background-color: var(--admin-loyal-blue);
}

.left-side-bar li:hover{
    background-color: var(--light-grey1);
}


.left-side-bar li:last-child{
    margin-top:auto;
}

.left-side-bar li a::before{
    width:1.5rem;
    height:1.5rem;
}


/* side-bar가 펼쳐질 때의 속성 정의 */
.left-side-bar.expended{
    width:16.5rem;
    padding: 0.938rem 0.875rem;
}

.left-side-bar.expended ul{
    width:14.75rem;
}

.left-side-bar.expended li.selected{
    font-weight: bold;
    color:var(--white);
}
</style>