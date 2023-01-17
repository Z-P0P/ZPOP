<template>
    <div class="notification-modal">
        <div class="button--container">
            <input type="button" class="notification__close-btn" @click="close">
        </div>
        <div class="notification-container"></div>
        <!-- 알림 들어가는 공간 -->
        <div class="blank-div">
            <div class="readAll-btn btn btn-round">모두 읽기</div>
        </div>
    </div>
</template>

<script>
import { useHeaderStore } from '../../stores/headerStore';

export default {
    setup() {
        const store = useHeaderStore();
        const close = () => {
            store.changeNotificationState();
        }
        return {
            close,
        }
    }
}
</script>

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