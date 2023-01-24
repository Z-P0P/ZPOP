<template>
    <div class="meeting-form__input">
        <label class="input__label">{{ input.title }}</label>
        <SelectBox @selectBoxClick="selectBoxClickhandler" 
        @optionClick="optionClickhandler"
        :option="input"/>
        <span class="input__message" :class="{'input__message--appear': input.hasError}">{{ input.message }}</span>
    </div>
</template>

<script>
import SelectBox from './SelectBox.vue';

export default {
    props: ['input'],
    components: {SelectBox},
    emits : ['selectBoxClick', 'optionClick'],
    setup(props,context){
        const selectBoxClickhandler = (id) =>{
            context.emit('selectBoxClick',id);
        }
        const optionClickhandler = (id, placeholder, value) => {
            context.emit('optionClick',id, placeholder, value)
        }
        return{
            selectBoxClickhandler,
            optionClickhandler,
        }
    }
}
</script>

<style scoped>
.error{
    
}
/* 
.meeting-form__input{
    display: flex;
    flex-direction: column;
}

.input__label{
    font-size: 1.125rem;
    font-weight: var(--bold);
}

.input__message{
	display:inline-flex;
    align-items:center;
    padding:5px 8px;
    height:32px;
} */
</style>

