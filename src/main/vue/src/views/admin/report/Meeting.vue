<template>
    <search-bar :searchOption="true" />
    <admin-table @search="searchHandler" :isLoaded="listData.isLoaded">
        <template #left-buttons>
            <input @click="checkAllHandler" class="checkbox" type="checkbox" name="table-checkbox" />
            <span @click="banMember" class="table-bar__icon table-bar__icon--user-ban"></span>
            <span @click="suspendMember" class="table-bar__icon table-bar__icon--user-suspend"></span>
            <span @click="cancelReport" class="table-bar__icon table-bar__icon--user-check"></span>
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
                <li class="admin-tb-3">신고자(id)</li>
                <li class="admin-tb-3">모임글 제목/작성자 닉네임(id)</li>
                <li class="admin-tb-3">종류</li>
                <li class="admin-tb-1">신고일자</li>
                <li class="admin-tb-1">처리 여부</li>
            </ul>
        </template>
        <template #list-content>
            <li v-for="(item, index) in listData.reportedMeetings" :key="index" class="table-list__content">
                <ul class="list-content">
                    <li class="list-content__id">
                        <input type="checkbox" class="checkbox" v-model="isChecked[index]">
                        <span>{{ item.id }}</span>
                    </li>
                    <li class="list-content__reporter admin-tb-3"> {{ `${item.reporterNickname}(${item.reporterId})` }}
                    </li>
                    <li class="list-content__reported admin-tb-3">
                        <div>{{ item.meetingTitle }}</div>
                        <div> {{ `${item.writerNickname}(${item.writerId})` }}</div>
                    </li>
                    <li class="admin-tb-3">{{ item.reportType }} </li>
                    <li class="admin-tb-1">{{ item.createdAt }}</li>
                    <li class="list-content__status admin-tb-1">
                        <span class="status status--round"
                            :class="item.processedAt ? 'status--positive' : 'status--negative'">
                            {{(item.processedAt) ? 'TRUE' : 'FALSE'}}
                        </span>
                    </li>
                </ul>
            </li>
        </template>
    </admin-table>
    <!-- <Modal @closeModal="closeModal" v-show="modalState.ban" :modalType="'ban'">
        <template #modal-header>
            <span class="admin-report-name bold">영구 정지 확인</span>
        </template>
        <template #modal-body>
            <div>
                <div class="admin-report-icon admin-report-icon--user-ban"></div>
                <span>정말로 다음 사용자를 영구정지하시겠습니까?</span>
            </div>
            <div class="reported-member">
                <div>사용자 : </div>
                <div>
                    <span v-for="(item, index) in members"></span>
                </div>
            </div>
        </template>
        <template #modal-footer>
            <span class="btn btn-round btn-action">확인</span>
            <span class="btn btn-round">취소</span>
        </template>
    </Modal>
    <Modal @closeModal="closeModal" v-show="modalState.suspendInput" :modalType="'suspendInput'">
        <template #modal-header>
            <span class="admin-report-name bold">정지 기간 설정</span>
        </template>
        <template #modal-body>
            <div>
                <div>
                    <div class="admin-report-icon admin-report-icon--user-ban"></div>
                    <span>활동 정지 기간을 설정해주세요</span>
                </div>
                <div class="reported-member">
                    <div>사용자 : </div>
                    <div>
                        <span v-for="(item, index) in members"></span>
                    </div>
                </div>
                <div>
                    <span>정지기간 : </span><input type="number" placeholder="기간입력 (단위: 일)">
                </div>
            </div>
        </template>
        <template #modal-footer>
            <span class="btn btn-round btn-action">확인</span>
            <span class="btn btn-round">취소</span>
        </template>
    </Modal>
    <Modal @closeModal="closeModal" v-show="modalState.suspendConfirm" :modalType="'suspendConfirm'">
        <template @closeModal="closeModal" #modal-header>
            <span class="admin-report-name bold">영구 정지 확인</span>
        </template>
        <template #modal-body>
            <div>
                <div>
                    <div class="admin-report-icon admin-report-icon--user-ban"></div>
                    <span>정말로 다음 회원을 기간 정지하겠습니까?</span>
                </div>
                <div class="reported-member">
                    <div>사용자 : </div>
                    <div>
                        <span v-for="(item, index) in members"></span>
                    </div>
                </div>
                <div>
                    <span>{{ `정지기간 : ${startDate} ~ ${endDate}` }}</span>
                </div>
            </div>
        </template>
        <template #modal-footer>
            <span class="btn btn-round btn-action">확인</span>
            <span class="btn btn-round">취소</span>
        </template>
    </Modal>
    <Modal @closeModal="closeModal" v-show="modalState.check" :modalType="'check'">
        <template #modal-header>
            <span class="admin-report-name bold">영구 정지 확인</span>
        </template>
        <template #modal-body>
            <div>
                <div class="admin-report-icon admin-report-icon--user-ban"></div>
                <span>정말로 다음 사용자를 영구정지하시겠습니까?</span>
            </div>
            <div class="reported-member">
                <div>사용자 : </div>
                <div>
                    <span v-for="(item, index) in members"></span>
                </div>
            </div>
        </template>
    </Modal> -->
</template>

<script setup>
import SearchBar from '../../../components/admin/SearchBar.vue';
import AdminTable from '../../../components/admin/Table.vue';
import Modal from '../../../components/admin/modal/AdminModal.vue';
import { computed, reactive, ref, watch } from 'vue';
import router from '../../../router'
import { useRoute } from 'vue-router';
import DetailModal from '../../../components/admin/modal/AdminDetailModal.vue';

const currentSelect = reactive({
    details: null
})

const modalState = reactive({
    ban: true,
    suspendInput: true,
    suspendConfirm: true,
    check: true,
})

const route = useRoute();


const listData = reactive({
    reportedMeetings: [],
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
    isChecked.length = 0;

    fetch(`/api/${route.path}${window.location.search}`)
        .then(res => res.json())
        .then(data => {
            listData.reportedMeetings = [];
            listData.count = data.count;
            data.reportedMeetings.forEach(item => {
                listData.reportedMeetings.push(item)
                isChecked.push(false);
            });
            listData.isLoaded = true;
        })
}
requestData();

const closeModal = (modalType) => {
    console.log(modalType);
    modalState[modalType] = false;
}

const banMember = () => {
    const ids = getCheckedIds();
    const members = getCheckedReportedMembers();

    if (ids.length == 0) {
        alert('선택한 신고항목이 없습니다.');
        return;
    }

    if (!confirm(`정말로 다음 사용자를 영구정지하시겠습니까?\n신고번호: ${ids}\n신고대상: ${[...members].join(', ')}\n해당 사용자가 작성한 신고 대상 글은 모두 삭제됩니다.`)) return
    const url = `/api/admin/report/meeting?ids=${ids}`
    const formData = new FormData();
    formData.append('period', '9999');
    const option = {
        method: 'PUT',
        body: formData,
    }
    fetch(url, option)
        .then(res => {
            if (res.ok) {
                alert('영구 정지가 정상적으로 처리되었습니다.')
                requestData();
            }
            else {
                throw new Error();
            }
        })
        .catch(err => {
            alert('신고처리 과정에서 오류가 발생하였습니다');
        })
}

const suspendMember = () => {
    const ids = getCheckedIds();
    const members = getCheckedReportedMembers();

    if (ids.length == 0) {
        alert('선택한 신고항목이 없습니다.');
        return;
    }

    let period = prompt('일시정지 기간을 입력해주세요.');
    if (period == null) return;

    period = parseInt(period);
    const blockedAt = new Date();
    const releasedAt = new Date();
    releasedAt.setDate(releasedAt.getDate() + period);
    if (isNaN(period)) {
        alert('숫자만 입력해주세요!');
        return;
    }
    if (!confirm(`정말로 다음 사용자를 일시정지하시겠습니까?\n신고번호: ${ids}\n신고대상: ${[...members].join(', ')}\n정지기간: ${blockedAt.toLocaleDateString()} ~ ${releasedAt.toLocaleDateString()}\n해당 사용자가 작성한 신고 대상 글은 모두 삭제됩니다.`)) return
    const url = `/api/admin/report/meeting?ids=${ids}`
    const formData = new FormData();
    formData.append('period', period);
    const option = {
        method: 'PUT',
        body: formData,
    }
    fetch(url, option)
        .then(res => {
            if (res.ok) {
                alert('일시 정지가 정상적으로 처리되었습니다.')
                requestData();
            }
            else {
                throw new Error();
            }
        })
        .catch(err => {
            alert('신고처리 과정에서 오류가 발생하였습니다' + err);
        })
}

const cancelReport = () => {
    const ids = getCheckedIds();
    const members = getCheckedReportedMembers();

    if (ids.length == 0) {
        alert('선택한 신고항목이 없습니다.');
        return;
    }

    if (!confirm(`정말로 다음 신고 내용을 취소처리하시겠습니까?\n신고번호: ${ids}\n신고대상: ${[...members].join(', ')}\n취소 이후에는 삭제처리된 글이 복구되고, 계정정지상태가 취소됩니다.`)) return
    const url = `/api/admin/report/meeting?ids=${ids}`
    const option = {
        method: 'DELETE'
    }
    fetch(url, option)
        .then(res => {
            if (res.ok) {
                alert('신고 취소가 정상적으로 처리되었습니다.')
                requestData();
            }
            else {
                throw new Error();
            }
        })
        .catch(err => {
            alert('신고처리 과정에서 오류가 발생하였습니다' + err);
        })
}

function getCheckedIds() {
    let ids = [];
    for (let index in isChecked) {
        if (isChecked[index]) {
            const reportedMeeting = listData.reportedMeetings[index];
            ids.push(reportedMeeting.id);
        }
    }
    return ids;
}

function getCheckedReportedMembers() {
    let members = new Set();
    for (let index in isChecked) {
        if (isChecked[index]) {
            const reportedMeeting = listData.reportedMeetings[index];
            members.add(`${reportedMeeting.writerNickname}(${reportedMeeting.writerId})`);
        }
    }
    return members;
}

</script>

<style scoped>
.list-content__reported {
    flex-direction: column;
}

.admin-report-icon {
    width: 100px;
    height: 100px;
    background-repeat: no-repeat;
    background-position: center;
    background-size: contain;
}

.admin-report-icon--user-ban {
    background-image: url(/images/icon/user-ban.svg);
}

.admin-report-icon--user-check {
    background-image: url(/images/icon/user-check.svg);
}

.admin-report-icon--user-suspend {
    background-image: url(/images/icon/user-suspend.svg);
}



.admin-report-name {
    margin-right: auto;
    color: white;
}

.reported-member {
    display: flex;
}

.reported-member>div:nth-child(1)>span {
    color: var(--red);
}

.modal__body>div {
    display: flex;
    flex-direction: column;
    align-items: center;
}
</style>