<template>
    <transition @after-leave="onAfterLeave">
        <div id="js-preloader" class="js-preloader" :class="{'js-preloader--whole-page' : isWholePage}">
            <div class="preloader-inner">
                <span class="dot"></span>
                <div class="dots">
                    <span></span>
                    <span></span>
                    <span></span>
                </div>
            </div>
        </div>
    </transition>
</template>

<script setup>
import { defineProps } from 'vue';

const props = defineProps(['isWholePage']);
const emit = defineEmits(['loadingEnd']);

const onAfterLeave = () => {
    emit('loadingEnd');
}
</script>

<style scoped>
.v-enter-active,
.v-leave-active {
  transition: opacity 0.3s 0.5s ease;
}

.v-enter-from,
.v-leave-to {
  opacity: 0;
}


.js-preloader{
  display: inline-block;
  position: relative;
}

.js-preloader--whole-page {
    position: fixed;
    top: 60px;
    left: 0;
    right: 0;
    bottom: 0;
    display: flex;
    align-items: center;
    justify-content: center;
    backdrop-filter: blur(3px);
    background: #ffffffdd;
    z-index: 10;
}

@media (min-width: 576px) {
    
    .js-preloader--whole-page {
        top: 80px;
    }
}

@keyframes dot8567 {
    50% {
        transform: translateX(96px);
    }
}

@keyframes dots723423 {
    50% {
        transform: translateX(-31px);
    }
}

.preloader-inner {
    position: relative;
    width: 142px;
    height: 40px;
    background: transparent;
    display:flex;
}

.preloader-inner .dot {
    position: absolute;
    width: 10px;
    height: 10px;
    left: 21px;
    background: var(--main-color);
    border-radius: 50%;
    transform: translateX(0);
    animation: dot8567 1.2s infinite;
}

.js-preloader--whole-page .preloader-inner .dot {
    width: 16px;
    height: 16px;
    left: 15px;
}


.preloader-inner .dots {
    transform: translateX(0);
    margin-left: 31px;
    animation: dots723423 1.2s infinite;
}

.preloader-inner .dots span {
    display: block;
    float: left;
    width: 10px;
    height: 10px;
    margin-left: 22px;
    background: var(--tiffany-blue);
    border-radius: 50%;
}

.js-preloader--whole-page .preloader-inner .dots span {
    width: 16px;
    height: 16px;
    margin-left: 16px;
}
</style>