window.addEventListener("load", function() {

	const participationBtn = document.querySelector("#participation-btn");
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

	const btnModalParticipate = document.querySelector(".btn-modal-right");
	const meetingId = document.querySelector(".meeting-Id").innerText.trim();
	const memberId = document.querySelector(".member-Id").innerText.trim();



	btnModalParticipate.onclick = function(e) {




		const data = {
			method: "POST", // 또는 'PUT'
			headers: {
				"Content-Type": "application/json",
			},
			body: JSON.stringify({
				"meetingId": meetingId,
				"participantId": memberId
			})
		}

		fetch("http://localhost:8080/meeting/participate", data)
			.then((response) => {
				if (response.ok) {
					console.log("성공");
					document.querySelector("#modal-wrapper-participation").classList.add("hidden");
				}

			})
			.catch((error) => {
				console.error('실패:', error);
				alert("참여에 실패했습니다");
			});


	}

})