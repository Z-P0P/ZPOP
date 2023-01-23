<template>
    <search-bar class="v-hidden" :searchOption="false"/>
    <admin-table :isLoaded="listData.isLoaded">
        <template #left-buttons>
            <input @click="checkAllHandler" class="checkbox" type="checkbox" name="table-checkbox" :checked="false"/>
            <span @click="activateRegionHandler($event, true)" class="table-bar__icon table-bar__icon-check"></span>
            <span @click="activateRegionHandler($event, false)" class="table-bar__icon table-bar__icon-x"></span>
        </template>
        <template #right-buttons>
            <span class="table-bar__page"> {{`${listData.count.toLocaleString()} 개 중
            ${pageMinIndex().toLocaleString()}-${pageMaxIndex().toLocaleString()}`}} </span>
            <span @click="decreasePage" class="table-bar__icon table-bar__icon-arrow-left hide"></span>
            <span @click="increasePage" class="table-bar__icon table-bar__icon-arrow-right hide"></span>
        </template>
        <template #list-header>
            <ul>
                <li><span>ID</span>
                    <button>
                        <img src="/images/icon/up-down.svg" alt="">
                    </button>
                </li>
                <li class="admin-tb-2">이름</li>
                <li class="admin-tb-2">총 미팅 수</li>
                <li class="admin-tb-1">활성 여부</li>
            </ul>
        </template>
        <template #list-content>
            <li v-for="(item, index) in listData.regions" :key="index" class="table-list__content">
                <ul class="list-content">
                    <li class="list-content__id">
                        <input type="checkbox" class="checkbox" v-model="item.isChecked">
                        <span>{{ item.id }}</span>
                    </li>
                    <li class="admin-tb-2">{{ item.name }}</li>
                    <li class="admin-tb-2">{{ item.num }}</li>
                    <li class="list-content__status admin-tb-1">
                        <span class="status status--round" :class="item.deletedAt ? 'status--negative' : 'status--positive'">
                            {{(item.deletedAt) ? 'FALSE' : 'TRUE'}}
                        </span>
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
    regions: [],
    count: 0,
    isLoaded: false,
});

const searchOption = reactive({
    keyword: "",
    option: "",
    page: "1",
    numPerSearch: 10,
})

const checkAllHandler = (event) => {
    let allCheckboxChecked = event.target.checked; 

    for (let region of listData.regions){
        region.isChecked = allCheckboxChecked;
    }
}


const getSearchOption = () => {
    const option = route.query.option;
    const page = parseInt(route.query.page);
    const keyword = route.query.keyword;
    const numPerSearch = parseInt(route.query.numPerSearch);
    searchOption.keyword = keyword == undefined ? "" : keyword;
    searchOption.option = option == undefined ? "" : option;
    searchOption.page = isNaN(page) ? 1 : page;
    searchOption.numPerSearch = isNaN(numPerSearch) ? 10 : numPerSearch;
}

const pageMinIndex = () => {
    const pageMaxNum = (searchOption.page - 1) * searchOption.numPerSearch + 1;
    return pageMaxNum <= listData.count ? pageMaxNum : listData.count;
}

const pageMaxIndex = () => {
    const pageMaxNum = (searchOption.page * searchOption.numPerSearch);
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
    updateUrl();
}

const updateUrl = () => {
    const searchParams = new URLSearchParams('');
    searchParams.set('page', searchOption.page);
    searchParams.set('keyword', searchOption.keyword);
    searchParams.set('option', searchOption.option);
    searchParams.set('num', searchOption.numPerSearch);
    const paramsURL = searchParams.toString();
    const url = `/admin/meeting/region?${paramsURL}`;
    route.query;
    router.push(url);
}

watch(route, () => {
    requestData();
    getSearchOption();
})

const requestData = () => {
    listData.isLoaded = false;
    isChecked.length = 0;
    fetch(`/api/admin/meeting/region${window.location.search}`)
        .then(res => res.json())
        .then(data => {
            listData.regions = [];
            listData.count = data.count;
            data.regions.forEach(item => {
                item.isChecked = false;
                isChecked.push(false);
            });
            listData.isLoaded = true;
        })
}
requestData();


const activateRegionHandler = (event, activate) => {
    let ids = [];
    listData.regions.forEach(region=>{
        if (region.isChecked){
            ids.push(region.id);
        }
    })
    const form = new FormData();
	form.append('ids', ids);
	form.append('activate', activate);
	const url = '/api/admin/meeting/region';
	const option = {
		method: "PUT",
		body: form,
	}

    fetch(url, option)
    .then(()=>{
        requestData();
    })
    .catch(err=>console.log(err));

    
    clearCheckedAll();
}

const clearCheckedAll = () => {
    listData.regions.forEach(region=> region.isChecked=false );
}

</script>

<style>
</style>