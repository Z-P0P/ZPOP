<template>
     <div class="modal-wrapper report-modal-wrapper">
     <div class="report-complete">
        <div v-if="!isDuplicated" class="report-complete-content">신고가 완료되었습니다</div>
        <div v-if="isDuplicated" class="report-complete-content">중복신고는 불가능합니다</div>
        <div class="report-complete__btn" @click.prevent="$emit('closeModal')">확인</div>
    </div>
    </div>
</template>

<style scoped>
@import url(../../assets/css/component/select.css);
@import url(../../assets/css/meeting/component/report-modal.css);
@import url(../../assets/css/meeting/component/modal.css);
</style>

<script setup>
import {defineProps} from "vue";
const props = defineProps({
    isDuplicated:Boolean
})

</script>