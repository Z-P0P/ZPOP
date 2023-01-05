window.addEventListener("load", function() {

	const participationBtn = document.querySelector("#participation-btn");
	const meetingId = document.querySelector(".meeting-id").dataset.id;
	const btnModalParticipate = document.querySelector(".btn-modal-right");
	const participantUl = document.querySelector(".participant__list");
	const participantCount = document.querySelector(".participant-count");
	const arrowUp = document.querySelector(".icon-arrow-up");
	const arrowDown = document.querySelector(".icon-arrow-down");

	if(participationBtn) {
		// 참여 확인 모달
		participationBtn.onclick = function(e) {
			e.preventDefault();
			const modal = document.querySelector(e.target.getAttribute("data-modal"));
			if (!modal.classList.contains("hidden")) {
				modal.classList.add("hidden");
				return;
			}
			modal.classList.remove("hidden");
		}
	}

	function hideModalByButton(e) {
		e.preventDefault();
		const modal = document.querySelector(e.target.getAttribute("data-modal"));
		modal.classList.add("hidden");
	}
	const modalCloseBtns = document.querySelectorAll(".modal__close-btn");
	for (closeBtn of modalCloseBtns) {
		closeBtn.onclick = hideModalByButton;
	}
	
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

	btnModalParticipate.onclick = function() {
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
          getParticipant();
        } else alert("시스템 장애로 등록이 안되고 있습니다.");
      })
      .catch((error) => {
        console.error("실패:", error);
      });
	}
	
	function getParticipant(){
		fetch(`/meeting/${meetingId}/participant`)
		.then(response => {
			if (response.ok) {
				return response;
			}
			
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

})