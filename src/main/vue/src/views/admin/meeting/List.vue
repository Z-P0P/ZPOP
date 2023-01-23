<template>
    <search-bar :searchOption="true"/>
    <admin-table @search="searchHandler" :isLoaded="listData.isLoaded">
        <template #left-buttons>
            <input @click="checkAllHandler" class="checkbox" type="checkbox" name="table-checkbox"/>
            <span @click="deleteAllMeeting" class="table-bar__icon table-bar__icon-check"></span>
            <span @click="cancelDeleteAllMeeting" class="table-bar__icon table-bar__icon-x"></span>
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
                        <img src="/images/icon/up-down.svg" alt="">
                    </button>
                </li>
                <li class="admin-tb-3">제목/방장</li>
                <li class="admin-tb-1">참여자</li>
                <li class="admin-tb-2">등록 일시</li>
                <li class="admin-tb-1">마감 여부</li>
                <li class="admin-tb-1">삭제 여부</li>
            </ul>
        </template>
        <template #list-content>
            <li @click="showDetails(item.id)" v-for="(item, index) in listData.meetings" :key="index" class="table-list__content">
                <ul class="list-content">
                    <li class="list-content__id">
                        <input type="checkbox" class="checkbox" :checked="isChecked[index]">
                        <span>{{ item.id }}</span>
                    </li>
                    <li class="list-content__title admin-tb-3">
                        <span class="bold">{{ item.title }}</span>
                        <span>{{ item.hostNickname }}</span>
                    </li>
                    <li class="admin-tb-1">{{`${item.participantsNum}/${item.maxMember}`}}</li>
                    <li class="list-content_date admin-tb-2">{{ item.createdAt }}</li>
                    <li class="list-content__status admin-tb-1">
                        <span class="status status--round" :class="item.closed ? 'status--negative' : 'status--positive'">
                            {{(item.closed) ? 'TRUE' : 'FALSE'}}</span>
                    </li>
                    <li class="list-content__status admin-tb-1">
                        <span class="status status--round" :class="item.deleted ? 'status--negative' : 'status--positive'">
                            {{(item.deleted) ? 'TRUE' : 'FALSE'}}
                        </span>
                    </li>
                </ul>
            </li>
        </template>
    </admin-table>
    <DetailModal v-if="meetingDetails.isLoaded" @closeDetailModal="closeDetailModal">
        <template #modal-header>
            <span class="admin-report-name bold">모임 상세 조회</span>
        </template>
        <template #modal-body>
            <ul class="details">
                <li class="details__status-container">
                    <div class="details__status">
                        <div class="input__label">활성여부</div>
                        <input type="checkbox" class="checkbox" :checked="meetingDetails.deleted" disabled>
                    </div>
                    <div class="details__status">
                        <div class="input__label">마감여부</div>
                        <input type="checkbox" class="checkbox" :checked="meetingDetails.closed" disabled>
                    </div>
                </li>
                <li class="details__members">
                    <div class="input__label">주최자</div>
                    <ul class="participant__list" id="reg-member">
                        <div class="participant__info">
                            <img :src="(meetingDetails.regMemberProfile == null) ?
                                '/images/icon/user-profile-grey.svg' : 
                                `/images/profile/${meetingDetails.regMemberProfile}`">
                            <span>{{ meetingDetails.regMemberNickname }}</span>
                        </div>
                    </ul>
                </li>
                <li class="details__members">
                    <participants :detail="meetingDetails"/>
                </li>
                <li class="details__option-container">
                    <meeting-form-select-input class="details__option"
                    @selectBoxClick="selectBoxClickHandler" @optionClick="optionClickHandler"
                    :input="meetingDetails.inputs.categories"/>
                    <meeting-form-select-input @selectBoxClick="selectBoxClickHandler" @optionClick="optionClickHandler"
                    :input="meetingDetails.inputs.regions" />
                    <meeting-form-text-input class="details__input-text"
                    @textInput="textInputHandler" :input="meetingDetails.inputs.detailRegion"/>
                </li>
                <li class="details__option-container">
                    <meeting-form-select-input class="details__option"
                    @selectBoxClick="selectBoxClickHandler" @optionClick="optionClickHandler"
                    :input="meetingDetails.inputs.contactTypes"/>
                    <meeting-form-text-input class="details__input-text"
                    @textInput="textInputHandler" :input="meetingDetails.inputs.contact"/>
                </li>
                <li class="details__option-container">
                    <meeting-form-select-input @selectBoxClick="selectBoxClickHandler" @optionClick="optionClickHandler"
                    :input="meetingDetails.inputs.ageRanges" />
                    <meeting-form-select-input @selectBoxClick="selectBoxClickHandler" @optionClick="optionClickHandler"
                    :input="meetingDetails.inputs.genderCategories" />
                    <meeting-form-select-input @selectBoxClick="selectBoxClickHandler" @optionClick="optionClickHandler"
                    :input="meetingDetails.inputs.maxMembers" />
                </li>
                <li class="details__input-text">
                    <meeting-form-text-input @textInput="textInputHandler" :input="meetingDetails.inputs.title" />
                </li>
                <li class="details__input-text">
                    <label class="input-text__label" for="">내용</label>
                    <div class="details__content" v-html="meetingDetails.inputs.content.currentValue">
                    </div>
                </li>
                <li class="details__date-container">
                    <div class="details__date">
                        <span>시작 일시</span>
                        <span id="started-at">{{meetingDetails.inputs.startedAt.currentValue}}</span>
                    </div>
                    <div class="details__date">
                        <span>마감 일시</span>
                        <span id="closed-at">{{meetingDetails.closedAt}}</span>
                    </div>
                </li>
                <li class="details__date-container">
                    <div class="details__date">
                        <span>등록 일시</span>
                        <span id="created-at">{{meetingDetails.createdAt}}</span>
                    </div>
                    <div class="details__date">
                        <span>수정 일시</span>
                        <span id="updated-at">{{meetingDetails.updatedAt}}</span>
                    </div>
                    <div class="details__date">
                        <span>삭제 일시</span>
                        <span id="deleted-at">{{meetingDetails.deletedAt}}</span>
                    </div>
                </li>
                <li class="details__count-container">
                    <div class="details__count">
                        <span>조회수</span>
                        <span id="num-comments">{{meetingDetails.viewCount}}</span>
                    </div>
                    <div class="details__count">
                        <span>댓글수</span>
                        <span id="num-comments">{{meetingDetails.commentCount}}</span>
                    </div>
                </li>
            </ul>
        </template>
        <template #modal-footer>
            <span v-if="!meetingDetails.deleted" class="btn btn-round btn-action--admin" @click="deleteMeeting()">삭제</span>
            <span v-if="meetingDetails.deleted" class="btn btn-round btn-action--admin" @click="cancelDeleteMeeting()">삭제취소</span>
        </template>
    </DetailModal>
</template>

<script setup>
import { reactive, computed, watch } from 'vue';
import { useRoute } from 'vue-router';
import router from '../../../router';
import AdminUpdateForm from '../../../utils/admin/meeting/AdminUpdateForm'
import MeetingFormSelectInput from '@/components/meeting/MeetingFormSelectInput.vue'
import MeetingFormTextInput from '@/components/meeting/MeetingFormTextInput.vue'
import DetailModal from '../../../components/admin/modal/AdminDetailModal.vue';
import SearchBar from '../../../components/admin/SearchBar.vue';
import AdminTable from '../../../components/admin/Table.vue';
import Participants from '../../../components/meeting/Participants.vue';


const route = useRoute();

let details = new AdminUpdateForm();
details.addDefaultInputs();

const meetingDetails = reactive(details);

const listData = reactive({
    meetings: [],
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
    requestList();
})

const requestList = () => {
    listData.isLoaded = false;
    //배열초기화
    isChecked.length = 0;
    fetch(`/api/admin/meeting/list${window.location.search}`)
        .then(res => res.json())
        .then(data => {
            listData.meetings = [];
            listData.count = data.count;
            data.meetings.forEach(item => {
                listData.meetings.push(item)
                isChecked.push(false);
            });
            listData.isLoaded = true;
        })
}

requestList();


const requestDetails = (meetingId) => {
    meetingDetails.meetingId = meetingId;
    meetingDetails.getRegisteredMeetingDetails();
}

const showDetails = (meetingId) => {
    requestDetails(meetingId);
}

const deleteMeeting = () => {
    meetingDetails.deleteMeeting();
    requestList();
}

const cancelDeleteMeeting = () => {
    meetingDetails.cancelDeleteMeeting();
    requestList();
}

const deleteAllMeeting = () => {
    let ids = [];
    for (let index in isChecked){
        if (isChecked[index]){
            ids.push(listData.meetings[index].id);
        }
    }
    meetingDetails.deleteAllMeeting(ids);
    requestList();
}

const cancelDeleteAllMeeting = () => {
    let ids = [];
    for (let index in isChecked){
        if (isChecked[index]){
            ids.push(listData.meetings[index].id);
        }
    }
    meetingDetails.cancelDeleteAllMeeting(ids);
    requestList();
}


</script>

<style scoped>

.list-content__title {
    flex-direction: column;
}

.details{
	display:flex;
	flex-direction:column;
	row-gap:16px;
}


.details__status-container{
  display: flex;
  column-gap:30px;
}

.details__status{
  display: flex;
  flex-direction: column;
  row-gap: 10px;
}

.details__option-container{
  display: flex;
  column-gap:10px;
}

.details__option{
  width:200px;
}


.details__date-container{
  display: flex;
  column-gap:30px;
}

.details__date{
	display:flex;
	flex-direction:column;
}

.details__count-container{
  display: flex;
  column-gap:30px;
}

.details__count{
  display: flex;
  flex-direction: column;
}

.details__date{
  display: flex;
  flex-direction:column;
}

.details__date > span:first-child,
.details__count > span:first-child{
    font-weight: var(--bold);
}

.details__date > span:last-child,
.details__count > span:last-child{
	border-bottom: 2px solid var(--admin-grey);
	margin-top:8px;
	height:22px;
    width:100%;
    padding: 0 10px;
    text-align:center;
}


.details__members{
	min-height:88px;
}

.details__input-text{
	display:flex;
	flex-direction:column;
	row-gap:10px;
	
}

.details__input-text > input{
	padding:10px 20px;
	border-top-style: hidden;
 	border-right-style: hidden;
  	border-left-style: hidden;
	border-bottom: 2px solid var(--admin-grey);	
	height:42px;
	background-color:transparent;
	font-size:18px;
}

.details *{
	color:var(--admin-grey);
}

.details__content :deep(img){
    width:100%;
}

.admin-report-name{
    color:white;
    font-weight: var(--bold);
}

.details__members :deep(.participant__info:hover){
    background-color:var(--true-blue);
}

</style>