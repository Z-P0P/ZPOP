<script setup>
import { defineProps,defineEmits} from 'vue';
import { useReplyStore} from '@/stores/replyStore'
import Reply from './Reply.vue'

const rplyStore = useReplyStore();
 const props = defineProps({
   commentId:Number
 });
const emit = defineEmits([
  'counterIncreased'
]);


function increaseCounter(){
  emit('counterIncreased');
}
</script>

<template>
  <ul class="reply__list">
    <li v-for="(reply, index) in rplyStore.comments[commentId].replyList" :key="index"> 
      <Reply 
        :reply="reply" 
        @counterIncreased="increaseCounter"
      />
    </li>
</ul>
</template>
<style >
 
</style>