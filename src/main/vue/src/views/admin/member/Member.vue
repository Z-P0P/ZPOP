<template>
    <Category :categories="categories" :categoryStatus="categoryStatus"/>
    <router-view/>
</template>

<script setup>
import {ref, watch} from 'vue';
import Category from '../../../components/admin/Category.vue';
import { useRoute } from 'vue-router';
const emit = defineEmits(["menuChanged"]);
emit("menuChanged", "member");

const categoryStatus = ref(0);
const route = useRoute();
const categories = [
    {
        name: '회원 목록',
        link: 'list'
    }, 
    {
        name: '인기도 기록',
        link: 'eval'
    }, 
    {
        name: '모임 참여 기록',
        link: 'participation'
    }, 
];

watch(route, () => changeCategory(route.path.split('/')[3]));

const changeCategory = (category) => {
    let value;
    switch(category){
        case "list":
        value = 0;
        break;
        case "eval":
        value = 1;
        break;
        case "participation":
        value = 2;
        break;
    }
    categoryStatus.value = value;
}

changeCategory(route.path.split('/')[3]);

</script>

<style>

</style>