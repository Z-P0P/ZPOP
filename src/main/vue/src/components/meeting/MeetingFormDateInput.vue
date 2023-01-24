<template>
    <div class="meeting-form__input" id="started-at-input">
        <label class="input__label" :for="input.parameterName">{{ input.title }}</label>
        <div class="input-date__content-wrapper add-deco-img-left deco-img-calendar" 
        :class="{'input-date__content-wrapper--error' : input.hasError}">
            <input type="datetime-local" class="input-text__content input-text__content--date" @change="changeDate" v-model="input.currentValue">
            <input @click="clickDatepickerHandler" placeholder="시작일자를 선택해주세요." type="text" class="custom-datepicker input-text__content input-text__content--date" @change="changeDate" :value="datepickerDate" readonly>
            <Datepicker @closeDatepicker="closeDatepicker" class="datepicker" v-show="!isDatepickerClosed" @submitDate="datepickerSubmitHandler"/>
        </div>
        <span class="input__message" :class="{'input__message--appear' : input.hasError}">{{ input.message }}</span>
    </div>
</template>

<script setup>
import { reactive, computed, ref } from 'vue';

import Datepicker from './Datepicker.vue';

const props = defineProps(['input', 'store'])
const input = props.input;

const datepickerDate = ref("");

const emit = defineEmits(['dateChange']);
const changeDate = (e) => {
    emit('dateChange', input.parameterName, e.target.value);
}

const isDatepickerClosed = ref(true);

const clickDatepickerHandler = () => {
    isDatepickerClosed.value = !isDatepickerClosed.value;
}

const closeDatepicker = () => {
    isDatepickerClosed.value = true;
}

const datepickerSubmitHandler = (selectedDate) => {
    let date = selectedDate.dateInfo;
    datepickerDate.value = `${date.year} 년 ${date.month} 월 ${date.day} 일 ${date.hour} 시 ${date.minute} 분`;
    emit('dateChange',input.parameterName,selectedDate.formattedDate);
}

</script>

<style scoped>
.custom-datepicker{
    position:relative;
    /* display:none; */
}
input[type='datetime-local']{
        display: none;
    }

.add-deco-img-left{
    position:relative;
}

.add-deco-img-left::before{
    display:none;
}

.datepicker{
    top:-30px;
    z-index:10;
}

@media (min-width: 576px) {

    input[type='datetime-local']{
        display: none;
    }

    .input-date__content-wrapper{
        margin-top:0;
    }

    .custom-datepicker{
        display:block;
        cursor:pointer;
    }

    .add-deco-img-left::before{
        position:absolute;
        display:inline-block;
        z-index:1;
        right:20px;
        width:1.3rem;
    }
}
</style>