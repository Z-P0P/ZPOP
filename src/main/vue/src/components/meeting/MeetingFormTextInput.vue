<template>
    <div class="meeting-form__input">
        <label class="input-text__label" for="">{{input.title}}</label>
        <div class="input-text">
            <div class="input-text__content-wrapper" :class="{'input-text__content-wrapper--error' : input.hasError}">
                <input class="input-text__content" type="text" :placeholder="input.placeholder"
                    :name="input.parameterName" @input="textInputHandler">
            </div>
        </div>
        <span class="input__message" :class="{'input__message--appear' : input.hasError}">{{ input.message }}</span>
    </div>
</template>

<script>
export default {
    props:['input','store'],
    emits:['textInput'],
    setup(props, context){
        const store = props.store;
        const textInputHandler = (e) => {
            const parameterName = props.input.parameterName;
            context.emit('textInput', parameterName, e.target.value);
        }
        return {
            store,
            textInputHandler,
        }
    }
}
</script>

<style>

</style>