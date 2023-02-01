
<script setup>
    import {ref, nextTick} from 'vue';
    defineProps({
        modelValue: String
    });
    const emit = defineEmits([
        'update:modelValue'
    ]);

    function updateInput(event){
       emit('update:modelValue',event.target.value)
    }
    defineExpose({
        childMethod
    })
    const childInput = ref();
    async function childMethod(){
        await nextTick();
        childInput.value.focus();
    }
</script>

<template>
    <textarea v-bind:value="modelValue" 
              v-on:input="updateInput" 
              ref="childInput"
    ></textarea>
</template>

<style>
    @import url(@/assets/css/meeting/component/comment.css);
</style>