<template>
    <div class="meeting-form-container" @click="clickHandler">
        <form class="meeting-form" action="meeting/register" @submit.prevent="submithandler">
            <h1 class="meeting-form__title">모임 등록하기</h1>
            <fieldset class="meeting-form__default-info">
                <legend>1. 모임 기본 정보를 입력해주세요.</legend>
                <meeting-form-select-input @selectBoxClick="selectBoxClickHandler" @optionClick="optionClickHandler"
                    :input="registerForm.inputs.categories" />
                <meeting-form-date-input @dateChange="dateChangeHandler" :input="registerForm.inputs.startedAt" />
                <meeting-form-select-input @selectBoxClick="selectBoxClickHandler" @optionClick="optionClickHandler"
                    :input="registerForm.inputs.regions" />
                <meeting-form-text-input @textInput="textInputHandler" :input="registerForm.inputs.detailRegion" />
                <meeting-form-select-input @selectBoxClick="selectBoxClickHandler" @optionClick="optionClickHandler"
                    :input="registerForm.inputs.ageRanges" />
                <meeting-form-select-input @selectBoxClick="selectBoxClickHandler" @optionClick="optionClickHandler"
                    :input="registerForm.inputs.genderCategories" />
                <meeting-form-select-input @selectBoxClick="selectBoxClickHandler" @optionClick="optionClickHandler"
                    :input="registerForm.inputs.maxMembers" />
                <meeting-form-select-text-input @selectBoxClick="selectBoxClickHandler"
                    @optionClick="optionClickHandler" @textInput="textInputHandler" :selectInput="registerForm.inputs.contactTypes"
                    :textInput="registerForm.inputs.contact" />
            </fieldset>
            <fieldset class="meeting-form__details">
                <legend>2. 모임에 대해 소개해주세요.</legend>
                <meeting-form-text-input @textInput="textInputHandler" :input="registerForm.inputs.title" />
                <div class="meeting-form__input">
                    <label class="input-text__label" for="">내용</label>
                        <div class="register-editor">
                            <div id="editor">
                            <p>우리의 모임에 대해 소개해주세요!</p>
                            <p>
                                내용이 <strong>자세하면</strong> <u>참여할 확률</u>이 높아져요.
                            </p>
                            <p>
                                <br>
                            </p>
                        </div>
                    </div>
                </div>
            </fieldset>
            <div class="meeting-form__buttons">
                <router-link class="btn btn-round btn-cancel" :to="{path: '/'}">취소</router-link>
                <button id="register-btn" class="btn btn-round btn-action" type="submit">등록하기</button>
            </div>
        </form>
        <Modal v-show="registerForm.modalState.isOpened" @close-modal="closeModalHandler">
            <template #modal-body>
                <div class="overflow-x-hidden">
                    <div class="modal__content-container" :style="{transform:`translateX(${registerForm.modalState.sectionNum*-100}%)`}">
                        <div class="modal__content">
                            <div class="icon-cancel"></div>
                            <div class="register-status__text">
                                <div class="register-status__message">게시글 등록에 실패했어요.<br>다음 내용을 확인해주세요.</div>
                                <div class="register-status__message">{{registerForm.errorMessage}}</div>
                            </div>
                        </div>
                        <div class="modal__content">
                            <loading-roller :isShow="true" />
                            <div class="register-status__text">
                                <div class="register-status__message">게시글을 등록하고 있어요!</div>
                                <span class="register-status__btn btn btn-semiround" href="#">등록 중</span>
                            </div>
                        </div>
                        <div class="modal__content">
                            <div class="icon-done"></div>
                            <div class="register-status__text">
                                <div class="register-status__message">게시글을 등록했어요!</div>
                                <a class="register-status__btn btn btn-semiround btn-action" :href="registerForm.meetingUrl">모임글로 이동</a>
                            </div>
                        </div>
                    </div>
                </div>
            </template>
            <template #modal-footer>
            </template>
        </Modal>
        <input type="file" id="fileUpload" class="hidden" @change="fileUploadHandler">
    </div>
    <PageLoader :isWholePage="true" v-show="!registerForm.isLoaded"/>
</template>

<script>
import { onUpdated, reactive, ref } from '@vue/runtime-core';
import LoadingRoller from '../../components/LoadingRoller.vue';
import MeetingFormDateInput from '../../components/meeting/MeetingFormDateInput.vue';
import MeetingFormSelectInput from '../../components/meeting/MeetingFormSelectInput.vue';
import MeetingFormSelectTextInput from '../../components/meeting/MeetingFormSelectTextInput.vue';
import MeetingFormTextInput from '../../components/meeting/MeetingFormTextInput.vue';
import Modal from '../../components/modal/Default.vue';
import { RegisterForm } from '../../utils/RegisterForm';
import { getQuillEditor, quillImageUploadHandler } from "../../utils/quill-generator";
import PageLoader from '../../components/PageLoader.vue';
export default {
    components: { Modal, MeetingFormSelectInput, MeetingFormTextInput, MeetingFormDateInput, MeetingFormSelectTextInput, LoadingRoller, PageLoader },
    setup() {
        
        const registerForm = reactive(new RegisterForm());
        registerForm.addDefaultInputs();
        registerForm.requestInputOptions();

        const submithandler = (event) => {
            
            registerForm.setContentInEditor();
            if(!registerForm.validateInput()){
                return;
            }
            registerForm.openStatusModal();
            // quill 에디터의 내용을 v-model이나 기타 input, change 이벤트를 이용해 즉각적으로 store에 반영하기 어려움
            // 따라서 제출 시 내용 한번만 확인
            registerForm.postMeeting();
        
        }

        const clickHandler = (event) => {
            if (!event.target.parentElement.classList.contains('select-box')) {
                registerForm.closeAllSelectBox();
            }
        }

        const closeModalHandler = (event) => {
            registerForm.closeStatusModal();
        }

        const fileUploadHandler = quillImageUploadHandler;

        const selectBoxClickHandler = (id) => {
            registerForm.updateSelectBox(id);
        }

        const optionClickHandler = (id, placeholder, value) => {
            registerForm.updateOption(id,placeholder,value);
        }

        const dateChangeHandler = (id, inputDate) => {
            registerForm.updateDate(id, inputDate);
        }

        const textInputHandler = (id, currentValue) => {
            registerForm.updateTextInput(id,currentValue);
        }

        onUpdated(() => {
            if (!registerForm.editor) {
                registerForm.setEditor(getQuillEditor());
                document.querySelector('.ql-editor').addEventListener('focus',function(){
                    this.parentElement.parentElement.classList.add('register-editor--focused');
                })
                document.querySelector('.ql-editor').addEventListener('blur',function(){
                    this.parentElement.parentElement.classList.remove('register-editor--focused');
                })
            }
        });

        return {
            registerForm,
            submithandler,
            fileUploadHandler,
            dateChangeHandler,
            selectBoxClickHandler,
            optionClickHandler,
            textInputHandler,
            clickHandler,
            closeModalHandler,
        }
    }
}

</script>

<style>
@import url(../../assets/css/form.css);
@import url(../../assets/css/component/select.css);
@import url('https://cdn.quilljs.com/1.3.6/quill.snow.css');

.input__label {
    color: var(--dark-grey2);
}

/* input__label과 폰트크기가 다른 현상 해결 */
.input-text__label{
    font-size: 1.125rem;
}

.input__label {
    font-size: 1.125rem;
    font-weight: var(--bold);
}

.input__message {
    display: inline-flex;
    align-items: center;
    padding: 5px 8px;
    height: 32px;
}

.register-editor{
    border: 0.125rem solid #EBEBEB;
    margin-top:8px;
    border-radius: 5px;
    transition: border 0.3s;
}

.meeting-form>.meeting-details {
    display: flex;
    flex-direction: column;
    row-gap: 1.5rem;
}

.meeting-form .ql-toolbar,
.meeting-form .ql-container {
    border:none;
}

.meeting-form .ql-toolbar{
    border-bottom: 0.125rem solid #EBEBEB;
}

.meeting-form .register-editor--focused{
    border: 0.125rem solid var(--main-color);
}

.register-status__text {
    display: flex;
    flex-direction: column;
    align-items: center;
    row-gap: 30px;
}

.meeting-form .ql-snow .ql-picker:not(.ql-color-picker):not(.ql-icon-picker) svg {
    right: -10px;
}

.modal__content-container{
	display:flex;
	transition:transform 0.3s;
}
/* 
.modal__content {
    display: grid;
    flex-direction: column;
    align-items: center;
    row-gap: 20px;
    flex: 0 0 100%;
} */

</style>

<style scoped>
.modal__content {
    display: grid;
    justify-items: center;
    row-gap: 20px;
    flex: 0 0 100%;
    grid-template-rows: 100px 100px;
    grid-template-columns: auto;
}

#register-btn {
    margin-left: 16px;
}
</style>