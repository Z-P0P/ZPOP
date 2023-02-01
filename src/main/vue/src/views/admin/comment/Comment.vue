<template>
    <Category :categories="categories" :categoryStatus="categoryStatus"/>
    <router-view/>
</template>

<script setup>
import {ref, watch} from 'vue';
import Category from '../../../components/admin/Category.vue';
import { useRoute } from 'vue-router';
const emit = defineEmits(["menuChanged"]);
emit("menuChanged", "comment");

const categoryStatus = ref(0);
const route = useRoute();
const categories = [
    {
        name: '댓글 리스트',
        link: 'list'
    }, 
];

watch(route, () => changeCategory(route.path.split('/')[3]));

const changeCategory = (category) => {
    let value;
    switch(category){
        case "list":
        value = 0;
        break;
    }
    categoryStatus.value = value;
}

changeCategory(route.path.split('/')[3]);

</script>

<style>

</style>