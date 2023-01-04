window.addEventListener('load', function(){
	
	// 참고: https://www.youtube.com/watch?v=Sj48KG3DMYA
	// Custom Infinite Image Carousel Slider (HTML, CSS, JavaScript) Part 2 of 2
	
	const banner = document.querySelector('.banner');
	let bannerIndex = 1;
	const bannerImgs = banner.querySelectorAll('img');
	let imgNum = bannerImgs.length;
	const transitionTime = 600;
	const bannerIntervalTime = 5000;
	const bannerBtnLeft = document.querySelector('.banner__btn--left');
	const bannerBtnRight = document.querySelector('.banner__btn--right');
	
	const lastBannerImgClone = bannerImgs[imgNum - 1].cloneNode();
	const firstBannerImgClone = bannerImgs[0].cloneNode();
	banner.insertAdjacentElement('afterbegin', lastBannerImgClone);
	banner.insertAdjacentElement('beforeend', firstBannerImgClone);
	moveBanner();
	
	setInterval(()=>moveHandler('right'), bannerIntervalTime);
	const maxIndex = imgNum + 2;
	
	
	function moveBanner(){
		banner.style.transform = `translateX(-${bannerIndex * 100}%)`;
	}
	
	function moveHandler(dir){
		banner.style.transition = `transform ${transitionTime}ms ease-in-out`;
		dir == 'left' ? bannerIndex-- : bannerIndex ++;
		moveBanner();
	}
	
	bannerBtnLeft.onclick = () => moveHandler('left');
	bannerBtnRight.onclick = () => moveHandler('right');
	
	banner.addEventListener('transitionend', ()=>{
		if (bannerIndex <= 0){
			banner.style.transition = 'none';
			bannerIndex = maxIndex - 2;
			moveBanner();
		}
		
		if (bannerIndex >= maxIndex - 1){
			banner.style.transition = 'none';
			bannerIndex = 1;
			moveBanner();
		}
	})
});

//
 