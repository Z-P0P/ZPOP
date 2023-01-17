<template>
    <div class="my-profile">
        <div class="my-profile-container">
    
    
            <h2 class="profile__title" >프로필 수정</h2>
            <div class="profile__image">
                <!--📌to do: 이미지 등록-->
                <div class="profile__image--no-photo">
                    <div class="icon icon-camera"></div>
                </div>
            </div>
    
            <div class="input-text">
                <!--📌to do: 닉네임 수정-->
                <label class="input-text__label" for="input-text__content">닉네임</label>
                <input id ="input-text__content" class="input-text__content" type="text">
            </div>
            <span class="btn-semiround profile__btn--save ">저장하기</span>
            
        </div>
    </div>  
    
    </template>
    
    <script setup>
    import { defineProps } from "vue";
    const props = defineProps({
        myInfo: {type : Object,
                required :true}
    });
    console.log(props.myInfo +"iiiiiii");
    </script> 
    
    
    <style scoped>
    @import url(../../assets/css/form.css);
    @import url(../../assets/css/member/mypage.css);
    
    </style>
    
    
    