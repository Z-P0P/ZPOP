<template>
    <div class="my-profile">
        <div class="my-profile-container">
    
    
            <h2 class="profile__title" >프로필 수정</h2>
            <div class="profile__image">
                <input @change="uploadImage($event)" type="file" accept="image/*" ref="fileInput" class="file-input">
                
                <div v-if="imgSrc" class="profile__image--with-photo">
                    <img  :src="imgSrc" alt="" class="profile__image--img">
                    <div class="icon icon-camera" @click="openFileUpload"></div>
                </div>
                <div v-else="imgSrc" class="profile__image--no-photo">
                    <div class="icon icon-camera" @click="openFileUpload"></div>
                </div> 
            </div>

    
            <div class="input-text">
                <!--📌to do: 닉네임 수정-->
                <label class="input-text__label" for="input-text__content" ></label>
                <input id ="input-text__content" class="input-text__content" v-bind:placeholder="phNickname" type="text" :value="inputNickname" @input="nicknameModify">
            </div>
            <span class="btn-semiround profile__btn--save ">저장하기</span>
            
        </div>
    </div>  
    
    </template>
    
    <script setup>
     import { defineProps, ref, watch, onMounted } from "vue";

    // import { useMemberStore } from "@/stores/memberStore";
    const props = defineProps({
        myInfo: {type : Object,
                required :true}
    });
    // const memberStore = useMemberStore();
    // // console.log(memberStore.nickname);
    const phNickname = props.myInfo.nickname;
    const inputNickname = null;
    const fileInput = ref(null);
    const imgSrc = ref("")


    function nicknameModify() {
      console.log("modified");  
    }

    function openFileUpload(e) {
        fileInput.value.click();
    }

    function uploadImage(e) {
       const file = e.target.files[0];
        
       const reader = new FileReader();

       reader.readAsDataURL(file);

       reader.onload = function(e) {
        imgSrc.value = e.target.result;
       }
        
    }

    </script> 
    
    
    <style scoped>
    @import url(../../assets/css/form.css);
    @import url(../../assets/css/member/mypage.css);
       
    .file-input {
        display: none;
    }

    .profile__image--with-photo {
        position: relative;
    }

    .profile__image--img {
        width: 8.75rem;
        height: 8.75rem;
        border-radius: 50%;
    }

    .profile__image .icon {
        position: absolute;
        bottom: 4px;
        right: 3px;
    }

    </style>
    
    
    