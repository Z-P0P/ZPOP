 <script setup>
    import { reactive, ref, watch } from "vue";
    import {useMemberStore} from"@/stores/memberStore"

   const memberInfo = useMemberStore();
   //place holder에 표시될 닉네임
   const phNickname = memberInfo.nickname;
   const inputStatus = reactive({
       inputMessage : "",
       isNicknameValid : null,
       inputNickname : "",
   })


   /**
    * 닉네임 유효성 검사를 위한 timer 와 validateNickname()함수 추가
    */
   let timer;
   const delay = 300;
   if (timer) {
           clearTimeout(timer);
       }
       timer = setTimeout(function() {
           validateNickname(inputStatus.inputNickname);
       }, delay);

   function validateNickname(nickname) {
       console.log(nickname);
       if (nickname.length === 0) return;
       const nicknameValidExp = /^(?=.*[a-zA-Z0-9가-힣])[a-zA-Z0-9가-힣]{0,10}$/;

       if (!nicknameValidExp.test(nickname)) {
            inputStatus.inputMessage = '닉네임은 한글, 숫자, 영어만 조합해주세요!'
            inputStatus.isNicknameValid = false;
           return;
        }
       const form = new FormData();
       form.append('nickname', nickname);
       const url = '/api/nickname/validation';
       const options = {
           method: "POST",
           body: form,
       }
       fetch(url, options)
           .then(response => {
               if (response.ok) {
                   return response.json();
               }
               throw Error();
           })
           .then(data => {
               let result = data.result;
               console.log(data);
               if (result === "NICKNAME_VALID") {
             
                
                  inputStatus.inputMessage= '사용할 수 있는 닉네임이에요!';
                  inputStatus.isNicknameValid = true;
                   
                //    setNicknameBtn.classList.add('btn-action');
                   return;
               }
               if (result == "NICKNAME_ALREADY_USED") {
                inputStatus.inputMessage = '이미 사용중인 닉네임이에요!';
                inputStatus.isNicknameValid = false;
                   return;
               }
           })
           .catch(err => {
               console.log(err)
           })
   }


   //=====================================image upload 🎇🎆🎆🎈🎈🎈
   const fileInput = ref(null);
   const imgSrc = ref("")
   function openFileUpload(e) {
       //파일선택 창 띄우기
       //fileInput = ref , 원본 값 value로 참조
       fileInput.value.click();
   }

   function uploadImage(e) {
       //input file 숨어있음 사진 아이콘 알맹이 on click -> openFileUpload실행
       //upload 이미지 = file
      const file = e.target.files[0];
       
      const reader = new FileReader();

      reader.readAsDataURL(file);

      reader.onload = function(e) {
       imgSrc.value = e.target.result;
      }
       
   }

   </script> 





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
                <div class="input-text__content-wrapper"  
                     v-bind:class="{'input-text__content-wrapper--correct input__message--appear' : inputStatus.isNicknameValid ,
                     'input-text__content-wrapper--error input__message--appear': !inputStatus.isNicknameValid } ">
                    <input class="input-text__content" type="text"
                        maxlength="10" spellcheck="false"
                        v-bind:placeholder="phNickname" v-model="inputStatus.inputNickname" @input="validateNickname(inputStatus.inputNickname)">
                </div>
                <span class="input__message" v-bind:textContent="inputStatus.inputMessage" ></span>

            </div>
            <span class="btn-semiround profile__btn--save ">저장하기</span>
            
        </div>
    </div>  
    
    </template>
   
    
    
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
    
    
    