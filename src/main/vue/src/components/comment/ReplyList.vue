<script setup>
import { setMapStoreSuffix } from 'pinia';
import { defineProps,defineEmits, reactive} from 'vue';
import { useCommentStore} from '@/stores/commentStore'
import Reply from './Reply.vue'
const props = defineProps({
  comment:Object
});
const emit = defineEmits(['counterIncreased']);

console.log(props.comment.id+" 리플라이리스트")
const cmtStore = useCommentStore();
const storedCommentId = cmtStore.comment.id;
const commentId = props.comment.id;
let replies = props.comment.replyList;
if(storedCommentId != commentId){
  replies  = cmtStore.comment.replyList;
console.log("피냐")
}
function increaseCounter(){
  emit('counterIncreased');
}
</script>

<template>
    <ul class="reply__list">
	
    <li v-for="(reply, index) in replies" :key="index"> 
       <Reply :reply="reply" @counterIncreased="increaseCounter"/>
    </li>
</ul>
</template>
<style >
 
</style>