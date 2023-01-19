<script setup>
import { useHeaderStore } from '../../stores/headerStore';
import api from "@/api";
import { reactive, ref, onBeforeMount } from 'vue';

const store = useHeaderStore();
const close = () => {
    store.changeNotificationState();
}
const state = reactive({
    notification:{
        memberId:0,
        url:"",
        type:0,
        elapsedTime:""
    }
})

const id = 18;
function getMemberNotification(id){
    api.notification.getNotification(id)
    .then((response)=>response.json())
    .then((data)=>{
        state.notification = data;
        console.log(state.notification);
    })
}

function readNotification(event){
    // 알림 클릭시 알림을 element를 없애기
    let deleteTarget = event.target;
    for (deleteTarget; !deleteTarget.classList.contains('notification');
    deleteTarget = deleteTarget.parentElement);
    
    let notificationModal = deleteTarget.parentElement;
    notificationModal.removeChild(deleteTarget);
    
    // 데이터 베이스에 해당 알림 읽음처리하기
    const id = deleteTarget.getAttribute("data-id");
    api.notification.readOne(id)
    .then((response)=>{
        // router.push('/users/eduardo') 수정 하기
        location.href=deleteTarget.getAttribute("data-url");
    })
}   

// 모두 읽기 버튼을 누르면 모든 알림이 사라진다.
const notificationAbsence = ref(false);
function readAllNotifications(event){
    state.notification.splice(0);
    const id = 18;
    api.notification.readAll(id)
    .then((response)=>{
        notificationAbsence.value=true;
    })
}

getMemberNotification(id);

</script>

<template>
    <div class="notification-modal">
        <div class="button--container">
            <input type="button" class="notification__close-btn" @click="close">
        </div>
        <div class="notification-container">
                <div v-for="item in state.notification" @click.prevent="readNotification" v-bind:data-url="item.url" v-bind:data-id="item.id" class="notification">
                    <span v-if="item.type=='1'">😃 평가하지 않은 모임이 있어요 !</span>
                    <span v-else-if="item.type=='2'">😎 내 모임에 새로운 참여자가 있어요 !</span>
                    <span v-else-if="item.type=='3'">💬 내 모임에 댓글이 달렸어요 !</span>
                    <span v-else-if="item.type=='4'">💬 내 댓글에 답글이 달렸어요 !</span>
                    <p>{{item.elapsedTime}}</p>
                </div>
                <div class="notification notification--none" v-show="notificationAbsence">받은 알림이 없네요!</div>
        </div>
        <div class="blank-div">
            <div v-if="!notificationAbsence" class="readAll-btn btn btn-round" @click.prevent="readAllNotifications">모두 읽기</div>
            <div v-if="notificationAbsence" class="readAll-btn btn btn-round deactivated-btn">모두 읽음</div>
        </div>
    </div>
</template>

<style>
.notification-modal {
    width: 320px;
    min-height: 300px;
    position: absolute;
    top: 80px;
    right: -28px;
    border-radius: 5px;
    box-shadow: 0px 5px 15px rgba(0, 0, 0, 0.2);
    background-color: white;
}

.button--container {
    display: flex;
}

.notification-container {
    overflow: scroll;
    height: 260px;
    position: static;
    -ms-overflow-style: none;
    /* IE and Edge */
    scrollbar-width: none;
    /* Firefox */
}

.notification-container::-webkit-scrollbar {
    display: none;
    /* Chrome, Safari, Opera*/
}

.blank-div {
    height: 60px;
    background-color: white;
    position: relative;
    border-radius: 0 0 5px 5px;
    /*	top : 280px;*/
}


.readAll-btn {
    background-color: var(--main-color);
    cursor: pointer;
    width: 100px;
    margin: 10px 0 10px 205px;
}


.notification__close-btn {
    height: 32px;
    width: 32px;
    border: none;
    background: url(/images/icon/x.svg) no-repeat;
    background-size: 32px;
    cursor: pointer;
    margin: 10px 0 0 284px;
}


.header__notification-on button::before {
    width: 24px;
    height: 24px;
    display: inline-block;
    content: "";
    background: url(/images/icon/notification-on.svg) center/contain no-repeat;
}

.deactivated-btn{
	cursor: default;
	background-color:#8B8B8B;
}

.notification {
    font-size: 16px;
    border-bottom: 1px solid #EBEBEB;
    height: 60px;
    width: 250px;
    margin: 10px 0 10px 35px;
    cursor: pointer;
}

.notification--none {
    font-size: 20px;
    border: none;
    cursor: default;
    height: 240px;
    line-height: 250px;
    text-align: center;
    color: #8B8B8B
}

.notification>p {
    color: #8B8B8B;
    margin: auto 0;
    padding-top: 5px;
}

.header__notification-on {
    position: relative;
}


@media (min-width: 576px) {

    .notification-modal {
        width: 353px;
        height: 360px;
        position: absolute;
        top: 100px;
        right: -12px;
    }

    .notification {
        font-size: 18px;
        width: 280px;
    }

    .readAll-btn {
        width: 131px;
        height: 35px;
        margin: 10px 0 10px 205px;
    }

    .header__notification-on button::before {
        width: 32px;
        height: 32px;
    }

    .notification__close-btn {
        margin: 10px 0 0 310px;
    }
}
</style>