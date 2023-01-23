<template>
    <search-bar class="v-hidden" :searchOption="false"/>
    <admin-table :isLoaded="listData.isLoaded">
        <template #left-buttons>
            <input @click="checkAllHandler" class="checkbox" type="checkbox" name="table-checkbox" :checked="false"/>
            <span @click="activateRegionHandler($event, true)" class="table-bar__icon table-bar__icon-check"></span>
            <span @click="activateRegionHandler($event, false)" class="table-bar__icon table-bar__icon-x"></span>
        </template>
        <template #right-buttons>
            <span class="table-bar__page"> {{`${listData.count.toLocaleString()} 개 중 ` + getMinMaxIndex }} </span>
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
                        <input type="checkbox" class="checkbox" v-model="isChecked[index]">
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
import { reactive, watch, computed } from 'vue';
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

const isChecked = reactive([]);

const searchOption = reactive({
    keyword : String,
    option : String,
    page : Number,
    num : Number,
    minDate : String,
    period : Number,
    order : String,
})

const checkAllHandler = (event) => {
    let allCheckboxChecked = event.target.checked; 

    for (let index in isChecked){
        isChecked[index] = allCheckboxChecked;
    }
}

// 지역 조회시에는 사용할 일이 없음
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
    updateOption();
    requestData();
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
                listData.regions.push(item);
                isChecked.push(false);
            });
            listData.isLoaded = true;
        })
}
requestData();


const activateRegionHandler = (event, activate) => {
    let ids = getCheckedIds();
    let regions = getCheckedRegions();

    if (ids.length == 0) {
        alert('선택한 항목이 없습니다.');
        return;
    }

    const message = activate?'활성화':'비활성화'

    if (!confirm(`정말로 다음 지역을 ${message}하시겠습니까?\n지역: ${[...regions]}`)) return

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
        alert(`지역이 ${message}되었습니다.`);
        requestData();
    })
    .catch(err=>console.log(err));

    clearCheckedAll();
}

function getCheckedIds() {
    let ids = [];
    for (let index in isChecked) {
        if (isChecked[index]) {
            const region = listData.regions[index];
            ids.push(region.id);
        }
    }
    return ids;
}

function getCheckedRegions() {
    let regions = new Set();
    for (let index in isChecked) {
        if (isChecked[index]) {
            const region = listData.regions[index];
            regions.add(`${region.name}(${region.id})`);
        }
    }
    return regions;
}

</script>

<style>
</style>