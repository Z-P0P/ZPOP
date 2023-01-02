
/* 💙 클릭시 팝업창이 튀어나오게 할 버튼에 id showPopup 적용해주세요💚 */
/* popup__wrap 부분의 display를 none/flex로 교체함에 따라서 보이고 안보이고 설정이 가능해집니다. */

window.addEventListener("load", function() {
    let showPopup = document.querySelector("#showPopup");
    let popupWrap = document.querySelector("#popup");
    let closeBtn = document.querySelector("#close");
    let confirmBtn = document.querySelector("confirm");
    
    showPopup.addEventListener("click", function(){
        popupWrap.className = "popup__wrap-active";
        
    });

    closeBtn.addEventListener("click", function(){
        popupWrap.className ="popup__wrap-inactive";
    })

    confirmBtn.addEventListener("click", function() {
        //수락눌렀을 때 수행할 내용 작성
    })

})
