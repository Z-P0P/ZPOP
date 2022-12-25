function remove(elem) {
	elem.parentNode.removeChild(elem);
}

function addNewCommentElement(newText) {

	if (newText != "") {
		const template = document.querySelector("#template");
		const sourceNode = window.template.content.querySelector("li");
		const newNode = sourceNode.cloneNode(true);
		const textSpan = newNode.querySelector('.comment__content');
		const newContent = document.createTextNode(newText);
		textSpan.appendChild(newContent);
		const targetNode = document.querySelector(".comment__list li:last-child");
		if (targetNode == null)
			template.after(newNode);
		else
			targetNode.after(newNode);
		document.getElementById("comment-text").value = "";//플레이스홀더 되살림.
	}
}

window.addEventListener("load", (e) => {
	const commentList = document.querySelector(".comment__list");
	const meetingId = document.querySelector("#meeting-id").innerText.trim();
	const registerBtn = document.querySelector("#register-btn");

	commentList.onclick = function(e) {
		e.preventDefault();
		if (!e.target.classList.contains("reply-cnt"))
			return;
		const parentId = e.target.previousElementSibling.innerText;//<span class="hidden comment-id">"
		const replyUl = e.target.parentElement.nextElementSibling.children[0];//<ul class="reply__list">
		const replyCnt = e.target; //<span class="reply-cnt">
		const replyClose = e.target.nextElementSibling//<span class="hidden pointer" id="reply-close">
		const replySection = replyUl.parentElement;
		
		const data = {
			method: "POST",
			headers: {
				"Content-Type": "application/json",
			},
			body: JSON.stringify({
				"meetingId": meetingId,
				"parentCommentId": parentId
			})
		}
		fetch("http://localhost:8080/meeting/reply", data)
			.then(response => {
				if (response.ok) {

					return response;
				}
			})
			.then(data => data.json())
			.then(json => {
				let replies = json.resultObject;
				let parentNickname = "";
				for (r of replies) {
					if (r.parentNickname != null)
						parentNickname = '@' + r.parentNickname;
					let template = `
						<li> 
							<div class="profile">
								<span class="profile__image"></span>
								<span class="profile__nickname profile__nickname">${r.nickname}</span>
								<span class="profile__time">3분전</span>
								<button></button>
							</div>
							<div class="reply-container">
								<span class="reply__to">${parentNickname}</span>
								<span class="reply__content">${r.content}</span>
							</div>
							<div class="reply__replies">
								<span class="hidden"></span>
								<span>답글 달기</span> <span class="hidden"></span>
							</div>
						</li>
					`
					replyUl.insertAdjacentHTML("beforeend", template);
				}
			});
		//완성된 답글 리스트 보여주기
		replySection.classList.remove("hidden");//<section class="reply hidden">
		//버튼 교체: 답글 갯수 -> 닫기
		e.target.parentNode.replaceChild(replyClose, replyCnt);
		replyClose.classList.remove("hidden");
		
		
        };

	registerBtn.addEventListener("click", (e) => {
		e.preventDefault();

		const commentText = document.querySelector("#comment-text").value;
		console.log(meetingId)
		const data = {
			method: "POST",
			headers: {
				"Content-Type": "application/json",
			},
			body: JSON.stringify({
				"meetingId": meetingId,
				"content": commentText,
				"writerId": 3,
			})
		}

		fetch("http://localhost:8080/meeting/comment", data)
			.then(response => {
				const newCommentText = document.getElementById("comment-text").value;
				if (newCommentText) {
					if (response.ok) {
						addNewCommentElement(newCommentText);
					}
					else alert("시스템 장애로 등록이 안되고 있습니다.");
				}
				else alert("댓글을 입력해주세요.");
			});

	});
});