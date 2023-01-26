<template>
     <div class="modal-wrapper report-modal-wrapper">
     <div class="report-complete">
        <div class="report-complete-content">신고가 완료되었습니다</div>
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
import { defineEmits } from 'vue';

</script>