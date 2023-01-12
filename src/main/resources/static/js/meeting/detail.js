window.addEventListener("load", function() {
	
	//비로그인시 버튼 비활성화
		const btnParticipation = document.querySelector("#btn-participation");
		const btnCancelParticipation = document.querySelector("#btn-cancel-participation");
		const btnCloseMeeting = document.querySelector("#btn-close");
	if(!document.querySelector("#header-notification")){
		if(btnParticipation)
			btnParticipation.dataset.modal = "#modal-login";
		if(btnCancelParticipation)
			btnCancelParticipation.dataset.modal = "#modal-login";
		if(btnCloseMeeting)
			btnCloseMeeting.dataset.modal =  "#modal-login";
	}
	
	const meetingId = document.querySelector(".meeting-id").dataset.id;
	const btnModalParticipate = document.querySelector("#participate-confirm");//참여하기 모달 우측 버튼
	const btnModalCancelParticipation = document.querySelector("#cancel-confirm");//취소 모달 우측 버튼
	const btnModalMeetingClose = document.querySelector("#close-confirm");//마감 모달 우측 버튼
	const participantUl = document.querySelector(".participant__list");
	const participantCount = document.querySelector("#participant-count");
	const arrowUp = document.querySelector(".icon-arrow-up");
	const arrowDown = document.querySelector(".icon-arrow-down");
	
	btnModalParticipate.addEventListener("click", ()=>{
		participate(meetingId, participantUl, participantCount);
		const modalParticipationCloseBtn = document.querySelectorAll("[data-modal='#modal-wrapper-participation']");
		modalParticipationCloseBtn.forEach(btn=> btn.addEventListener('click', ()=>resetModal()));
	});

	btnModalCancelParticipation.addEventListener("click", () => {
		cancelParticipate(meetingId, participantUl, participantCount);
	});

	btnModalMeetingClose.addEventListener("click", () => {
		closeMeeting(meetingId, btnCloseMeeting);
	});


	arrowUp.addEventListener("click", (e) => {
		participantUl.classList.add("hidden");
		e.target.classList.add("hidden");
		e.target.nextElementSibling.classList.remove("hidden");
	});
	arrowDown.addEventListener("click", (e) => {
		participantUl.classList.remove("hidden");
		e.target.classList.add("hidden");
		e.target.previousElementSibling.classList.remove("hidden");
	});
});

function resetModal(){
	const participationCheckModal = document.querySelector(".modal-participation-content-container:last-child");
	const statusMessage = participationCheckModal.querySelector('.participation-status__message');
	const modalBody = document.querySelector(".modal-participation .modal__body");
	const statusIcon = participationCheckModal.querySelector('.participation-status__icon > div');
	const inputMessage = participationCheckModal.querySelector('.input__message');
	const closeModalBtn = participationCheckModal.querySelector('.btn.btn-semiround');
	modalBody.classList.remove('modal__body--content1');
	statusIcon.classList.remove(...['icon-cancel', 'icon-check']);
	statusIcon.classList.add('lds-roller');
	closeModalBtn.classList.add('hidden');
	inputMessage.classList.add('hidden');
	statusMessage.innerText = "";
	inputMessage.innerText = "";
}
//참여
function participate(meetingId, participantUl, participantCount) {
	const modalBody = document.querySelector(".modal-participation .modal__body");
	const participationCheckModal = document.querySelector(".modal-participation-content-container:last-child");
	modalBody.classList.add('modal__body--content1');
	const statusIcon = participationCheckModal.querySelector('.participation-status__icon > div');
	const statusMessage = participationCheckModal.querySelector('.participation-status__message');
	const inputMessage = participationCheckModal.querySelector('.input__message');
	const messageTemplate = `참가자에 등록하고 있어요!`
	const closeModalBtn = participationCheckModal.querySelector('.btn.btn-semiround');
	statusMessage.insertAdjacentHTML('beforeend', messageTemplate);
	const input = participationCheckModal.querySelector('input');
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
				return response.json();
			}
			else{
				throw new Error('모임 참여에 실패했어요!');
			} 
		})
		.then(data=>{
			
			if (!data.success){
				throw new Error(data.reason)
			}
			
			statusIcon.classList.remove('lds-roller');
			statusIcon.classList.add('icon-done');
			statusMessage.innerText = '모임 참여에 성공했어요!';
			inputMessage.innerText = '클릭 시 새 창으로 이동합니다!💨';
			inputMessage.href = getClickableLink(data.contact);
			inputMessage.target = '_blank';
			inputMessage.classList.remove('hidden');
			input.classList.remove('hidden');
			input.value = data.contact;
			while (participantUl.hasChildNodes())//기존 참여자아이콘 한개씩 삭제
				participantUl.removeChild(participantUl.firstChild);
			getParticipant(meetingId, participantUl, participantCount);//참여자 목록 refresh

			const btnParticipate = document.querySelector("#btn-participation");//참여하기 버튼
			btnParticipate.innerText = "참여취소";
			btnParticipate.id = "btn-cancel-participation";
			btnParticipate.dataset.modal = "#modal-wrapper-cancel";
		})
		.catch((error) => {
			statusIcon.classList.remove('lds-roller');
			statusIcon.classList.add('icon-cancel');
			statusMessage.innerText = error.message;
		})
		.finally(()=>{
			closeModalBtn.classList.remove('hidden');
		});
}
//참여취소
function cancelParticipate(meetingId, participantUl, participantCount) {
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
		.then(data => data.json())
		.then(result => {
			if (result) {
				console.log("참여취소완료")
				while (participantUl.hasChildNodes())//기존 참여자아이콘 한개씩 삭제
					participantUl.removeChild(participantUl.firstChild);
				getParticipant(meetingId, participantUl, participantCount);//참여자 목록 refresh

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
//모임 조기마감
function closeMeeting(meetingId, btnMeetingClose) {

	const data = {
		method: "PATCH",
		headers: {
			"Content-Type": "application/json",
		},
		body: JSON.stringify({
			meetingId: meetingId,
		}),
	};
	fetch(`/meeting/${meetingId}/close`, data)
		.then(response => {
			if (response.ok) {
				return response;
			}
			else alert("시스템 장애로 등록이 안되고 있습니다.");
		})
		.then(data => data.json())
		.then(result => {
			if (result) {
				console.log("미팅이 마감됨")
				btnMeetingClose.remove();
				const template = `<span class="btn btn-round btn-join btn-dead">모집완료</span>`;
				document.querySelector(".meeting__btn").insertAdjacentHTML("afterbegin", template);

			}
		});
}
//참여자 목록 받기
function getParticipant(meetingId, participantUl, participantCount) {

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
		                <img src="/images/icon/user-profile-grey.svg">  
		                <span>${p.nickname}</span>
		            </div>
	            </li>
			`
				participantUl.insertAdjacentHTML("beforeend", template);
			}
		});
}
//모임 삭제
function deleteMeeting(meetingId){
	 
   // 비동기처리하지 않고 페이지전환으로 controller에 요청하여 처리.
}

function getClickableLink (link) {
    return link.startsWith("http://") || link.startsWith("https://") ?
      link: `http://${link}`;
}