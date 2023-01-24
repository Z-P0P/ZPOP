<template>
    <div class="admin-table">
        <div class="table-bar">
            <div class="table-bar__left-container">
                <slot name="left-buttons"></slot>
            </div>
            <div class="table-bar__right-container">
                <slot name="right-buttons"></slot>
            </div>
        </div>
        <div class="table-list">
            <div class="table-list__header">
                <slot name="list-header" class="table-list__header"></slot>
            </div>
            <ul class="table-list__content--wrapper">
                <slot name="list-content"></slot>
                <div :class="{ 'loader': !isLoaded }"></div>
            </ul>
        </div>
    </div>
</template>
<script setup>
const props = defineProps(['isLoaded']);

</script>

<style scoped>
@import url(../../assets/css/admin/component/admin-list.css);

</style>
<script>
</script>

<style>

</style>