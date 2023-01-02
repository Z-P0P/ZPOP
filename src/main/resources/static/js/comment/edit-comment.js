import {getComment} from "./comment.js";
//수정,신고,삭제 기능 구현
window.addEventListener("load", () => {
	
	const meetingId = document.querySelector(".meeting-id").innerText.trim();
	const inputBox = document.querySelector(".comment__input");
	const commentUl = document.querySelector(".comment__list");
	const registerBtn = document.querySelector("#register-btn");
	const modalSelectEdit = document.querySelector('[data-id="comment-edit"]');
	const modalSelectDelete = document.querySelector('[data-id="comment-delete"]');
	const modalSelectReport = document.querySelector('[data-id="comment-report"]');
	const editSaveBtn = document.querySelector("#edit-save-btn");
	var commentId = 0;
	editSaveBtn.addEventListener("click",()=>{
		if(commentId > 0){	
			saveEdit(meetingId, commentId, commentUl,inputBox);
			registerBtn.classList.remove("hidden");
			editSaveBtn.classList.add("hidden");
		}
		else return;
	});	
	modalSelectEdit.onclick = function(e) {
		e.preventDefault();
		const text = e.target.parentElement.parentElement.nextElementSibling.innerText.trim();
		inputBox.focus();
		inputBox.value= "";
		inputBox.value = text+ " ";
		commentId = e.target.parentElement.previousElementSibling.previousElementSibling.innerText.trim();
		registerBtn.classList.add("hidden");
		editSaveBtn.classList.remove("hidden");
	}
	
	modalSelectDelete.onclick = function(e){
		e.preventDefault();
		commentId = e.target.parentElement.previousElementSibling.previousElementSibling.innerText.trim();
		deleteComment(meetingId, commentId, commentUl);
	}
	
	modalSelectReport.onclick = function(e){
		e.preventDefault();
		commentId = e.target.parentElement.previousElementSibling.previousElementSibling.innerText.trim();
		reportComment(commentId, 3, "그냥 맘에 안듬");
		//신고모달띄우기 추가
		//신고모달내에서 신고유형과 이유를 입력하도록함.
	}
	//신고모달내의 확인버튼에 이벤트 핸들러추가
	//확인버튼.addEventListener("click", function(e){
	//	reportComment(commentId, reportType, reportReason);	
	//});
});

function saveEdit(meetingId, commentId,commentUl,inputBox){
	const editedText = inputBox.value;
	const data = {
		method: "PATCH",
		headers: {
			"Content-Type": "application/json",
		},
		body: JSON.stringify({
			"id": commentId,
			"content": editedText 
		})
	};
	if(editedText==""){
		 alert("댓글을 입력해주세요.");
		 return;
	}
	
	fetch(`/comment/${commentId}`, data)
		.then(response => {
				if (response.ok) {
					inputBox.value = "";
					while(commentUl.hasChildNodes()) //기존 댓글 한개씩 삭제
						commentUl.removeChild(commentUl.firstChild);
					getComment(meetingId,commentUl)
				}
				else alert("시스템 장애로 등록이 안되고 있습니다.");
		});
}

function deleteComment(meetingId, commentId, commentUl){
	const data = {
		method: "DELETE",
		headers: {
			"Content-Type": "application/json",
		},
		body: JSON.stringify({
			"id": commentId,
		})
	};
	
	fetch(`/comment/${commentId}`, data)
		.then(response => {
				if (response.ok) {
					while(commentUl.hasChildNodes()) //기존 댓글 한개씩 삭제
						commentUl.removeChild(commentUl.firstChild);
					getComment(meetingId,commentUl)
				}
				else alert("시스템 장애로 등록이 안되고 있습니다.");
		});
}
function reportComment(commentId, reportType, reportReason){
	const data = {
		method: "PUT",
		headers: {
			"Content-Type": "application/json",
		},
		body: JSON.stringify({
			"commentId": commentId,
			"typeId":reportType,
			"reason":reportReason
		})
	};
	
	fetch(`/comment/${commentId}`, data)
		.then(response => {
			if (response.ok) {
				return response;
			}
			else alert("시스템 장애로 등록이 안되고 있습니다.");
		})
		.then(data=>data.json())
		.then(json=>{
			
		});
}