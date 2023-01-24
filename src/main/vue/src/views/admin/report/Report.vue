<template>
    <Category :categories="categories" :categoryStatus="categoryStatus"/>
    <router-view/>
</template>

<script setup>
import {ref, watch} from 'vue';
import Category from '../../../components/admin/Category.vue';
import { useRoute } from 'vue-router';
const emit = defineEmits(["menuChanged"]);
emit("menuChanged", "report");

const categoryStatus = ref(0);
const route = useRoute();
const categories = [
    {
        name: '회원신고 관리',
        link: 'member'
    }, 
    {
        name: '모임신고 관리',
        link: 'meeting'
    }, 
    {
        name: '댓글신고 관리',
        link: 'comment',
    }
];

watch(route, () => changeCategory(route.path.split('/')[3]));

const changeCategory = (category) => {
    let value;
    switch(category){
        case "member":
        value = 0;
        break;
        case "meeting":
        value = 1;
        break;
        case "comment":
        value = 2;
        break;
    }
    categoryStatus.value = value;
}

changeCategory(route.path.split('/')[3]);

</script>

<style>

</style>