
export function getReply(meetingId, groupId, replyUl) {
	const data = {
		method: "POST",
		headers: {
			"Content-Type": "application/json",
		},
		body: JSON.stringify({
			"meetingId": meetingId,
			"groupId": groupId
		})
	}
	fetch("/meeting/reply", data)
		.then(response => {
			if (response.ok) {
				return response;
			}
		})
		.then(data => data.json())
		.then(json => {
			let replies = json.resultObject;
			let count = json.countOfReply;
			refreshReplyCount(replyUl, count);
			for (const r of replies) {
				let parentNickname = "";
				if (r.parentNickname != null)
					parentNickname = '@' + r.parentNickname;
				let template = `
					<li> 
						<div class="profile">
							<span class="profile__image"></span>
							<span class="profile__nickname profile__nickname">${r.nickname}</span>
							<span class="profile__time">${r.elapsedTime}</span>
							<button></button>
						</div>
						<div class="reply-container">
							<span class="reply__to">${parentNickname}</span>
							<span class="reply__content">${r.content}</span>
						</div>
						<div class="reply__replies">
							<span class="hidden">${r.id}</span>
							<span class="pointer underline reply-to-reply" >답글 달기</span> 
							<span class="hidden">${r.groupId}</span>
						</div>
					</li>
				`
				replyUl.insertAdjacentHTML("beforeend", template);
			}
		});
}

export function writeReply(meetingId, writerId, groupId, parentId, replyUl, linkContainer) {
		
		let template =  // text input box를 동적으로 추가 
			`
              <div class="reply__input-container"> 
                  <textarea
                      id="reply-text"
                      class="reply__input"
                      name="reply-input"
                      placeholder="답글을 입력하세요."></textarea>
                  <div class="reply__btn-container">
                      <span class="reply__btn btn btn-round cancel-btn">취소하기</span>
                      <span class="reply__btn btn btn-round register-btn">등록하기</span>
                  </div>
              </div> 
                   `;
		//클릭된 특정 답글링크의 위치아래에 inputbox추가.    
		linkContainer.insertAdjacentHTML("afterend", template);

		document.querySelector(".register-btn").addEventListener("click", () => {
			
			const replyText = document.querySelector("#reply-text").value;
			
			if(replyText==""){
			 alert("답글을 입력해주세요.");
			 return;
			}
			const data = {
				method: "POST",
				headers: {
					"Content-Type": "application/json",
				},
				body: JSON.stringify({
					"content": replyText,
					"writerId": writerId,
					"meetingId": meetingId,
					"parentCommentId": parentId,
					"groupId": groupId
				})
			}

				fetch("/reply", data)
					.then(response => {
							if (response.ok) {
								linkContainer.classList.remove("hidden");
								while(replyUl.hasChildNodes()) //기존 댓글 한개씩 삭제
									replyUl.removeChild(replyUl.firstChild);
								getReply(meetingId, groupId, replyUl); //AJAX로 새로 렌더링
							}
							else alert("시스템 장애로 등록이 안되고 있습니다.");
					});
		});//end of registerBtn event handler
		//취소버튼에 이벤트핸들러 부착 
		document.querySelector(".cancel-btn").addEventListener("click",()=>{
			document.querySelector(".reply__input-container").remove();
			linkContainer.classList.remove("hidden");
		});
}

//답글 갯수 갱신
function refreshReplyCount(replyUl, count){
	replyUl.parentElement.previousElementSibling.children[1].innerHTML = "답글 " + count + "개";
}


//DOM추가기법으로 새 답글을 리스트 하단에 표시 (사용x)
//function addNewReplyElement(newText, parentNickname) {
//	if (newText != "") {
		//   const template = document.querySelector("#template-reply");
		//   const sourceNode = template.content.querySelector("li");
		//   const newNode = sourceNode.cloneNode(true);
		//   const textSpan = newNode.querySelector('.reply__content');
		//   const nicknameSpan = newNode.querySelector('.reply__to');
		//   const textNode = document.createTextNode(newText);
		//   const nicknameNode = document.createTextNode(parentNickname);
		//   textSpan.appendChild(textNode);
		//   nicknameSpan.appendChild(nicknameNode);
		//   const targetNode = document.querySelector(".reply__list").lastElementChild;
		//   targetNode.after(newNode);
		//   document.querySelector(".reply__input-container").remove();

//	}
//}