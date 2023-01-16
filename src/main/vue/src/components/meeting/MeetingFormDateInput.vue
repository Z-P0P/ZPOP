<template>
    <div class="meeting-form__input" id="started-at-input">
        <label class="input__label" :for="input.parameterName">{{ input.title }}</label>
        <div class="input-date__content-wrapper" :class="{'input-date__content-wrapper--error' : input.hasError}">
            <input type="datetime-local" class="input-text__content input-text__content--date" @change="changeDate">
        </div>
        <span class="input__message" :class="{'input__message--appear' : input.hasError}">{{ input.message }}</span>
    </div>
</template>

<script>
export default {
    props:['input', 'store'],
    setup(props,context){
        const input = props.input;
        const changeDate = (e) => {
            context.emit('dateChange', input.parameterName, e.target.value);
        }

        return {
            changeDate,
        }
    }
}
</script>

<style>

</style>