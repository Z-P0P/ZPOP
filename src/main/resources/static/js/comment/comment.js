import * as Reply from "./reply.js";
import {addListenerToCommentKebob, addListenerToModalBtn} from "./edit-comment.js";
import {addListenerToReplyModalBtn} from "./edit-reply.js";
window.addEventListener("load", () => {
	 
	const meetingId = document.querySelector(".meeting-id").dataset.id;
	const commentUl = document.querySelector(".comment__list");
	const replyCntList = document.querySelectorAll(".reply-cnt");
	
	
	
	//비로그인시 
	if(!document.querySelector("#header-notification")){
		const clickables = document.querySelectorAll(".modal__on-btn");
		for(const item of clickables)
			item.dataset.modal = "#modal-login"
		for(const span of replyCntList){
			 span.addEventListener("click", (e)=>{
				 showRestrictedReplyList(e);
			 });
		}
	}
	else{ //로그인시
	
		//댓글 케밥 셀렉트옵션들에 이벤트핸들러 등록
		addListenerToCommentKebob(meetingId,commentUl);
		//댓글 삭제모달 우측버튼에 이벤트핸들러 등록
		addListenerToModalBtn(meetingId,commentUl);
		//답글 삭제모달 우측버튼에 이벤트핸들러 등록
		addListenerToReplyModalBtn();
		//새 댓글 등록버튼에 이벤트핸들러 등록
		writeComment(meetingId,commentUl); 
		
		//SSR로 뿌려진 댓글리스트 전체에 부착할 핸들러
		commentUl.onclick = function(e) {
			
			//클릭시 답글 보기/쓰기 버튼이 아닌경우 리턴
			if (!e.target.classList.contains("reply-cnt")&&
				!e.target.classList.contains("reply-write")&&
				!e.target.classList.contains("reply-close"))
				return; 
			//답글 리스트 (default는 hidden)
			const commentId = e.target.closest("li").dataset.id;
			const replyUl = e.target.parentElement.nextElementSibling.children[0];
			const replySection = replyUl.parentElement;
			//각 댓글별로 답글 조회
			if(e.target.classList.contains("reply-cnt")){
				 revealReplyList(e.target, commentId, replyUl, replySection, meetingId);
			}
			//원댓글에 답글달기
			if(e.target.classList.contains("reply-write")){
				const parent = e.target.parentElement;
				parent.classList.add("hidden"); //답글링크 감춰 중복클릭 방지
				const groupId = commentId; //원댓글에 대한 답글은 groupId와 commentId가 동일
				Reply.writeReply(meetingId, groupId, commentId, replyUl, parent);
				replySection.classList.remove("hidden");//<section class="reply hidden">
			}
			//답글리스트 닫기버튼
			if(e.target.classList.contains("reply-close")){
				const replyCnt = e.target.previousElementSibling;
				closeReplyList(replyUl, replyCnt, e.target);
			}
		}
	}
});
//답글리스트보여주기함수
function revealReplyList(span, commentId, replyUl, replySection, meetingId){
	
	//AJAX로 답글 리스트 생성
	Reply.getReply(commentId, replyUl, true);
	
	//각 댓글 하위의 답글리스트에 '답글에 답글달기' 이벤트 핸들러 1회만 부착
	if(!replyUl.classList.contains("click-handler")){
		
		replyUl.addEventListener("click",(e)=>{
			if(e.target.classList.contains("reply-to-reply")){
				const parentId = e.target.closest("li").dataset.id;
				const parent = e.target.parentElement;
				parent.classList.add("hidden"); //답글링크 감춰 중복클릭 방지
				Reply.writeReply(meetingId, commentId, parentId, replyUl, parent);
			} //groupId = commentId
		});
		replyUl.classList.add("click-handler");
	}
	
	//AJAX로 완성된 답글 리스트 보여주기
	replySection.classList.remove("hidden");//<section class="reply hidden">
	
	//링크 교체: 답글보기 링크 -> 닫기
	const replyCnt = span; //<span class="reply-cnt">
	const replyClose = span.nextElementSibling//<span class="hidden pointer" id="reply-close">
	replyClose.classList.remove("hidden");
	replyCnt.classList.add("hidden")
	
	replyClose.addEventListener("click",()=>{ //'닫기'버튼 핸들러를 부착 
		closeReplyList(replyUl, replyCnt, replyClose);
	}); 
}
//비로그인상태에서 답글리스트 보여주기
function showRestrictedReplyList(e){
	const commentId = e.target.closest("li").dataset.id;
	const replyUl = e.target.parentElement.nextElementSibling.children[0];
	const replySection = replyUl.parentElement;// <section class="reply hidden">
	
	Reply.getReply(commentId, replyUl, false);
	replySection.classList.remove("hidden");
	const replyCnt = e.target; //<span class="reply-cnt">
	const replyClose = e.target.nextElementSibling//<span class="hidden pointer" id="reply-close">
	replyClose.classList.remove("hidden");
	replyCnt.classList.add("hidden")
	
	replyClose.addEventListener("click",()=>{ //'닫기'버튼 핸들러를 부착 
		closeReplyList(replyUl, replyCnt, replyClose);
	}); 
}
//닫기버튼에 부착된 이벤트핸들러
function closeReplyList(replyUl, replyCnt, replyClose){
		while(replyUl.hasChildNodes()) //답글 한개씩 삭제
			replyUl.removeChild(replyUl.firstChild);
		replyUl.parentElement.classList.add("hidden");//<section class="reply"> 감추기
		//닫기버튼 ->답글보기 링크
		replyClose.classList.add("hidden")
		replyCnt.classList.remove("hidden")
}

//AJAX로 댓글 렌더링
export function getComment(meetingId,commentUl) {
	fetch(`/comment?meetingId=${meetingId}`)
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
				//글 작성자를 위한 케밥메뉴
				let kebobModalWriter = `
					<div class="modal-select select-box__options comment__kebob z-idx-1">
			        <div class="modal-select__contents modal__on-btn" data-id="comment-edit" data-modal="#dummy-modal">수정
			            <span class="icon icon-edit"></span>
			        </div>
			        <div class="modal-select__contents modal__on-btn" data-id="comment-delete" data-modal="#modal-delete-comment">삭제
			            <span class="icon icon-trash"></span>
			        </div>
			    	</div>	
				`
				//일반가입자를 위한 케밥메뉴
				let kebobModalReader = `
					 <div class="modal-select select-box__options comment__kebob z-idx-1">
				        <div class="modal-select__contents modal__on-btn" data-id="comment-report" data-modal="#modal-report-comment">답글 신고
				            <span class="icon icon-siren-red"></span>
				        </div>
				     </div>
				`
				let template = `
					<li data-id="${c.id}">
						<div class="profile select-box">
							<span class="profile__image"></span> 
							<span class="profile__nickname">${c.nickname}</span> 
							<span class="profile__time">${c.elapsedTime}</span>
							<button></button>
							${c.myComment?kebobModalWriter:kebobModalReader}
						</div> 
						<span class="comment__content">${c.content}</span>
						<div class="comment__replies underline pointer"> 
							<span class="pointer underline reply-cnt">${countOfReply}</span>
							<span class="hidden pointer hidden reply-close">닫기</span>
							<span class="pointer underline reply-write modal__on-btn" data-modal="#dummy-modal">답글 달기</span>
						</div>
						<section class="reply hidden">
							<ul class="reply__list">
							</ul>
						</section>
					</li>
				`
				commentUl.insertAdjacentHTML("beforeend", template);
			}
			addListenerToCommentKebob(meetingId,commentUl);
			initModals();
			initSelectBoxes();
		});
}
//새 댓글 등록시 SSR로 렌더링된 기존 댓글을 지우고  AJAX로 전체를 다시 렌더링함. 
function writeComment(meetingId,commentUl){
	const registerBtn = document.querySelector("#register-btn");
	const inputBox = document.querySelector(".comment__input");
	registerBtn.addEventListener("click", () => {
		const commentText = inputBox.value;
		const data = {
			method: "POST",
			headers: {
				"Content-Type": "application/json",
			},
			body: JSON.stringify({
				"meetingId": meetingId,
				"content": commentText 
			})
		};
		if(commentText==""){
			 console.log("댓글을 입력해주세요.");
			 return;
		}
		
		fetch("/comment", data)
			.then(response => {
					if (response.ok) {
						inputBox.value = "";
						while(commentUl.hasChildNodes()) //기존 댓글 한개씩 삭제
							commentUl.removeChild(commentUl.firstChild);
						getComment(meetingId,commentUl); //AJAX로 새로 렌더링
					}
					else alert("시스템 장애로 등록이 안되고 있습니다.");
			});
	});
}
