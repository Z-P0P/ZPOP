<template>
    <Category :categories="categories" :categoryStatus="categoryStatus"/>
    <router-view/>
</template>

<script setup>
import {ref, watch} from 'vue';
import Category from '../../../components/admin/Category.vue';
import { useRoute } from 'vue-router';
const emit = defineEmits(["menuChanged"]);
emit("menuChanged", "meeting");

const categoryStatus = ref(0);
const route = useRoute();
const categories = [
    {
        name: '모임 조회',
        link: 'list'
    }, 
    {
        name: '카테고리 관리',
        link: 'category'
    }, 
    {
        name: '지역관리',
        link: 'region',
    }
];

watch(route, () => changeCategory(route.path.split('/')[3]));

const changeCategory = (category) => {
    let value;
    switch(category){
        case "list":
        value = 0;
        break;
        case "category":
        value = 1;
        break;
        case "region":
        value = 2;
        break;
    }
    categoryStatus.value = value;
}

changeCategory(route.path.split('/')[3]);

</script>

<style>

</style>