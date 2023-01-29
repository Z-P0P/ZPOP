<template>
    <div class="meeting-form__input" id="contact-type-input">
        <label class="input__label" for="category">{{ selectInput.title }}</label>
        <SelectBox @selectBoxClick="selectBoxClickhandler" 
        @optionClick="optionClickhandler"
        :option="selectInput"/>
        <span class="input__message" :class="{'input__message--appear': selectInput.hasError}">{{ selectInput.message }}</span>
        <div class="input-text__content-wrapper" :class="{'input-text__content-wrapper--error' : textInput.hasError}">
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

        const selectBoxClickhandler = (id) =>{
            context.emit('selectBoxClick',id);
        }
        const optionClickhandler = (id, placeholder, value) => {
            context.emit('optionClick',id, placeholder, value)
        }
        const textInputHandler = (e) => {
            const id = props.textInput.id;
            context.emit('textInput', id, e.target.value);
        }
        return {
            textInputHandler,
            selectBoxClickhandler,
            optionClickhandler
        }
    }
}
</script>

<style scoped>
@import url('@/assets/css/form.css');


.input-text__content-wrapper{
    margin-top:0;
}
.input__label{
    margin-bottom: 8px;
}
</style>