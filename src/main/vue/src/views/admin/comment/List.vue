<template>
    <search-bar :searchOption="true"/>
    <admin-table @search="searchHandler" :isLoaded="listData.isLoaded">
        <template #left-buttons>
            <input @click="checkAllHandler" class="checkbox" type="checkbox" name="table-checkbox"/>
            <span @click="deleteAllComments" class="table-bar__icon table-bar__icon-check"></span>
            <span @click="cancelDeleteAllComments" class="table-bar__icon table-bar__icon-x"></span>
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
                <li class="admin-tb-3">모임글 제목/댓글 내용</li>
                <li class="admin-tb-2">작성자(id)</li>
                <li class="admin-tb-1">부모 댓글 id</li>
                <li class="admin-tb-2">작성 일자</li>
                <li class="admin-tb-1">삭제여부</li>
            </ul>
        </template>
        <template #list-content>
            <li v-for="(item, index) in listData.comments" :key="index" class="table-list__content">
                <ul class="list-content">
                    <li class="list-content__id">
                        <input type="checkbox" class="checkbox" v-model="isChecked[index]">
                        <span>{{ item.id }}</span>
                    </li>
                    <li class="list-content__title admin-tb-3">
                        <span class="bold">{{ item.meetingTitle}}</span>
                        <span>{{ item.content}}</span>
                    </li>
                    <li class="admin-tb-2">{{ `${item.nickname}(${item.writerId})` }}</li>
                    <li class="admin-tb-1">{{ `${item.parentCommentId==0?'N/A':item.parentCommentId}` }}</li>
                    <li class="list-content_date admin-tb-2">{{ item.createdAt }}</li>
                    <li class="list-content__status admin-tb-1">
                        <span class="status status--round" :class="item.deletedAt ? 'status--negative' : 'status--incomplete'">
                            {{(item.deletedAt) ? 'TRUE' : 'FALSE'}}
                        </span>
                    </li>
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
    comments: [],
    count: 0,
    isLoaded: false,
});
const searchOption = reactive({
    keyword: String,
    option: String,
    page: Number,
    num: Number,
    minDate: String,
    period: Number,
    order: String,
})

const reverseOrder = () => {
    if (searchOption.order == "desc") {
        searchOption.order = "asc";
    }
    else {
        searchOption.order = "desc";
    }
    updateUrl();
}

const updateOption = () => {
    let { keyword, option, page = 1, num = 10, minDate = new Date().toISOString().slice(0, 10),
        period = 9999, order = "desc" } = route.query;

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

const closeDetailModal = () => {
    meetingDetails.isLoaded = false;
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
    listData.isLoaded = false;
    //배열초기화
    isChecked.length = 0;

    fetch(`/api/admin/comment/list${window.location.search}`)
        .then(res => res.json())
        .then(data => {
            listData.comments = [];
            listData.count = data.count;
            data.comments.forEach(item => {
                listData.comments.push(item)
                isChecked.push(false);
            });
            listData.isLoaded = true;
        })
}
requestData();

const deleteAllComments = () => {
    let ids = [];
    for (let index in isChecked){
        if (isChecked[index]){
            ids.push(listData.comments[index].id);
        }
    }

    if (ids.length == 0) {
        alert('선택한 댓글이 없습니다.');
        return;
    }

    if (!confirm(`정말로 다음 댓글을 삭제하시겠습니까?\n댓글번호: ${ids}`)) return
    const url = `/api/admin/comment?ids=${ids}`
    const formData = new FormData();
    formData.append('isDeleted', true);
    const option = {
        method: 'DELETE',
        body: formData,
    }
    fetch(url, option)
    .then(res => {
        if (res.ok) {
            alert('댓글 삭제가 정상적으로 처리되었습니다.')
            requestData();
        }
        else {
            throw new Error();
        }
    })
    .catch(err => {
        alert('댓글 삭제 과정에서 오류가 발생하였습니다');
    })
}

const cancelDeleteAllComments = () => {
    let ids = [];
    for (let index in isChecked){
        if (isChecked[index]){
            ids.push(listData.comments[index].id);
        }
    }

    if (ids.length == 0) {
        alert('선택한 댓글이 없습니다.');
        return;
    }

    if (!confirm(`정말로 다음 댓글을 삭제 취소하시겠습니까?\n댓글번호: ${ids}`)) return
    const url = `/api/admin/comment?ids=${ids}`
    const formData = new FormData();
    formData.append('isDeleted', false);
    const option = {
        method: 'DELETE',
        body: formData,
    }
    fetch(url, option)
    .then(res => {
        if (res.ok) {
            alert('댓글 삭제 취소가 정상적으로 처리되었습니다.')
            requestData();
        }
        else {
            throw new Error();
        }
    })
    .catch(err => {
        alert('댓글 삭제 취소 과정에서 오류가 발생하였습니다');
    })
}



</script>

<style scoped>
.list-content__title{
    flex-direction: column;
}
</style>