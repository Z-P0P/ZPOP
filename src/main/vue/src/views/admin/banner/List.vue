<template>
    <search-bar :searchOption="true" class="v-hidden"/>
    <admin-table @search="searchHandler" :isLoaded="listData.isLoaded">
        <template #left-buttons>
            <input @click="checkAllHandler" class="checkbox" type="checkbox" name="table-checkbox" />
            <span class="table-bar__icon table-bar__icon-check tooltip tooltip--hover">
                <span class="tooltip-solid tooltip-solid-top">활성화로 전환</span>
            </span>
            <span class="table-bar__icon table-bar__icon-x tooltip tooltip--hover">
                <span class="tooltip-solid tooltip-solid-top">비활성화로 전환</span>
            </span>
            <span class="table-bar__icon table-bar__icon-plus tooltip tooltip--hover" @click="openAddBanner">
                <span class="tooltip-solid tooltip-solid-top">배너 추가</span>
            </span>
        </template>
        <template #right-buttons>
            <span class="table-bar__page"> {{`${listData.count.toLocaleString()} 개 중 ` + getMinMaxIndex }} </span>
            <span @click="decreasePage" class="table-bar__icon table-bar__icon-arrow-left hide"></span>
            <span @click="increasePage" class="table-bar__icon table-bar__icon-arrow-right hide"></span>
        </template>
        <template #list-header>
            <ul>
                <li><span>ID</span>
                    <button @click="reverseOrder">
                        <img src="../../../../public/images/icon/up-down.svg" alt="">
                    </button>
                </li>
                <li class="admin-tb-2">제목</li>
                <li class="admin-tb-1 list-content__order">
                    <span>순서</span>
                    <button @click="reverseOrder">
                        <img src="../../../../public/images/icon/up-down.svg" alt="">
                    </button>
                </li>
                <li class="admin-tb-3">이미지</li>
                <li class="admin-tb-2">링크</li>
                <li class="admin-tb-2">생성일자</li>
                <li class="admin-tb-1">활성여부</li>
            </ul>
        </template>
        <template #list-content>
            <li v-for="(item, index) in listData.banners" :key="index" class="table-list__content" @click="openDetailModal(index)">
                <ul class="list-content">
                    <li class="list-content__id">
                        <input type="checkbox" class="checkbox" v-model="isChecked[index]">
                        <span>{{ item.id }}</span>
                    </li>
                    <li class="admin-tb-2">
                        {{ item.name }}
                    </li>
                    <li class="admin-tb-1">{{ item.order }}</li>
                    <li class="admin-tb-3 list-content__banner-img">
                        <img :src="`/images/banner/${item.imagePath}`" alt="item.name">
                    </li>
                    <li class="admin-tb-2">{{ item.link ? item.link : 'N/A' }}</li>
                    <li class="list-content_date admin-tb-2">{{ item.createdAt }}</li>
                    <li class="list-content__status admin-tb-1">
                        <span class="status status--round"
                            :class="item.activated ? 'status--positive' : 'status--negative'">
                            {{(item.activated) ? 'TRUE' : 'FALSE'}}
                        </span>
                    </li>
                </ul>
            </li>
        </template>
    </admin-table>
    <DetailModal v-if="bannerDetails.isOpened" @closeDetailModal="closeDetailModal" class="admin-detail-modal" title="배너 상세조회">
        <template #modal-body>
            <label for="">id : </label><span>{{ bannerDetails.id }}</span>
            <label for="">이름 : </label><input type="text" v-model="bannerDetails.name">
            <label for="">링크 : </label><input type="text" v-model="bannerDetails.link">
            <label for="">파일명 : </label><span>{{ bannerDetails.imagePath }}</span>
            <label for="">순서 : </label><input type="number" v-model="bannerDetails.order">
            <label for="">활성여부 : </label><input type="checkbox" class="checkbox" v-model="bannerDetails.activated">
            <label>베너 이미지</label>
            <img :src="`/images/banner/${bannerDetails.imagePath}`" :alt="bannerDetails.name">
            <span>배너 변경</span>            
            <button class="icon icon-camera"></button>
        </template>
        <template #modal-footer>
            <span class="btn btn-round btn-action--admin" @click="edit">수정</span>
        </template>
    </DetailModal>
    <DetailModal v-if="addedBanner.isOpened" @closeDetailModal="closeDetailModal" class="admin-detail-modal" title="배너 추가">
        <template #modal-body>
            <div class="detail-modal__input">
                <label for="">이름 : </label><input type="text" v-model="addedBanner.name" placeholder="배너 이름을 입력하세요">
            </div>
            <div class="detail-modal__input">
                <label for="">링크 : </label><input type="text" v-model="addedBanner.link" placeholder="링크가 있는 경우 링크를 입력하세요.">
            </div>
            <div class="detail-modal__input">
                <label for="">순서 : </label><input type="number" v-model="addedBanner.order" placeholder="배너가 표시되는 순서를 입력하세요.">
            </div>
            <div class="detail-modal__input">
                <label for="">활성여부 : </label><input type="checkbox" class="checkbox" v-model="addedBanner.activated">
            </div>
            <div class="detail-modal__input">
                <label>배너 이미지 업로드</label>
                <span>* 배너 이미지는 16 : 3 비율의 이미지를 업로드해주세요.</span>
                <img :src="null" :alt="addedBanner.name">
                <div class="banner-img-added">
                    <img :src="uploadedBanner.imgSrc" alt="배너">
                </div>
                <button class="icon icon-camera" @click="uploadBannerClickHandler"></button>
                <input @change="uploadImage" type="file" accept="image/*" ref="fileInput" class="hidden">
            </div>
        </template>
        <template #modal-footer>
            <span class="btn btn-round btn-action--admin" @click="add">저장</span>
        </template>
    </DetailModal>
</template>

<script setup>
import SearchBar from '../../../components/admin/SearchBar.vue';
import AdminTable from '../../../components/admin/Table.vue';
import { computed, reactive, ref, watch } from 'vue';
import router from '../../../router'
import { useRoute } from 'vue-router';
import DetailModal from '../../../components/admin/modal/AdminDetailModal.vue';



const route = useRoute();

const fileInput = ref(null);
const uploadedBanner = reactive({
    file : null,
    imgSrc : null,
})

const addedBanner = reactive({
    isOpened : false,
    name : null,
    link : null,
    imagePath : null,
    order : null,
    activated : Boolean,
});

const bannerDetails = reactive({
    isOpened : false,
    id : Number,
    name : String,
    link : String,
    imagePath : String,
    order : Number,
    activated : Boolean,
});

const listData = reactive({
    banners: [],
    count: 0,
    isLoaded: false,
});

const searchOption = reactive({
    page : Number,
    num : Number,
    order : String,
    bannerOrder : String,
})


const updateOption = () => {
    let {  page = 1, order =  "desc", num = 10, bannerOrder= "desc"} = route.query;
    searchOption.num = num;
    searchOption.page = page;
    searchOption.order = order;
    searchOption.bannerOrder = bannerOrder;
}
updateOption();

const isChecked = reactive([]);

const checkAllHandler = (event) => {
    let allCheckboxChecked = event.target.checked;

    for (let index in isChecked) {
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
    updateOption();
    requestData();
})

const requestData = () => {
    const url = `/api/admin/banner/list${window.location.search}`;
    fetch(url)
        .then(res => res.json())
        .then(data => {
            listData.banners = [];
            listData.count = data.length;
            data.forEach(item => {
                listData.banners.push(item)
                isChecked.push(false);
            });
            listData.isLoaded = true;
        })
}

requestData();

const edit = () => {
    const url = `/api/admin/banner/${bannerDetails.id}`;
    const formData = new FormData();
    const data = new Blob([JSON.stringify(addedBanner)], {
        type: "application/json",
    });
    formData.append('banner', data);
    formData.append('bannerImg',)
    const option = {
        method:'PUT',
    }
}

const add = () => {
    const url = `/api/admin/banner`;
    const formData = new FormData();
    const data = new Blob([JSON.stringify(addedBanner)], {
        type: "application/json",
    });

    formData.append('banner', data);
    formData.append('bannerImg', uploadedBanner.file);
    const option = {
        method:'POST',
        body:formData,
    }
    fetch(url,option)
    .then(res=>res.json())

}


const openDetailModal = (index) => {
    bannerDetails.isOpened = true;
    Object.keys(listData.banners[index]).forEach(key=>{
        bannerDetails[key] = listData.banners[index][key]
    });
}

const openAddBanner = () => {
    addedBanner.isOpened = true;
}

const closeDetailModal = () => {
    bannerDetails.isOpened = false;
    addedBanner.isOpened = false;
    uploadedBanner.imgSrc = null;
    uploadedBanner.file = null;
}

const uploadBannerClickHandler = () => {
    fileInput.value.click();
}

const uploadImage = (e) => {
    const file = e.target.files[0];
    uploadedBanner.file = file;
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = function(e) {
        uploadedBanner.imgSrc = e.target.result;
    }    
}

</script>

<style scoped>
.list-content__banner-img > img{
    width:100%;
}

.list-content__order{
    display:flex;
    align-items: center;
    justify-content: center;
}

.list-content__order > button{
    display:inline-flex;
    align-items: center;
    height:100%;
}
.banner-img-added{
    width: 1010px;
    height:190px;
    background-color:antiquewhite;
}

.banner-img-added > img{
    width:100%;
    height:100%;
    object-fit: contain;
    object-position: center;
}

.detail-modal__input{
    padding:5px 0;
    display:flex;
}

</style>