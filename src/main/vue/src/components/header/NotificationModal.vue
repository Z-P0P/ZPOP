<script setup>
    import { useHeaderStore } from '../../stores/headerStore';
    import api from "@/api";
    import { reactive, ref, defineEmits } from 'vue';
    import { useMemberStore } from '../../stores/memberStore';
    const notificationAbsence = ref(false);
    const emit = defineEmits(['checkNotification']);
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

    const memberStore = useMemberStore();
    const id =  memberStore.id;

    function getMemberNotification(id){
        api.notification.getNotification(id)
        .then((response)=>response.json())
        .then((data)=>{
            state.notification = data;
             // 받은 알림이 없는 경우 알림 모달 창에 알림 없음 처리하기
            if(state.notification.length==0){
                emit('checkNotification');
                notificationAbsence.value=true;
            }
        })
    }

    function readNotification(event){
        // 알림 클릭시 알림을 element를 없애기
        let deleteTarget = event.target;
        for (deleteTarget; !deleteTarget.classList.contains('notification');
        deleteTarget = deleteTarget.parentElement);
        
        let notificationModal = deleteTarget.parentElement;
        notificationModal.removeChild(deleteTarget);

        const typeId = deleteTarget.getAttribute("data-type");
        const id = deleteTarget.getAttribute("data-id");
        if(typeId == 1){
            
        } 
        else {
             // 데이터 베이스에 해당 알림 읽음처리하기
            api.notification.readOne(id)
            .then((response)=>{
            // router.push('/users/eduardo') 수정 하기
            location.href=deleteTarget.getAttribute("data-url");
            })
        }
        
     
    }   

    // 모두 읽기 버튼을 누르면 모든 알림이 사라진다.
    function readAllNotifications(event){
        state.notification.splice(0);
        api.notification.readAll(id)
        .then((response)=>{
            notificationAbsence.value=true;
            emit('checkNotification');
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
                <div v-for="item in state.notification" @click.prevent="readNotification" v-bind:data-url="item.url" v-bind:data-id="item.id" v-bind:data-type="item.type" class="notification">
                    <span v-if="item.type=='1'">😃 평가하지 않은 모임이 있어요 !</span>
                    <span v-else-if="item.type=='2'">😎 내 모임에 새로운 참여자가 있어요 !</span>
                    <span v-else-if="item.type=='3'">💬 내 모임에 댓글이 달렸어요 !</span>
                    <span v-else-if="item.type=='4'">💬 내 댓글에 답글이 달렸어요 !</span>
                    <p v-if="item.type=='1'">이동하기</p>
                    <p v-else>{{item.elapsedTime}}</p>
                </div>
                <div class="notification notification--none" v-show="notificationAbsence">받은 알림이 없네요!</div>
        </div>
        <div class="blank-div">
            <div v-if="!notificationAbsence" class="readAll-btn btn btn-round" @click.prevent="readAllNotifications">모두 읽기</div>
            <div v-if="notificationAbsence" class="readAll-btn btn btn-round deactivated-btn">알림 없음</div>
        </div>
    </div>
</template>

<style>
@import url(../../../../resources/static/css/member/component/notification-modal.css);
</style>