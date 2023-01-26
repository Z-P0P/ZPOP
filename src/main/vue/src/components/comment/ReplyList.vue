<script setup>
import { defineProps,defineEmits} from 'vue';
import { useReplyStore} from '@/stores/replyStore'
import Reply from './Reply.vue'
const props = defineProps({
  comment:Object
});
const emit = defineEmits([
  'counterIncreased'
]);

const rplyStore = useReplyStore();
const storedCommentId = rplyStore.comment.id;
const commentId = props.comment.id;
let replies = props.comment.replyList;
if(storedCommentId != commentId){
  replies  = rplyStore.comment.replyList;
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