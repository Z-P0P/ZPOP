export default function() {
    const banner = document.querySelector(".banner");
    const bannerImgs = banner.querySelectorAll('img');

    let bannerIndex = 1;
    let imgNum = bannerImgs.length;
    const transitionTime = 600;
    const bannerIntervalTime = 5000;
    const bannerBtnLeft = document.querySelector(".banner__btn--left");
    const bannerBtnRight = document.querySelector(".banner__btn--right");

    moveBanner();
    
    setInterval(() => moveHandler("right"), bannerIntervalTime);
    const maxIndex = imgNum;
    
    function moveBanner() {
      banner.style.transform = `translateX(-${bannerIndex * 100}%)`;
    }
    
    function moveHandler(dir) {
      if (bannerIndex >= maxIndex - 1 || bannerIndex <= 0) return;
    
      banner.style.transition = `transform ${transitionTime}ms ease-in-out`;
      dir == "left" ? bannerIndex-- : bannerIndex++;
      moveBanner();
    }
    
    bannerBtnLeft.onclick = () => moveHandler("left");
    bannerBtnRight.onclick = () => moveHandler("right");
    
    banner.addEventListener("transitionend", () => {
      if (bannerIndex <= 0) {
        banner.style.transition = "none";
        bannerIndex = maxIndex - 2;
        moveBanner();
      }
    
      if (bannerIndex >= maxIndex - 1) {
        banner.style.transition = "none";
        bannerIndex = 1;
        moveBanner();
      }
    });
}

