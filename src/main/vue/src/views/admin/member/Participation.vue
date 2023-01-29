<template>
    <search-bar :searchOption="true"/>
    <admin-table @search="searchHandler" :isLoaded="listData.isLoaded">
        <template #left-buttons>
            <input @click="checkAllHandler" class="checkbox" type="checkbox" name="table-checkbox"/>
            <span class="table-bar__icon table-bar__icon-check"></span>
            <span class="table-bar__icon table-bar__icon-x"></span>
        </template>
        <template #right-buttons>
            <span class="table-bar__page"> {{`${listData.count.toLocaleString()} 개 중
            ${pageMinIndex().toLocaleString()}-${pageMaxIndex().toLocaleString()}`}} </span>
            <span @click="decreasePage" class="table-bar__icon table-bar__icon-arrow-left hide"></span>
            <span @click="increasePage" class="table-bar__icon table-bar__icon-arrow-right hide"></span>
        </template>
        <template #list-header>
            <ul>
                <li>
                    <span>ID</span>
                    <button><img src="/images/icon/up-down.svg" alt=""></button>
                </li>
                <li class="admin-tb-3">모임(id)</li>
                <li class="admin-tb-2">참여자(id)</li>
                <li class="admin-tb-2">생성일자</li>
                <li class="admin-tb-1">취소여부</li>
            </ul>
        </template>
        <template #list-content>
            <li v-for="(participation, index) in listData.participations" :key="index" class="table-list__content">
                <ul class="list-content">
                    <li class="list-content__id">
                        <input type="checkbox" class="checkbox" v-model="isChecked[index]">
                        <span v-text="participation.id"></span>
                    </li>
                    <li class="list-content__title admin-tb-3 ">
                        <span class="bold" v-text=participation.title></span>
                        <span v-text="`(${participation.meetingId})`"></span>
                    </li>
                    <li class="list-content__nickname admin-tb-2 ">
                        <img :src="participation.profileImagePath==null?'/images/icon/user-profile.svg':'/images/user/'+ participation.profileImagePath"
                            alt="">
                        <span v-text="participation.participantNickname"></span>
                        <span v-text="`(${participation.participantId})`"></span>
                    </li>
                    <li class="list-content_date admin-tb-2" v-text="participation.createdAt"></li>
                    <li class="admin-tb-1"><span class="status status--round"
                            :class="participation.canceledAt==null ? 'status--neutral' : 'status--negative'"
                            v-text="participation.canceledAt==null? 'FALSE' : 'TRUE'"></span>
                    </li>
                </ul>
            </li>
        </template>
    </admin-table>
</template>

<script setup>
import { reactive, watch } from 'vue';
import { useRoute } from 'vue-router';
import SearchBar from '../../../components/admin/SearchBar.vue';
import AdminTable from '../../../components/admin/Table.vue';
import router from '../../../router';

const route = useRoute();


const listData = reactive({
    participations: [],
    count: 0,
    isLoaded: false,
});

let { keyword, option, page = 1, num = 10, minDate = new Date().toISOString().slice(0, 10), period = 9999 } = route.query;

const searchOption = reactive({
    keyword,
    option,
    page,
    num,
    minDate,
    period,
})


const isChecked = reactive([]);

const checkAllHandler = (event) => {
    let allCheckboxChecked = event.target.checked; 

    for (let index in isChecked){
        isChecked[index] = allCheckboxChecked;
    }
}

const pageMinIndex = () => {
    const pageMaxNum = (searchOption.page - 1) * searchOption.num + 1;
    return pageMaxNum <= listData.count ? pageMaxNum : listData.count;
}

const pageMaxIndex = () => {
    const pageMaxNum = (searchOption.page * searchOption.num);
    return pageMaxNum <= listData.count ? pageMaxNum : listData.count;
}

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
})

const requestData = () => {

    fetch(`/api/admin/member/participation${window.location.search}`)
        .then(res => res.json())
        .then(data => {
            listData.participations = [];
            listData.count = data.count;
            data.participations.forEach(item => {
                listData.participations.push(item)
                isChecked.push(false);
            });
            listData.isLoaded = true;
        })
}

requestData();
</script>

<style>
</style>