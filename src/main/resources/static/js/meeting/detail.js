window.addEventListener("load", function() {

	const participationBtn = document.querySelector("#participation-btn");
	const meetingId = document.querySelector(".meeting-id").innerText.trim();
	const btnModalParticipate = document.querySelector(".btn-modal-right");
	
	participationBtn.onclick = function(e) {
		e.preventDefault();
		const modal = document.querySelector(e.target.getAttribute("data-modal"));
		if (!modal.classList.contains("hidden")) {
			modal.classList.add("hidden");
			return;
		}
		modal.classList.remove("hidden");
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

	
	


	btnModalParticipate.onclick = function(e) {
		const data = {
			method: "GET", // 또는 'PUT'
			headers: {
				"Content-Type": "application/json",
			},
			body: JSON.stringify({
				"meetingId": meetingId,
				//"participantId": memberId //세션을 이용하므로 필요가 없다.
			})
		}

		fetch("/meeting/participate", data)
			.then((response) => {
				if (response.ok) {
					console.log("성공");
					document.querySelector("#modal-wrapper-participation").classList.add("hidden");
				}
				else alert("시스템 장애로 등록이 안되고 있습니다.");

			})
			.catch((error) => {
				console.error('실패:', error);
			});


	}

})