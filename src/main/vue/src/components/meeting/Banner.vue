<template>
    <div class="banner-container">
      	<div @transitionend="transitionendHandler" class="banner" :style="`transition: ${banner.transition}; transform: ${banner.transform}`">
            <router-link v-for="(banner, index) in bannerList" :to="`${banner.link == null ? '#' : banner.link}`"><img :src="`/image/banner/${banner.imagePath}`" :key="index" :alt="banner.name"></router-link>
      	</div>
   		<div @click="onLeftBtnClick" class="banner__btn banner__btn--left icon icon-arrow-left"></div>
    	<div @click="onRightBtnClick" class="banner__btn banner__btn--right icon icon-arrow-right"></div>
    </div>
</template>

<script setup>
import { reactive } from 'vue';

const transitionTime = 600;
const bannerIntervalTime = 5000;
const banner = reactive({
	index : 1,
	imgNum : 0,
	maxIndex : 1,
	transform: "",
	transition: "",
})

const moveBanner = () => {
	banner.transform = `translateX(-${banner.index * 100}%)`;
}
moveBanner();

const emit = defineEmits(['bannerLoaded']);
const bannerList = reactive([]);
const getActiveList = () => {
	const url = '/api/banner'
	fetch(url)
	.then(res=>res.json())
	.then(data=>{
		data.forEach(bannerInfo=>bannerList.push(bannerInfo));
		const firstBanner = bannerList[0];
		const lastBanner = bannerList[bannerList.length - 1];
		bannerList.push(firstBanner);
		bannerList.unshift(lastBanner);
		banner.imgNum = data.length;
		banner.maxIndex = bannerList.length;
		emit('bannerLoaded');
	})
}
getActiveList();

const transitionendHandler = () => {
	if (banner.index <= 0) {
        banner.transition = "none";
        banner.index = banner.maxIndex - 2;
        moveBanner();
      }
    
      if (banner.index >= banner.maxIndex - 1) {
        banner.transition = "none";
        banner.index = 1;
        moveBanner();
      }
}

const onLeftBtnClick = () => moveHandler("left");
const onRightBtnClick = () => moveHandler("right");

const moveHandler = (dir) => {
      if (banner.index >= banner.maxIndex - 1 || banner.index <= 0) return;
    
      banner.transition = `transform ${transitionTime}ms ease-in-out`;
      dir == "left" ? banner.index-- : banner.index++;
      moveBanner();
}
    
setInterval(() => moveHandler("right"), bannerIntervalTime);

</script>
<style scoped>
.banner-container{
  width: 100%;
  height: 96px;
  padding:none;
  margin:auto;
  overflow:hidden;
  position:relative;
}

.banner{
	display:flex;
}


@media (min-width: 576px) {
  .banner{
	display:flex;
  }
}

.banner > a {
	flex: 0 0 100%;
	width: 100%;
	height: 96px;
}

.banner > a > img{
	width:100%;
	height: 100%;
	object-fit: cover;
	object-position: 50% 50%;
	float:left;
}


@media (min-width: 576px) {
  .banner > a > img{
	object-fit: cover;
  }
}


@media (min-width: 1200px) {
  .banner > a > img {
    object-fit: cover;
  }
}

.banner__btn{
	opacity:0;
	width:20px;
	height:20px;
	border-radius:10px;
	position:absolute;
	top:50%;
	transform:translateY(-50%);
	background-color:var(--light-grey1);
	transition:opacity 0.3s;
	background-size:80%;
}

@media (min-width: 576px) {
	.banner__btn{
		width:40px;
		height:40px;
		border-radius:20px;
	}
}

.banner-container:hover .banner__btn{
	opacity: 0.5;
}

.banner__btn--left{
	left: 5px;
}

.banner__btn--right{
	right:5px;
	background-position: 60% 50%;
}

@media (min-width: 576px) {
	.banner__btn--left{
		left: 10px;
	}
}


@media (min-width: 576px) {
	.banner__btn--right{
		right:10px;
	}
}


@media (min-width: 576px) {
  .banner-container, .banner > a {
    height: 160px;
  }
}

@media (min-width: 1200px) {
  .banner-container, .banner > a {
    height: 255px;
  }
}

</style>