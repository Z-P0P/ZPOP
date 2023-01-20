<script setup>
import { defineProps, reactive } from 'vue';
import api from '@/api';

const props = defineProps({
  userDetail: Object,
  participantId: Number
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

function getMemberProfile(id){
  api.member.getProfile(id)
  .then((response)=>response.json())
  .then((data)=>{state.member = data;
    if(state.member.resigned)
      alert("탈퇴한 회원입니다!");
  })
}

getMemberProfile(id);

</script>

<template>
     <!------------ sheet 컴포넌트 시작 --------------->
     <div id="sheet" class="column items-center justify-end" >
               <div class="overlay"></div> <!-- for overlay background -->
               <div class="contents column">
                   <header class="controls">
                       <div class="draggable-area">
                       <div class="draggable-thumb"></div>
                       </div>
                       <span class="close-sheet icon icon-x"></span>
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
                                   <span>집합 참여</span>
                                   <span>인기도</span>
                               </div>
       
                               <div class="user__info-flexible">
                                   <span>{{state.member.nickname}}</span>
                                   <span>{{state.member.participatedMeetingNumber}} 회</span>
                                   <span>{{state.member.fame}} 점</span>
                               </div>
                           </div>  
       
                           <div class="ban__wrap">
                               <span class="btn btn-semiround report modal__on-btn" data-id="member-report" data-modal="#modal-report-member">
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
</template>

<style>


  
#sheet {
    --overlay: #888;
    --divider: #333;
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    z-index: 300;
    visibility: visible;
    transition: opacity 0.5s, visibility 0.5s;
  }
  
  #sheet[aria-hidden="true"] {
    opacity: 0;
    visibility: hidden;
    pointer-events: none;
  }
  
  #sheet .overlay {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    z-index: -1;
    background: var(--overlay);
    opacity: 0.5;
  }
  
  #sheet .contents {
    border-radius: 1rem 1rem 0 0;
    position: relative;
    overflow-y: hidden;
  
    --default-transitions: transform 0.5s, border-radius 0.5s;
    background-color: #fff;
    transition: var(--default-transitions);
    transform: translateY(0);
    max-height: 42vh;
    height: 42vh;
    min-width: 360px;
    box-sizing: border-box;
    padding-top:40px;
    padding-left: 18px;
    padding-right :18px;
    padding-bottom: 5px;
  }
  
  #sheet .contents:not(.not-selectable) {
    transition: var(--default-transitions), height 0.5s;
  }
  

  #sheet[aria-hidden="true"] .contents {
    transform: translateY(100%);
  }
  
  #sheet .draggable-area {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    width: 3rem;
    margin: auto;
    padding-top: 10px;
    padding-bottom: 18px;
    cursor: grab;
  }
  
  #sheet .draggable-thumb {
    width: inherit;
    height: 0.25rem;
    background: var(--divider);
    border-radius: 0.125rem;
  }
  
  #sheet .close-sheet {
    position: absolute;
    right: 0;
    top: 0;
    border: none;
    margin-right: 15px;
    margin-top: 10px;
  }
  
  #sheet .body {
    height: 100%;
    overflow-y: hidden;
    gap: 1rem;
  }
  
  .image {
    width: 100px;
    height: 100px;
    border-radius: 99px;
    position: absolute;
   left: 2px;
   top: 2px;
  }

  .image-wrap {
    display: flex;
    justify-content: center;
  
  }
  .image-bg{
    width: 104px;
    height: 104px;
    background-color: var(--tiffany-blue);
    position: relative;
    border-radius: 99px;
  }
  .user__info{
    font-size: 16px;
    display: flex;
    justify-content: center;
    margin-top: 23px;
    line-height: 20px;
  }

  .user__info-fixed{
    display: flex;
    flex-direction: column;
    color: var(--main-color);
    font-weight: 600;
    margin-left: -30px;
    margin-right: 10px;
  }
  .user__info-flexible{
    color: var(--dark-grey2);
    display: flex;
    flex-direction: column;
    padding-left: 10px;
    padding-right:10px;
  }

  .report{
    padding-left: 10px;
    background-color: var(--main-color)
  }
  .kick{
    padding-left: 10px;
    background-color: var(--tiffany-blue)
  }

span.icon{
margin-right: 10px;
}
  .ban__wrap{
    display: flex;
    justify-content: center;
    margin-top: 26px;
    gap:10px;
  }

  @media (min-width: 576px){
    .btn__wrap.btn {
      padding-left: 10px;
      padding-right: 10px;
  }
  }

</style>