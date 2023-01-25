<template>
    <div class="my-profile">
        <div class="my-profile-container">
            <h2 class="profile__title" >내 프로필</h2>
            <div class="profile__image">
                <div class="profile__image--no-photo .icon"></div>
            </div>
            <span class="profile__nickname">{{ nickname }}</span>

            <div class="profile__fame">
                <span>{{ fame }}점</span>
                <span class="deco-img-star "></span>
            </div>

            <router-link to="/my-profile/edit" class="btn-semiround profile__btn">프로필 수정</router-link>
            <div class="profile__page-move">
                <router-link to="/my-meeting" >&nbsp;내가 참여한 모임</router-link>
                <span class="icon icon-arrow-right">오른쪽 화살표</span>
            </div>
            <div class="profile__page-move">
                <router-link to="/my-gathering" >&nbsp;내가 모집한 모임</router-link>
                <span class="icon icon-arrow-right">오른쪽 화살표</span>
            </div>
            <a class="profile__sign-out">탈퇴하기</a>
        </div>
    </div>

</template>

<!-- 생명주기 시점문제 script가 돌아가고 template이 실행되어야함
myPage.vue 의 script와 template이 돌아간다음 자식 컴포넌트 스크립트 템플릿 -->
 <script setup>
 import {useMemberStore} from "@/stores/memberStore";
 const memberStore = useMemberStore();
 const nickname = memberStore.nickname;
 const fame = memberStore.fame;
//  console.log(nickname);
//   const props = defineProps({
//                         myInfo: {type : Object,
//                         required :true}
//   })

</script> 
  
<style scoped>
@import url(../../assets/css/member/mypage.css);
@import url(../../assets/css/style.css);


</style>