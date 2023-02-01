<template>
    <div id="modal-register" class="modal-wrapper">
        <div class="modal">
            <div class="modal__header">
                <span class="modal__close-btn icon icon-x" @click="close">닫기</span>
            </div>
            <div class="modal__body">
                <div class="modal__contents">
                    <Transition name="content1">
                        <div class="modal__content" v-show="!isRegisteredSuccessful">
                            <div class="logo-img">
                                <img src="../../../public/images/logo/logo-modal.svg" alt="" class="src">
                            </div>
                            <div class="modal-register__nickname-form input-text">
                                <label class="input-text__label" for="">집합에서 사용할 닉네임을 입력해주세요.</label>
                                <div class="input-text__content-wrapper"  
                                v-bind:class="{'input-text__content-wrapper--correct' : nickname.isValid == true ,
                                'input-text__content-wrapper--error': nickname.isValid  == false} ">
                                    <input class="input-text__content" type="text" placeholder="최소 2글자, 최대 10글자까지 입력 가능"
                                        :maxlength="maxNicknameLength" spellcheck="false" :value="nickname.value" @input=nicknameChangeHandler @keydown="keydownHandler">
                                </div>
                                <span class="input__message " v-bind:class="{'input__message--appear': message}"> {{ message }} </span>
                                <span class="btn btn-semiround" :class="{'btn-disabled':!nickname.isValid, 'btn-action':nickname.isValid}" @click.stop="setNickname">닉네임 설정</span>
                            </div>
                        </div>
                    </Transition>
                    <Transition name="content2">
                        <div class="modal__content" v-show="isRegisteredSuccessful">
                            <div class="greetings">
                                <p>
                                    <img src="../../../public/images/logo/logo-only-text.svg"><span>에 오신걸 환영해요!</span>
                                </p>
                                <p>
                                    😉 집합에 새로오신 ‘<span class="greetings__nickname">나홀로집에</span>’님!
                                </p>
                            </div>
                            <div class="pros">
                                <div>집합은 이런 점이 좋아요!</div>
                                <ol>
                                    <li>1. 간편하게 모임을 만들 수 있어요!</li>
                                    <li>2. 다양한 카테고리의 모임이 있어요!</li>
                                    <li>3. 전국 어디든 모일 수 있어요!</li>
                                </ol>
                            </div>
                            <div class="go-home">
                                <div>그럼 지금부터,</div>
                                <div>우리만의 교집합을 만들어 볼까요?😆</div>
                                <span class="btn btn-semiround btn-action" @click.stop="emit('close')">좋아요!</span>
                            </div>
                        </div>
                    </Transition>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { reactive, ref } from 'vue';
import { useMemberStore } from '../../stores/memberStore';
const nickname = reactive({
    value : "",
    isValid : null,
})
const maxNicknameLength = 10;
const message = ref("");
const emit = defineEmits(['close']);
const memberStore = useMemberStore();
const isRegisteredSuccessful = ref(false);

const close = () => {
    emit('close');
}

const setNickname = () => {
    
    if(!nickname.isValid){
        return;
    }
    
    const form = new FormData();
    form.append('nickname', nickname.value);
    
    const url = '/api/register/nickname/set';
    const options = {
        method: "POST",
        body: form,
    }
    fetch(url, options)
    .then(res => {
            if (!res.ok){
                throw new ServerException(res);
            }
            isRegisteredSuccessful.value = true;
            memberStore.isAuthenticated();
    })
    .catch((err)=> {
        err.json().then((data)=>{
            message.value = data.message;
            nickname.isValid = false;
        });
    });
}
    

let timer;
const delay = 300;
const nicknameChangeHandler = (event) => {
    nickname.isValid = null;
    message.value = null;
    nickname.value = event.target.value;
    if (timer){
        clearTimeout(timer);
    }
    timer = setTimeout(()=>{
        try {
            validateNickname();
        } catch (error) {
            nickname.isValid = false;
            message.value = error.message;
        }
    },delay);
}

const validateNickname = () => {
    
    if (nickname.value.length === 0) {
        throw new Error('닉네임은 공백이 될 수 없어요!');
    }
    if (nickname.value.length > maxNicknameLength){
        throw new Error('닉네임은 10글자 이하로 입력해주세요!');
    }

    const nicknameValidExp = /^(?=.*[a-zA-Z0-9가-힣])[a-zA-Z0-9가-힣]{0,10}$/;

    if (!nicknameValidExp.test(nickname.value)) {
        throw new Error('닉네임은 한글, 숫자, 영어만 조합해주세요!');
    }

    const form = new FormData();
    form.append('nickname', nickname.value);
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
        else {
            throw new Error('서버에 오류가 발생했습니다.');
        }
    })
    .then(data => {
        let result = data.result;
        console.log(data);
        if (result === "NICKNAME_VALID") {
            nickname.isValid = true;
            message.value ='사용할 수 있는 닉네임이에요!';
            return;
        }
        if (result == "NICKNAME_ALREADY_USED") {
            throw new Error('이미 사용중인 닉네임이에요!');
        }
    })
    .catch(err=>{
        nickname.isValid = false;
        message.value = err.message;
    })
}


</script>

<style scoped>
@import url('@/assets/css/component/modal.css');
/* @import url('@/assets/css/component/modal-2.css'); */
@import url('@/assets/css/form.css');


.content1-enter-active,
.content1-leave-active {
  transition: all 0.5s ease;
}

.content1-enter-from,
.content1-leave-to {
  transform:translateX(-100%);
  opacity: 0;
}


.content2-enter-active,
.content2-leave-active {
  transition: all 0.5s ease;
}

.content2-enter-to,
.content2-leave-from {
  transform:translateX(-100%);
  opacity: 1;
}

.modal__contents{
    height:600px; 
    display:flex; 
    overflow-x:hidden;
}
</style>
    
    