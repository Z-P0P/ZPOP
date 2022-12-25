import * as Reply from "./reply.js";

const writerId = 4;
window.addEventListener("load", () => {
	//DOM에서 SSR로 뿌려진값들 추출 
	const commentUl = document.querySelector(".comment__list");
	const meetingId = document.querySelector("#meeting-id").innerText.trim();
	const registerBtn = document.querySelector("#register-btn");
	
	//새 댓글등록
	writeComment(registerBtn, meetingId, commentUl); //댓글 등록 버튼에 이벤트 핸들러 부착
	
	//SSR로 뿌려진 댓글리스트 전체에 이벤트 핸들러 부착
	commentUl.onclick = function(e) {
		//클릭시 답글 보기/쓰기 버튼이 아닌경우 리턴
		if (!e.target.classList.contains("reply-cnt")&&!e.target.classList.contains("reply-write")) 
			return; 
		//DOM에서 SSR로 뿌려진값들 추출 
		const commentId = e.target.parentElement.firstElementChild.innerText;//<span class="hidden comment-id">"
		const replyUl = e.target.parentElement.nextElementSibling.children[0];//<ul class="reply__list">
		const replySection = replyUl.parentElement;// <section class="reply hidden">
		
		//각 댓글별로 답글 조회
		if(e.target.classList.contains("reply-cnt")){
			//AJAX로 답글 리스트 생성
			Reply.getReply(meetingId, commentId, replyUl);
			//각 댓글 하위의 답글리스트에 '답글에 답글달기' 이벤트 핸들러 부착 
			replyUl.addEventListener("click",(e)=>{
				console.log(e.target)
				if(e.target.classList.contains("reply-to-reply")){
				const parentId = e.target.previousElementSibling.innerText.trim();
				const parent = e.target.parentElement;
				parent.classList.add("hidden"); //답글링크 감춰 중복클릭 방지
				Reply.writeReply(meetingId, writerId, commentId, parentId, replyUl, parent);
				} //groupId = commentId
			});
			//AJAX로 완성된 답글 리스트 보여주기
			replySection.classList.remove("hidden");//<section class="reply hidden">
			
			//링크 교체: 답글보기 링크 -> 닫기
			const replyCnt = e.target; //<span class="reply-cnt">
			const replyClose = e.target.nextElementSibling//<span class="hidden pointer" id="reply-close">
			replyClose.classList.remove("hidden");
			replyCnt.classList.add("hidden")
			
			closeReplyList(replyUl); //'닫기'버튼 핸들러를 부착 
		}
		//원댓글에 답글달기
		if(e.target.classList.contains("reply-write")){
			const parent = e.target.parentElement;
			parent.classList.add("hidden"); //답글링크 감춰 중복클릭 방지
			const parentId = parent.children[0].innerText.trim();
			const groupId = parentId; //원댓글에 대한 답글은 groupId와 parentId가 동일
			Reply.writeReply(meetingId, writerId, groupId, parentId, replyUl, parent);
			replySection.classList.remove("hidden");//<section class="reply hidden">
		}
	};
});
//닫기버튼 이벤트 핸들러
function closeReplyList(replyUl){
	const replyClose = replyUl.parentElement.previousElementSibling.children[2];
	const replyCnt = replyUl.parentElement.previousElementSibling.children[1];
	replyClose.addEventListener("click",()=>{
		while(replyUl.hasChildNodes()) //답글 한개씩 삭제
			replyUl.removeChild(replyUl.firstChild);
		
		replyUl.parentElement.classList.add("hidden");//<section class="reply"> 감추기
		//닫기버튼 ->답글보기 링크
		replyClose.classList.add("hidden")
		replyCnt.classList.remove("hidden")
	});			
}
//새 댓글 등록시 SSR로 렌더링된 기존 댓글을 지우고  AJAX로 전체를 다시 렌더링함. 
function writeComment(registerBtn, meetingId, commentUl){
	registerBtn.addEventListener("click", () => {
		const commentBox= document.querySelector("#comment-text");
		const commentText = commentBox.value;
		console.log(commentText)
		const data = {
			method: "POST",
			headers: {
				"Content-Type": "application/json",
			},
			body: JSON.stringify({
				"meetingId": meetingId,
				"writerId": writerId,
				"content": commentText
			})
		};
		if(commentText==""){
			 alert("댓글을 입력해주세요.");
			 return;
		}
		
		fetch("http://localhost:8080/comment", data)
			.then(response => {
					if (response.ok) {
						commentBox.value = "";
						while(commentUl.hasChildNodes()) //기존 댓글 한개씩 삭제
							commentUl.removeChild(commentUl.firstChild);
						getComment(meetingId, commentUl); //AJAX로 새로 렌더링
					}
					else alert("시스템 장애로 등록이 안되고 있습니다.");
			});
	});
}
//AJAX로 댓글 렌더링
function getComment(meetingId, commentUl) {
	const data = {
		method: "POST",
		headers: {
			"Content-Type": "application/json",
		},
		body: JSON.stringify({
			"meetingId": meetingId,
		})
	}
	fetch("http://localhost:8080/meeting/comment", data)
		.then(response => {
			if (response.ok) {
				return response;
			}
		})
		.then(data => data.json())
		.then(json => {
			let comments = json.resultObject;
			let count = json.countOfComment;
			const countHeader = document.querySelector(".comment__num");
			countHeader.innerHTML = "댓글 " + count +" 개";
			for (const c of comments) {
				let countOfReply = "";
				if(c.countOfReply != 0)
					countOfReply = "답글 " + c.countOfReply + "개";
				let template = `
					<li>
						<div class="profile">
							<span class="profile__image"></span> 
							<span class="profile__nickname">${c.nickname}</span> 
							<span class="profile__time">${c.elapsedTime}</span>
							<button></button>
						</div> <span class="comment__content">${c.content}</span>
						<div class="comment__replies underline pointer">
							<span class="hidden comment-id">${c.id}</span> 
							<span class="pointer underline reply-cnt">${countOfReply}</span>
							<span class="hidden pointer reply-close">닫기</span>
							<span class="pointer underline reply-write">답글 달기</span>
						</div>
						<section class="reply hidden">
							<ul class="reply__list">
							</ul>
						</section>
					</li>
				`
				commentUl.insertAdjacentHTML("beforeend", template);
			}
		});
}
//DOM추가로 새글 보여주기(사용x)
//function addNewCommentElement(newText) {

// 	if (newText != "") {
// 		const template = document.querySelector("#template-comment");
// 		const sourceNode = window.template.content.querySelector("li");
// 		const newNode = sourceNode.cloneNode(true);
// 		const textSpan = newNode.querySelector('.comment__content');
// 		const newContent = document.createTextNode(newText);
// 		textSpan.appendChild(newContent);
// 		const targetNode = document.querySelector(".comment__list li:last-child");
// 		if (targetNode == null)
// 			template.after(newNode);
// 		else
// 			targetNode.after(newNode);
// 		document.getElementById("comment-text").value = "";//플레이스홀더 되살림.
// 	}
// }