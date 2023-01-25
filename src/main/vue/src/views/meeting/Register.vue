<template>
    <div class="meeting-form-container" v-if="registerForm.isLoaded" @click="clickHandler">
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
            </fieldset>
            <div class="meeting-form__buttons">
                <button class="btn btn-round">취소</button>
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
export default {
    components: { Modal, MeetingFormSelectInput, MeetingFormTextInput, 
        MeetingFormDateInput, MeetingFormSelectTextInput, LoadingRoller },
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
.meeting-form-container {
    padding: 0 20px;
    max-width: 996px;
    width: 100%;
    box-shadow: none;
}


@media (min-width:576px) {
    .meeting-form-container {
        padding: 64px 20px;
        margin-top: 50px;
        box-shadow: 0px 5px 15px rgba(0, 0, 0, 0.15);
    }
}

.meeting-form {
    display: flex;
    max-width: 790px;
    row-gap: 24px;
    flex-direction: column;
    z-index: 1;
}

.meeting-form * {
    font-size: 1rem;
}

/* error 메세지 출력 라벨 크기가 16px로 적용되는 현상 해결 */
.meeting-form .input__message {
    font-size: 14px;
}

/* input__label과 폰트크기가 다른 현상 해결 */
.input-text__label{
    font-size: 1.125rem;
}

@media (min-width:576px) {
    .meeting-form * {
        font-size: 1.125rem;
    }


}


@media (min-width:576px) {
    .meeting-form {
        margin: 0 auto;
    }
}


.meeting-form__default-info {
    display: grid;
    column-gap: 1.5rem;
    grid-template-columns: 1fr 1fr;
}

@media (max-width:576px) {
    .meeting-form__default-info {
        grid-template-columns: 1fr;
    }
}


.meeting-form__title {
    display: none;
}

@media (min-width:576px) {
    .meeting-form__title {
        display: block;
        align-self: center;
        color: var(--tiffany-blue);
        font-weight: 600;
        font-size: 36px;
    }
        

}

.meeting-form legend {
    font-size: 1.5rem;
    font-weight: 700;
    padding: 1rem 0;
    width: 100%;
    border-bottom: 2px solid var(--tiffany-blue);
    margin-bottom: 1.5rem;
    grid-area: legend;
    color: var(--dark-grey2);
}


.meeting-form__input {
    display: flex;
    flex-direction: column;
}

.meeting-form__input .ql-container {
    min-height: 300px;
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

.meeting-form__buttons {
    align-self: flex-end;
}

.form-input-container>span {
    display: inline-block;
    font-weight: bold;
}

.form-input-container>*:not(span) {
    margin-top: 0.5rem;
}

.meeting-form>.meeting-details {
    display: flex;
    flex-direction: column;
    row-gap: 1.5rem;
}

.meeting-form .ql-toolbar {
    margin-top: 8px;
    border-top-left-radius: 5px;
    border-top-right-radius: 5px;
    border: 0.125rem solid #EBEBEB;
}

.meeting-form .ql-container {
    border-bottom-left-radius: 5px;
    border-bottom-right-radius: 5px;
    border: 0.125rem solid #EBEBEB;
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

</style>