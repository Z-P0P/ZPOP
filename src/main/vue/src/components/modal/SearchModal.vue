<template>
  <div class="modal-wrapper modal">
    <div class="modal__header">
      <input class="modal__search-bar" type="text" placeholder="모임 이름으로 검색" @change="searchByKeyword" v-model="keyword">
      <span class="modal__close-btn icon icon-x" @click="closeModal">닫기</span>
    </div>
    <div class="modal__body">
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';
import { ref } from 'vue';
import { useMeetingListStore} from '@/stores/meetingListStore';

const store = useMeetingListStore();


const emit = defineEmits('close');
const router = useRouter();
const keyword = ref("");
const closeModal = () => {
    emit('close');
}

const searchByKeyword = () => {
    emit('close');
    router.push({
        name: "searchMeeting",
        query:{
            q: keyword.value,
        }
    })
}
</script>

<style scoped>
@import url(../../assets/css/component/modal.css);

.modal__header{
    align-items: center;
}

.modal__region-option{
    height: 20px;

}
</style>