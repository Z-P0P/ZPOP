<template>
    <Category/>
    <AdminList/>
</template>

<script setup>
const emit = defineEmits(["menuChanged"]);
emit("menuChanged", "home");


</script>

<style>

</style>