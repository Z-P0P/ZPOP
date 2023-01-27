<template>
    <svg width="60" height="60" viewBox="0 0 60 60" fill="none" xmlns="http://www.w3.org/2000/svg" @click="scrollUp">
        <rect width="60" height="0" rx="30" fill="#62B3B9"/>
        <path d="M19.3334 33.9999L30 23.3333L40.6667 33.9999" stroke="white" stroke-width="2" stroke-linecap="round"
            stroke-linejoin="round" />
        <path class="arrow" d="M19.3334 33.9999L30 23.3333L40.6667 33.9999" stroke="white" stroke-width="2" stroke-linecap="round"
            stroke-linejoin="round" />
    </svg>
</template>

<script setup>
const scrollUp = () => {
    window.scrollTo({
        top:0, 
        behavior: 'smooth',
    });
}
</script>

<style scoped>
.scroll-up-btn{
    position: fixed;
    background: no-repeat center/contain url(/images/icon/button-up.svg);
    left: 50%;
    top: 80%;
    height:46px;
    width:46px;
    margin-left: -23px; /*half the width*/
    cursor: pointer;
    transform:translateX(175px);
}

@media (min-width:768px) {
    .scroll-up-btn{
        transform:translateX(395px);
        margin-left: -30px;
        height: 60px;
        width:60px;
    }
}


@media (min-width:1200px) {
    .scroll-up-btn{
        transform:translateX(600px);
    }
}

svg:hover > .arrow{
    animation-name:arrow-up;
    animation-duration: 2s;
    animation-iteration-count: infinite;
}

@keyframes arrow-up {
    from{
        opacity:1;
    }
    to{
        transform: translateY(-10px);
        opacity: 0;
    }
}
</style>