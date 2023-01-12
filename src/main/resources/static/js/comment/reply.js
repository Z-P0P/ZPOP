import {addListenerToReplyKebob} from "./edit-reply.js";
export function getReply(groupId, replyUl, isSignedOn) {
	//GET request가 디폴트
	fetch(`/reply?groupId=${groupId}`)
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
				//글 작성자를 위한 셀렉트메뉴
				let kebobModalForWriter = `
					<div class="modal-select select-box__options comment__kebob z-idx-1">
			        <div class="modal-select__contents modal__on-btn" data-id="comment-edit" data-modal="#dummy-modal">수정
			            <span class="icon icon-edit"></span>
			        </div>
			        <div class="modal-select__contents modal__on-btn" data-id="comment-delete" data-modal="#modal-delete-reply">삭제
			            <span class="icon icon-trash"></span>
			        </div>
			    	</div>	
				`
				//일반가입자를 위한 셀렉트메뉴
				let kebobModalForReader = `
					 <div class="modal-select select-box__options comment__kebob z-idx-1">
				        <div class="modal-select__contents modal__on-btn" data-id="comment-report" data-modal="#modal-report-comment">답글 신고
				            <span class="icon icon-siren-red"></span>
				        </div>
				     </div>
				`
				let template = `
					<li data-id="${r.id}"> 
						<div class="profile select-box">
							<span class="profile__image"></span>
							<span class="profile__nickname profile__nickname">${r.nickname}</span>
							<span class="profile__time">${r.elapsedTime}</span>
							<button></button>
							${r.myComment?kebobModalForWriter:kebobModalForReader}
						</div>
						<div class="reply-container">
							<span class="reply__to">${parentNickname}</span>
							<span class="reply__content">${r.content}</span>
						</div>
						<div class="reply__replies">
							<span class="pointer underline modal__on-btn reply-to-reply" data-modal="#dummy-modal">답글 달기</span> 
						</div>
					</li>
				`
				replyUl.insertAdjacentHTML("beforeend", template);
				addListenerToReplyKebob(r.id, replyUl.lastElementChild, replyUl, r.myComment)
			}   //로그인상태에서만 케밥메뉴 비활성화
				if(isSignedOn){
					initSelectBoxes();
				}//비로그인상태에서는 로그인모달띄움
				if(!isSignedOn){
					const clickables = document.querySelectorAll(".modal__on-btn");
					for(const item of clickables)
						item.dataset.modal = "#modal-login"
				}
				initModals();
		});
}

export function writeReply(meetingId, groupId, parentId, replyUl, linkContainer) {
		//기존에 열려있는 텍스트 박스들 닫기
		removeTextBox("multi");
		let template =  // text input box를 동적으로 추가 
			`
              <div class="reply__input-container"> 
                  <textarea
                      id="reply-text"
                      class="reply__input"
                      name="reply-input"
                      placeholder="답글을 입력하세요."></textarea>
                  <div class="reply__btn-container">
                      <span class="reply__btn btn btn-round btn-cancel cancel-btn" id="reply-cancel">취소하기</span>
                      <span class="reply__btn btn btn-round btn-action register-btn">등록하기</span>
                  </div>
              </div> 
                   `;
		//클릭된 특정 답글링크의 위치아래에 inputbox추가.    
		linkContainer.insertAdjacentHTML("afterend", template);
		const inputBox = document.querySelector("#reply-text");
		inputBox.focus();
		document.querySelector(".reply__btn.register-btn").addEventListener("click", () => {
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
					"meetingId": meetingId,
					"parentCommentId": parentId,
					"groupId": groupId
				})
			}

				fetch("/reply", data)
					.then(response => {
							if (response.ok) {
								if(groupId == parentId) {//원댓글에 대한 답글일 경우
									removeTextBox('single');
									linkContainer.children[0].classList.add("hidden"); //답글갯수 감추고
									linkContainer.children[1].classList.remove("hidden");//닫기 버튼 표출
								}
								linkContainer.classList.remove("hidden");
								while(replyUl.hasChildNodes()) //기존 댓글 한개씩 삭제
									replyUl.removeChild(replyUl.firstChild);
								getReply(groupId, replyUl, true); //AJAX로 새로 렌더링
							}
							else alert("시스템 장애로 등록이 안되고 있습니다.");
					});
		});//end of registerBtn event handler
		//취소버튼에 이벤트핸들러 부착 
		document.querySelector("#reply-cancel").addEventListener("click",()=>{
			document.querySelector(".reply__input-container").remove();
			linkContainer.classList.remove("hidden");
		});
}

//답글 갯수 갱신
function refreshReplyCount(replyUl, count){
	replyUl.parentElement.previousElementSibling.children[0].innerHTML = "답글 " + count + "개";
}

//text-area 제거
export function removeTextBox(count) {
	if (count == 'single'){
		const inputBox = document.querySelector(".reply__input-container");
			inputBox.previousElementSibling.classList.remove("hidden");	
			inputBox.remove();
		}
	
	else if (count == 'multi'){
		const inputBoxes = document.querySelectorAll(".reply__input-container");
		inputBoxes.forEach((item)=>{
			item.previousElementSibling.classList.remove("hidden");	
			item.remove();
		});
	}
}