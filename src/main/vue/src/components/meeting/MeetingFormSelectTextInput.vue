<template>
    <div class="meeting-form__input" id="contact-type-input">
        <label class="input__label" for="category">{{ selectInput.title }}</label>
        <SelectBox @selectBoxClick="selectBoxClickhandler" 
        @optionClick="optionClickhandler"
        :option="selectInput"/>
        <span class="input__message" :class="{'input__message--appear': selectInput.hasError}">{{ selectInput.message }}</span>
        <div class="input-text__content-wrapper" :class="{'input-text__content-wrapper--error' : textInput.hasError}" id="contact-input">
            <input @input="textInputHandler" v-model="textInput.currentValue" 
            class="input-text__content" type="text" :placeholder="textInput.placeholder" :name="textInput.parameterName" :id="textInput.parameterName">
        </div>
        <span class="input__message" :class="{'input__message--appear': textInput.hasError}">{{ textInput.message }}</span>
    </div>
</template>

<script>
import SelectBox from './SelectBox.vue';

export default {
    props: ["selectInput", "textInput"],
    emits: ['optionClick', 'selectBoxClick','textInput'],
    components: { SelectBox },  
    setup(props, context){

        const selectBoxClickhandler = (parameterName) =>{
            context.emit('selectBoxClick',parameterName);
        }
        const optionClickhandler = (parameterName, placeholder, value) => {
            context.emit('optionClick',parameterName, placeholder, value)
        }
        const textInputHandler = (e) => {
            const parameterName = props.textInput.parameterName;
            context.emit('textInput', parameterName, e.target.value);
        }
        return {
            textInputHandler,
            selectBoxClickhandler,
            optionClickhandler
        }
    }
}
</script>

<style>

</style>