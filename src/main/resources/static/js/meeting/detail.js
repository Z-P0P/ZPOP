window.addEventListener("load",function(){
		
	 const participationBtn = document.querySelector("#participation-btn");
	 const modalContainer = document.querySelector(".modal-container");
	participationBtn.onclick = function(e){
		e.preventDefault();
        const modal = document.querySelector(e.target.getAttribute("data-modal"));
        if (!modal.classList.contains("hidden")) {
          modal.classList.add("hidden");
          return;
        }
        modal.classList.remove("hidden");
	}
		
	let template = `
    <div class="modal-2 hidden" id="modal-wrapper-participation">

        <div class="modal-participation">
            <div class="modal__header">
                <span class="modal__x-icon"></span>
            </div>
            <div class="modal__body">
                <div class="modal-participation-content">
                    <div class="modal-icon">
                        <img src="/images/icon/handshake24.svg">
                    </div>
                    <div class="modal-text">
                        <strong>
                            찬우와 월드컵 응원할 사람
                        </strong>
                        <div class="next-line">이 모임에 참여하시겠어요?</div>
                    </div>
                </div>
                <div class="btn-modal">
                    <div class="btn-modal-left">다음에 할래요</div>
                    <div class="btn-modal-right">참여하기</div>
                </div>
            </div>
        </div>
        
    `      
    modalContainer.insertAdjacentHTML("afterend",template);
    
    const btnModalParticipate = document.querySelector(".btn-modal-right");
    const meetingId = document.querySelector(".meeting-Id").innerHTML;
    console.log(meetingId);   
  	const participantId = document.querySelector(".participant-Id").innerText;
    
    
    
    btnModalParticipate.onclick = function(e){
	
			
			
			
		    	const data = { 
    			  method: "POST", // 또는 'PUT'
                  headers: {
                  "Content-Type": "application/json",
               	},
                  body: JSON.stringify({
               	  		"meetingId":meetingId,
                	  "participantId":participantId
                  })
        		}

                fetch("http://localhost:8080/meeting/participate",data)
                .then((response) => {
                  console.log('성공:', data);
                })
                .catch((error) => {
       			console.error('실패:', error);
       			alert("참여에 실패했습니다");
                });
	
			
	}
    
})