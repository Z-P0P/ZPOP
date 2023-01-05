window.addEventListener("load", function() {

	const meetingId = document.querySelector(".meeting-id").dataset.id;
	const btnModalParticipate = document.querySelector("#participate-confirm");//참여하기 모달 우측 버튼
	const btnModalCancelParticipation = document.querySelector("#cancel-confirm");//취소 모달 우측 버튼
	const btnMeetingClose = document.querySelector("#btn-close");//모임마감하기 버튼
	const btnModalMeetingClose = document.querySelector("#close-confirm");//마감 모달 우측 버튼
	const participantUl = document.querySelector(".participant__list");
	const participantCount = document.querySelector("#participant-count");
	const arrowUp = document.querySelector(".icon-arrow-up");
	const arrowDown = document.querySelector(".icon-arrow-down");
	
	btnModalParticipate.addEventListener("click", ()=>{
			participate(meetingId, participantUl,participantCount);
		
	});
	
	btnModalCancelParticipation.addEventListener("click",()=>{
		 	cancelParticipate(meetingId, participantUl,participantCount);
	});
	
	btnModalMeetingClose.addEventListener("click",()=>{
		closeMeeting(meetingId,btnMeetingClose);
	});
	
	const meetingTitleHambugerIcon = document.querySelector(".meeting__title-hambuger-icon");
	
	meetingTitleHambugerIcon.onclick = function(e){
		
		
	}
		
	arrowUp.addEventListener("click",(e)=>{
		participantUl.classList.add("hidden");
		e.target.classList.add("hidden");
		e.target.nextElementSibling.classList.remove("hidden");
	});
	arrowDown.addEventListener("click",(e)=>{
		participantUl.classList.remove("hidden");
		e.target.classList.add("hidden");
		e.target.previousElementSibling.classList.remove("hidden");
	});

	
});

function participate(meetingId, participantUl,participantCount){
	const data = {
	      method: "POST",
	      headers: {
	        "Content-Type": "application/json",
	      },
	      body: JSON.stringify({
	        meetingId: meetingId,
	      }),
      };

    fetch(`/meeting/${meetingId}/participate`, data)
      .then((response) => {
        if (response.ok) {
		  console.log("참여등록완료")
          document.querySelector("#modal-wrapper-participation").classList.add("hidden");
          while (participantUl.hasChildNodes())//기존 참여자아이콘 한개씩 삭제
            participantUl.removeChild(participantUl.firstChild);
          getParticipant(meetingId, participantUl, participantCount);//참여자 목록 refresh
          
          const btnParticipate = document.querySelector("#btn-participation");//참여하기 버튼
          btnParticipate.innerText = "참여취소";
          btnParticipate.id = "btn-cancel-participation";
          btnParticipate.dataset.modal = "#modal-wrapper-cancel";
          
        } else alert("시스템 장애로 등록이 안되고 있습니다.");
      })
      .catch((error) => {
        console.error("실패:", error);
      });
}

function cancelParticipate(meetingId, participantUl,participantCount){
	const data = {
	      method: "DELETE",
	      headers: {
	        "Content-Type": "application/json",
	      },
	      body: JSON.stringify({
	        meetingId: meetingId,
	      }),
      };

    fetch(`/meeting/${meetingId}/leave`, data)
      .then((response) => {
        if (response.ok) {
			return response;
        } else alert("시스템 장애로 등록이 안되고 있습니다.");
      })
      .then(data =>data.json())
	  .then(result => {
		  console.log(result)
		  if(result){
			  console.log("참여취소완료")
	          while (participantUl.hasChildNodes())//기존 참여자아이콘 한개씩 삭제
	            participantUl.removeChild(participantUl.firstChild);
	          getParticipant(meetingId, participantUl,participantCount);//참여자 목록 refresh
	          
	          const btnCancelParticipate = document.querySelector("#btn-cancel-participation");//참여취소하기 버튼
	          btnCancelParticipate.innerText = "참여하기";
          	  btnCancelParticipate.id = "btn-participation";
          	  btnCancelParticipate.dataset.modal = "#modal-wrapper-participation";
		  }
	  })
      .catch((error) => {
        console.error("실패:", error);
      });
}
function closeMeeting(meetingId, btnMeetingClose){
	
	const data = {
	      method: "PATCH",
	      headers: {
	        "Content-Type": "application/json",
	      },
	      body: JSON.stringify({
	        meetingId: meetingId,
	      }),
      };
	fetch(`/meeting/${meetingId}/close`,data)
		.then(response => {
            if (response.ok) {
				return response;
            }
            else alert("시스템 장애로 등록이 안되고 있습니다.");
      	})
      	.then(data => data.json())
		.then(result => {
			if(result){
				console.log("미팅이 마감됨")
				btnMeetingClose.remove();
				const template = `<span class="btn btn-round btn-join btn-dead">모집완료</span>`;
				document.querySelector(".meeting__btn").insertAdjacentHTML("afterbegin", template);
        
			}
		});
}
function getParticipant(meetingId, participantUl,participantCount){
	
	fetch(`/meeting/${meetingId}/participant`)
	.then(response => {
		if (response.ok) {
			return response;
		}
		else console.log("시스템 장애로 등록이 안되고 있습니다.")
	})
	.then(data => data.json())
	.then(participants => {
		let count = participants.length;
		participantCount.innerText = count;
		for (const p of participants) {
			let template = `
				<li>
		            <div class="participant__info">
		                <img src="/images/girl.svg">  
		                <span>${p.nickname}</span>
		            </div>
	            </li>
			`
			participantUl.insertAdjacentHTML("beforeend", template);
		}
	});
}