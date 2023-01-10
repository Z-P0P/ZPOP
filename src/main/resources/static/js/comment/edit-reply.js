import {getReply, removeTextBox} from "./reply.js";

export function addListenerToReplyKebob(replyId, replyLi, replyUl, isMyComment){
    //자신이 쓴 답글일 경우
    if(isMyComment){
	    const editOption = replyLi.querySelector("[data-id='comment-edit']");
	    const deleteOption = replyLi.querySelector("[data-id='comment-delete']");
	    const profile = replyLi.querySelector(".profile");
	    editOption.addEventListener("click", ()=>{
		    editReply(replyId, replyUl, profile);
		});
		deleteOption.addEventListener("click", ()=>{
			const modalBtn = document.querySelector("#delete-reply-confirm");
        	modalBtn.setAttribute("data-id",replyId);
		});
	} else {
		const reportOption = replyLi.querySelector("[data-id='comment-report']");	   
	    reportOption.addEventListener("click",()=>{
         //view의 댓글번호를 모달로 전달함. 이후는 report.js에서 처리(임우빈)
         const modal = document.querySelector("#modal-report-comment");
         modal.setAttribute("data-id",replyId);
      	});
   	}
}

//삭제모달창 우측버튼
export function addListenerToReplyModalBtn (){
	const btnModalReplyDelete = document.querySelector("#delete-reply-confirm");
    btnModalReplyDelete.addEventListener("click",(e)=>{
		const replyId = e.target.dataset.id;
		const replyUl = document.querySelector(`li[data-id='${replyId}']`).parentElement;
	    deleteReply(replyId, replyUl);
    });
}
function editReply(replyId, replyUl, profile){
	removeTextBox("multi");
	const replyContainer = profile.nextElementSibling;
	const linkContainer = replyContainer.nextElementSibling;
	linkContainer.classList.add("hidden");
	replyContainer.classList.add("hidden");
	let template =  // text input box를 동적으로 추가 
			`
              <div class="reply__input-container"> 
                  <textarea
                      id="reply-text"
                      class="reply__input"
                      name="reply-input"></textarea>
                  <div class="reply__btn-container">
                      <span class="reply__btn btn btn-round btn-cancel cancel-btn" id="edit-reply-cancel">취소하기</span>
                      <span class="reply__btn btn btn-round save-reply-edit btn-action"  >저장하기</span>
                  </div>
              </div> 
                   `;
		//클릭된 특정 답글링크의 위치아래에 inputbox추가.    
		linkContainer.insertAdjacentHTML("afterend", template);
		const inputBox = document.querySelector("#reply-text");
		const text = replyContainer.children[1].innerText.trim();
		inputBox.focus();
		inputBox.value="";
		inputBox.value = text+ " ";
		//저장버튼
		document.querySelector(".save-reply-edit").addEventListener("click",()=>{
			saveEdit(replyId,replyUl,inputBox);
		});
		//취소버튼
        document.querySelector("#edit-reply-cancel").addEventListener("click",()=>{
			document.querySelector(".reply__input-container").remove();
			linkContainer.classList.remove("hidden");
			replyContainer.classList.remove("hidden");
		});
}

function saveEdit(replyId,replyUl,inputBox){
   const editedText = inputBox.value;
   const data = {
      method: "PATCH",
      headers: {
         "Content-Type": "application/json",
      },
      body: JSON.stringify({
         "id": replyId,
         "content": editedText 
      })
   };
   if(editedText==""){
       alert("답글을 입력해주세요.");
       return;
   }
   
   fetch(`/reply/${replyId}`, data)
      .then(response => {
            if (response.ok) {
				console.log("답글이 수정됨")
				return response;
            }
            else alert("시스템 장애로 등록이 안되고 있습니다.");
      })
      .then(data => data.json())
	  .then(json => {
		  while(replyUl.hasChildNodes()) //기존 댓글 한개씩 삭제
              replyUl.removeChild(replyUl.firstChild);
          getReply(json,replyUl) //json값은 백단에서 받아오는 groupId
	  });
}

function deleteReply(replyId, replyUl){
   const data = {
      method: "DELETE",
      headers: {
         "Content-Type": "application/json",
      },
      body: JSON.stringify({
         "id": replyId,
      })
   };
   
   fetch(`/reply/${replyId}`, data)
      .then(response => {
            if (response.ok) {
				console.log("답글이 삭제됨")
				return response;
            }
            else alert("시스템 장애로 등록이 안되고 있습니다.");
      })
      .then(data => data.json())
	  .then(json => {
		  while(replyUl.hasChildNodes()) //기존 댓글 한개씩 삭제
              replyUl.removeChild(replyUl.firstChild);
          getReply(json,replyUl) //json값은 백단에서 받아오는 groupId
	  });
}
