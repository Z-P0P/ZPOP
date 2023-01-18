<script setup>
import { setMapStoreSuffix } from 'pinia';
import { defineProps} from 'vue';
import Reply from './Reply.vue'
const props = defineProps({
  replies:Object
});
</script>

<template>
    <ul class="reply__list">
	
    <li v-for="(reply, index) in replies" :key="index"> 
       <Reply :reply="reply"/>
    </li>
</ul>
</template>
<style >
 
</style>