import {getComment} from "./comment.js";
//댓글 수정,신고,삭제 기능 구현


export function addListenerToCommentKebob(meetingId,commentUl){
   const modalSelectEditList = document.querySelectorAll('[data-id="comment-edit"]');
   const modalSelectDeleteList = document.querySelectorAll('[data-id="comment-delete"]');
   const modalSelectReportList = document.querySelectorAll('[data-id="comment-report"]');
   const inputBox = document.querySelector(".comment__input");
   const registerBtn = document.querySelector("#register-btn");
   const editSaveBtn = document.querySelector("#edit-save-btn");
   const cancelBtn = document.querySelector(".cancel-btn");
   var commentId = 0; 
 //셀렉트 옵션들에 이벤트핸들러 등록  
   for(const m of modalSelectEditList)
      m.addEventListener("click",(e)=>{
         e.preventDefault();
         const text = e.target.closest("li").querySelector(".comment__content").innerText.trim();
         inputBox.focus();
         inputBox.value= "";
         inputBox.value = text+ " ";
         commentId = e.target.closest("li").dataset.id;
         registerBtn.classList.add("hidden");
         editSaveBtn.classList.remove("hidden");
         cancelBtn.classList.remove("hidden");
         cancelBtn.addEventListener("click",()=>{
	         inputBox.value="";
	         inputBox.blur();
	         registerBtn.classList.remove("hidden");
             editSaveBtn.classList.add("hidden");
             cancelBtn.classList.add("hidden");
      	});
      });
   for(const m of modalSelectDeleteList)
      m.addEventListener("click",(e)=>{
         e.preventDefault();
         commentId = e.target.closest("li").dataset.id;
         const modalBtn = document.querySelector("#delete-confirm");
         modalBtn.setAttribute("data-id",commentId);
      });
   for(const m of modalSelectReportList){
      m.addEventListener("click",(e)=>{
         e.preventDefault();
         commentId = e.target.closest("li").dataset.id;
         //view의 댓글번호를 모달로 전달함. 이후는 report.js에서 처리(임우빈)
         const modal = document.querySelector("#modal-report-comment");
         modal.setAttribute("data-id",commentId);
      });
   }   
   
   editSaveBtn.addEventListener("click",()=>{
      if(commentId > 0){   
         saveEdit(meetingId, commentId, commentUl,inputBox);
         registerBtn.classList.remove("hidden");
         editSaveBtn.classList.add("hidden");
         cancelBtn.classList.add("hidden");
         
      }
      else return;
   }); 
   
}

//모달우측버튼 이벤트핸들러
export function addListenerToModalBtn (meetingId,commentUl){
	const btnModalCommentDelete = document.querySelector("#delete-confirm");
    btnModalCommentDelete.addEventListener("click", (e)=>{
	   const commentId = e.target.dataset.id;
	   deleteComment(commentId, meetingId,commentUl);
   });  
}
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
            console.log("댓글이 수정됨")   
               inputBox.value = "";
               while(commentUl.hasChildNodes()) //기존 댓글 한개씩 삭제
                  commentUl.removeChild(commentUl.firstChild);
               getComment(meetingId,commentUl)
             //  addListenerToCommentKebob(meetingId,commentUl);
			initModals();
				initSelectBoxes();
            }
            else alert("시스템 장애로 등록이 안되고 있습니다.");
      });
}

function deleteComment(commentId, meetingId,commentUl){
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
            console.log("댓글이 삭제됨")
               while(commentUl.hasChildNodes()) //기존 댓글 한개씩 삭제
                  commentUl.removeChild(commentUl.firstChild);
               getComment(meetingId,commentUl)
            }
            else alert("시스템 장애로 등록이 안되고 있습니다.");
      });
}

