window.addEventListener("load", function() {

	const meetingId = document.querySelector(".meeting-id").dataset.id;
	const btnParticipate = document.querySelector("#btn-participation");//참여하기 버튼
	const btnModalParticipate = document.querySelector("#participate-confirm");//참여하기 모달 우측 버튼
	const btnMeetingClose = document.querySelector("#btn-close");//마감하기 버튼
	const btnModalClose = document.querySelector("#close-confirm");//마감하기 모달 우측 버튼
	const participantUl = document.querySelector(".participant__list");
	const participantCount = document.querySelector(".participant-count");
	const arrowUp = document.querySelector(".icon-arrow-up");
	const arrowDown = document.querySelector(".icon-arrow-down");
	btnModalParticipate.addEventListener("click", (e)=>{
		participate(meetingId, btnParticipate,participantUl);
	});
	
	btnModalClose.addEventListener("click",(e)=>{
		closeMeeting(meetingId,btnMeetingClose);
	});
	
	const meetingTitleHambugerIcon = document.querySelector(".meeting__title-hambuger-icon");
	
	meetingTitleHambugerIcon.onclick = function(e){
		console.log("햄버거 버튼입니다");
		
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
function participate(meetingId, btnParticipate, participantUl){
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
          document
            .querySelector("#modal-wrapper-participation")
            .classList.add("hidden");
          while (participantUl.hasChildNodes())
            //기존 참여자아이콘 한개씩 삭제
            participantUl.removeChild(participantUl.firstChild);
          getParticipant(meetingId, participantUl);
        } else alert("시스템 장애로 등록이 안되고 있습니다.");
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
function getParticipant(meetingId, participantUl){
	fetch(`/meeting/${meetingId}/participant`)
	.then(response => {
		if (response.ok) {
			return response;
		}
		else console.log(response)
	})
	.then(data => data.json())
	.then(participants => {
		console.log(getParticipant)
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