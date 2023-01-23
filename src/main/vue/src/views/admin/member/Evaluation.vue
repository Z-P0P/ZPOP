<template>
    <search-bar :searchOption="true"/>
    <admin-table @search="searchHandler" :isLoaded="listData.isLoaded">
        <template #left-buttons>
            <input @click="checkAllHandler" class="checkbox" type="checkbox" name="table-checkbox"/>
            <span class="table-bar__icon table-bar__icon-check"></span>
            <span class="table-bar__icon table-bar__icon-x"></span>
        </template>
        <template #right-buttons>
            <span class="table-bar__page"> {{`${listData.count.toLocaleString()} 개 중 ` + getMinMaxIndex }} </span>
            <span @click="decreasePage" class="table-bar__icon table-bar__icon-arrow-left hide"></span>
            <span @click="increasePage" class="table-bar__icon table-bar__icon-arrow-right hide"></span>
        </template>
        <template #list-header>
            <ul>
                <li>
                    <span>ID</span>
                    <button @click="reverseOrder"><img src="/images/icon/up-down.svg" alt=""></button>
                </li>
                <li class="admin-tb-2">평가자(id)</li>
                <li class="admin-tb-2">피평가자(id)</li>
                <li class="admin-tb-1">평가점수</li>
                <li class="admin-tb-2">모임(id)</li>
                <li class="admin-tb-2">평가일자</li>
            </ul>
        </template>
        <template #list-content>
            <li v-for="(evaluation, index) in listData.evaluations" :key="index" class="table-list__content">
                <ul class="list-content">
                    <li class="list-content__id">
                        <input type="checkbox" class="checkbox" v-model="isChecked[index]">
                        <span v-text="evaluation.id"></span>
                    </li>
                    <li class="list-content__title admin-tb-2">
                        <span class="bold" v-text="evaluation.evaluateeNickname"></span>
                        <span v-text="`(${evaluation.evaluateeId})`"></span>
                    </li>
                    <li class="list-content__title admin-tb-2">
                        <span class="bold" v-text="evaluation.evaluatorNickname"></span>
                        <span v-text="`(${evaluation.evaluatorId})`"></span>
                    </li>
                    <li class="admin-tb-1" v-text="evaluation.rate"></li>
                    <li class="list-content__title admin-tb-2">
                        <span class="bold" v-text="evaluation.meetingTitle"></span>
                        <span v-text="`(${evaluation.meetingId})`"></span>
                    </li>
                    <li class="list-content_date admin-tb-2" v-text="evaluation.createdAt"></li>
                </ul>
            </li>
        </template>
    </admin-table>
</template>

<script setup>
import SearchBar from '../../../components/admin/SearchBar.vue';
import AdminTable from '../../../components/admin/Table.vue';
import { computed, reactive, ref, watch } from 'vue';
import router from '../../../router'
import { useRoute } from 'vue-router';

const route = useRoute();


const listData = reactive({
    evaluations: [],
    count: 0,
    isLoaded: false,
});


const searchOption = reactive({
    keyword : String,
    option : String,
    page : Number,
    num : Number,
    minDate : String,
    period : Number,
    order : String,
})

const reverseOrder = () => {
    if (searchOption.order =="desc"){
        searchOption.order = "asc";
    }
    else{
        searchOption.order = "desc";
    }
    updateUrl();
}

const updateOption = () => {
    let { keyword, option, page = 1, num = 10, minDate = new Date().toISOString().slice(0, 10), 
    period = 9999, order =  "desc"} = route.query;

    searchOption.keyword = keyword;
    searchOption.option = option;
    searchOption.page = page;
    searchOption.num = num;
    searchOption.minDate = minDate;
    searchOption.period = period;
    searchOption.order = order;
}
updateOption();


const isChecked = reactive([]);

const checkAllHandler = (event) => {
    let allCheckboxChecked = event.target.checked; 

    for (let index in isChecked){
        isChecked[index] = allCheckboxChecked;
    }
}

const pageMinIndex = () => {
    const pageMaxNum = (searchOption.page - 1) * searchOption.num + 1;
    return (pageMaxNum <= listData.count ? pageMaxNum : listData.count);
}

const pageMaxIndex = () => {
    const pageMaxNum = (searchOption.page * searchOption.num);
    return (pageMaxNum <= listData.count ? pageMaxNum : listData.count);
}

const getMinMaxIndex = computed(() => {
    return `${pageMinIndex().toLocaleString()}-${pageMaxIndex().toLocaleString()}`
})

const decreasePage = () => {
    if (pageMinIndex() == 0 || pageMinIndex() == 1) {
        return;
    }

    if (!listData.isLoaded) {
        return;
    }
    searchOption.page--;
    listData.isLoaded = false;
    updateUrl();
}

const increasePage = () => {
    if (pageMaxIndex() == listData.count) {
        return;
    }
    if (!listData.isLoaded) {
        return;
    }
    searchOption.page++;
    listData.isLoaded = false;
    updateUrl();
}

const updateUrl = () => {
    router.push({
        path: route.path,
        query: searchOption,
    });
}

watch(route, () => {
    requestData();
    updateOption();
})

const requestData = () => {

    fetch(`/api/admin/member/eval${window.location.search}`)
        .then(res => res.json())
        .then(data => {
            listData.evaluations = [];
            listData.count = data.count;
            data.evaluations.forEach(item => {
                listData.evaluations.push(item)
                isChecked.push(false);
            });
            listData.isLoaded = true;
        })
}

requestData();
</script>

<style scoped>
.list-content{
    padding: 10px 0px;
}
</style>