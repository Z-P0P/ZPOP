<template>
    <div class="banner-container">
      	<div class="banner">
            <img v-for="(images, index) in bannerImages" :src="`/images/${images.path}`" :key="index" alt="베너이미지">
      	</div>
   		<div @click="onLeftBtnClick" class="banner__btn banner__btn--left icon icon-arrow-left"></div>
    	<div @click="onRightBtnClick" class="banner__btn banner__btn--right icon icon-arrow-right"></div>
    </div>
</template>

<script>
import { onMounted } from 'vue';
import bannerHandler from '../../utils/banner'
export default {
    setup(){
        const bannerImages = 
        [{ path: 'banner-jpg1.jpg' },
        { path: 'banner-jpg2.jpg' },
        { path: 'banner-jpg3.jpg' },
        //  { path: 'banner-cat.png'},
        //  { path: 'banner-bread.png'},
                                  ];
        const firstBanner = bannerImages[0];
        const lastBanner = bannerImages[bannerImages.length - 1];
        bannerImages.push(firstBanner);
        bannerImages.unshift(lastBanner);

        onMounted(()=>{
            bannerHandler();
        })
        return{
            bannerImages,
        }
    }
}
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

.banner > img{
	flex: 0 0 100%;
	width: 100%;
	height: 96px;
	object-fit: cover;
	object-position: 50% 50%;
	float:left;
}


@media (min-width: 576px) {
  .banner > img{
	object-fit: cover;
  }
}


@media (min-width: 1200px) {
  .banner > img {
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
  .banner-container, .banner > img{
    height: 160px;
  }
}

@media (min-width: 1200px) {
  .banner-container, .banner > img {
    height: 255px;
  }
}

</style>