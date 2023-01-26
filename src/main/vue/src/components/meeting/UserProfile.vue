<script setup>
import { defineProps, reactive, ref, defineEmits } from 'vue';
import api from '@/api';
import ReportUser from '../report/ReportUser.vue';
import { useMemberStore } from '../../stores/memberStore';

let isResigned = ref(false);
let isReportModalOpened = ref(false);
const emit = defineEmits(['closeModal']);

const props = defineProps({
    userDetail: Object,
    participantId: Number,
    isModalOpened: Boolean
})
const state = reactive({
    member:{
        nickname:null,
        fame:0,
        participatedMeetingNumber:0,
        profileImg:null,
        resigned:null
    }
});

const id = props.participantId;
async function getMemberProfile(id){
    const response = await api.member.getProfile(id)
    const data = await response.json();
    state.member.nickname = data.nickname;
    console.log(data);
    if(state.member.resigned)
      isResigned.value = true;
}

function openReportUserModal(){
    isReportModalOpened.value = true;
}

function closeModal(){
    isReportModalOpened.value = false;
}

getMemberProfile(id);
</script>

<template>
            <!-----------------------------유저 프로필 모달!! 시작-------------------------------------->
           <!------------ sheet 컴포넌트 시작 --------------->
           <div id="sheet" class="column items-center justify-end" :class="{'hidden':isReportModalOpened}">
               <div class="overlay"></div> <!-- for overlay background -->
               <div class="contents column">
                   <header class="controls">
                       <div class="draggable-area">
                       <div class="draggable-thumb"></div>
                       </div>
                       <span @click.prevent="$emit('closeModal')" class="close-sheet icon icon-x"></span>
                   </header>
         
                   <div class="body column">
                       <div class="profile-container">
                           <div class="image-wrap">
                               <div class="image-bg">
                                <img v-bind:src="state.member.profileImg" class="image" alt="">
                               </div>
                           </div>
                       
                           <div class="user__info">
       
                               <div class="user__info-fixed">
                                   <span>닉네임</span>
                                   <span v-if="!isResigned">집합 참여</span>
                                   <span v-if="!isResigned">인기도</span>
                               </div>
       
                               <div class="user__info-flexible">
                                   <span>{{state.member.nickname}}</span>
                                   <span v-if="isResigned" class="resigned-member">탈퇴한 회원입니다!</span>
                                   <span v-if="!isResigned">{{state.member.participatedMeetingNumber}} 회</span>
                                   <span v-if="!isResigned">{{state.member.fame}} 점</span>
                               </div>
                           </div>  
       
                           <div v-if="!isResigned" class="ban__wrap">
                              <span @click.prevent="openReportUserModal" class="btn btn-semiround report modal__on-btn" data-id="member-report" data-modal="#modal-report-member">
                                   <span class="icon icon-siren-white"></span>
                                   신고하기
                               </span>
       
                               <span class="btn btn-semiround kick" >
                                   <span class="icon icon-door"></span>
                                   내보내기
                               </span>
                           </div>
                       </div><!-- end of profile-container -->
                   </div><!-- end of body column -->
               </div><!-- end of content column -->
           </div><!-- end of sheet -->
 <!----------------------유저 프로필 모달!! 끝-------------------------->
<ReportUser @closeModal="closeModal" v-if="isReportModalOpened" :reportedMemberId="id"/>
</template>

<style>

@import url(../../../../resources/static/css/member/member/profile-modal.css);


</style>