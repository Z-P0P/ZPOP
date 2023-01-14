<template>
    <div class="meeting-form-container" v-if="isLoaded">
        <form class="meeting-form" action="meeting/register" @submit.prevent="submithandler">
            <h1 class="meeting-form__title">모임 등록하기</h1>
            <fieldset class="meeting-form__default-info">
                <legend>1. 모임 기본 정보를 입력해주세요.</legend>
                <meeting-form-select-input 
                @selectBoxClick="selectBoxClickHandler"
                @optionClick="optionClickHandler"
                :input="inputs.categories"/>
                <meeting-form-date-input 
                @dateChange="dateChangeHandler"
                :input="inputs.startedAt"/>
                <meeting-form-select-input 
                @selectBoxClick="selectBoxClickHandler"
                @optionClick="optionClickHandler"
                :input="inputs.regions"/>
                <meeting-form-text-input 
                @textInput="textInputHandler"
                :input="inputs.detailRegion"/>
                <meeting-form-select-input 
                @selectBoxClick="selectBoxClickHandler"
                @optionClick="optionClickHandler"
                :input="inputs.ageRanges"/>
                <meeting-form-select-input 
                @selectBoxClick="selectBoxClickHandler"
                @optionClick="optionClickHandler"
                :input="inputs.genderCategories"/>
                <meeting-form-select-input 
                @selectBoxClick="selectBoxClickHandler"
                @optionClick="optionClickHandler"
                :input="inputs.maxMember"/>
                <meeting-form-select-text-input 
                @selectBoxClick="selectBoxClickHandler"
                @optionClick="optionClickHandler"
                @textInput="textInputHandler"    
                :selectInput="inputs.contactTypes" :textInput="inputs.contact"/>
            </fieldset>
            <fieldset class="meeting-form__details">
                <legend>2. 모임에 대해 소개해주세요.</legend>
                <meeting-form-text-input @textInput="textInputHandler"    
                :input="inputs.title" />
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
    </div>
    <div id="modal-register-meeting" class="modal-wrapper hidden">
        <div class="modal">
            <div class="modal__header">
                <span class="modal__close-btn icon icon-x" data-modal="#modal-register-meeting">닫기</span>
            </div>
            <div class="modal__body register-status">
                <div class="lds-roller register-status__icon">
                    <div></div>
                    <div></div>
                    <div></div>
                    <div></div>
                    <div></div>
                    <div></div>
                    <div></div>
                    <div></div>
                </div>
                <div class="register-status__text">
                    <div class="register-status__message">게시글을 등록하고 있어요!</div>
                    <a class="register-status__btn btn btn-semiround" href="#">등록 중</a>
                </div>
            </div>
        </div>
    </div>
    <input type="file" id="fileUpload" class="hidden" @change="fileUploadHandler">
</template>

<script>
import api from '@/api';
import { validateInput, addDefaultInputs, checkTimeValid, closeAllExcept, addInput } from '../../utils/register';
import { onUpdated, reactive, ref } from '@vue/runtime-core';
import MeetingFormDateInput from '../../components/meeting/MeetingFormDateInput.vue';
import MeetingFormSelectInput from '../../components/meeting/MeetingFormSelectInput.vue';
import MeetingFormSelectTextInput from '../../components/meeting/MeetingFormSelectTextInput.vue';
import MeetingFormTextInput from '../../components/meeting/MeetingFormTextInput.vue';
import { getQuillEditor, quillImageUploadHandler } from "../../utils/quill-generator";
export default {
    components: { MeetingFormSelectInput, MeetingFormTextInput, MeetingFormDateInput, MeetingFormSelectTextInput },
    setup() {
        const isLoaded = ref(false);
        const inputs = reactive({});
        const editor = ref({});
        const submithandler = (e) => {
            const quill = editor.value;
            // quill 에디터의 내용을 v-model이나 기타 input, change 이벤트를 이용해 즉각적으로 store에 반영하기 어려움
            // 따라서 제출 시 내용 한번만 확인하기 위해 우선 store에 내용 저장
            inputs['content'].currentValue = quill.root.innerHTML;
            validateInput(inputs);
        }

        const fileUploadHandler = quillImageUploadHandler;

        const selectBoxClickHandler = (parameterName) => {
            const input = inputs[parameterName];
            closeAllExcept(inputs, parameterName);
            input.isOpened = !input.isOpened;
            input.hasError = false;
        }

        const optionClickHandler = (parameterName, placeholder, value) => {
            const input = inputs[parameterName];
            input.placeholder = placeholder;
            input.currentValue = value;
            input.isOpened = !input.isOpened;
        }

        const dateChangeHandler = (parameterName, inputDate) => {
            const input = inputs[parameterName];
            input.hasError = false;
            const result = checkTimeValid(inputDate);
            if (!result.isValid) {
                input.message = result.reason;
                input.hasError = true;
                return;
            }
            input.currentValue = inputDate;
        }

        const textInputHandler = (parameterName, currentValue) => {
            console.log('텍스트입력중')
            const input = inputs[parameterName];
            input.hasError = false;
            input.currentValue = currentValue;
        }

        addDefaultInputs(inputs);
        const request = api.meeting.getActiveOptions();
        request
            .then(res => res.json())
            .then(data => {
                Object.keys(data).forEach((key) => {
                    let title;
                    let placeholder;
                    switch (key) {
                        case 'categories':
                            title = '카테고리'
                            placeholder = '카테고리를 선택해주세요.'
                            break;
                        case 'ageRanges':
                            title = '연령대'
                            placeholder = '연령대를 선택해주세요.'
                            break;
                        case 'regions':
                            title = '지역';
                            placeholder = '지역을 선택해주세요.';
                            break;
                        case 'contactTypes':
                            title = '연락방법';
                            placeholder = '연락방법을 선택해주세요.';
                            break;
                    }
                    addInput(inputs, data[key], title, placeholder, key);
                    isLoaded.value = true;
                })
            })
            .catch((err) => { alert('모임글 옵션을 불러오는데 실패했습니다.') });

        onUpdated(() => {
            const quill = getQuillEditor();
            editor.value = quill;
        });
        return {
            isLoaded,
            inputs,
            submithandler,
            fileUploadHandler,
            dateChangeHandler,
            selectBoxClickHandler,
            optionClickHandler,
            textInputHandler,
            editor,
        }
    }
}

</script>

<style>
@import url(../../assets/css/form.css);

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
    font-weight: bold;
    padding: 1rem 0;
    width: 100%;
    border-bottom: 2px solid var(--tiffany-blue);
    margin-bottom: 1.5rem;
    grid-area: legend;
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

#contact-input {
    margin-top: 0;
}

#modal-register-meeting .modal__body {
    display: flex;
    flex-direction: column;
    height: 300px;
    align-items: center;
    padding: 50px 50px;
    row-gap: 20px;
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
</style>