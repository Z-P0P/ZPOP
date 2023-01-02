import {getComment} from "./comment.js";
//수정,신고,삭제 기능 구현
window.addEventListener("load", () => {
   
   const meetingId = document.querySelector(".meeting-id").innerText.trim();
   const inputBox = document.querySelector(".comment__input");
   const commentUl = document.querySelector(".comment__list");
   const registerBtn = document.querySelector("#register-btn");
   const modalSelectEditList = document.querySelectorAll('[data-id="comment-edit"]');
   const modalSelectDeleteList = document.querySelectorAll('[data-id="comment-delete"]');
   const modalSelectReportList = document.querySelectorAll('[data-id="comment-report"]');
   const editSaveBtn = document.querySelector("#edit-save-btn");
   var commentId = 0;
   
   for(const m of modalSelectEditList)
      m.addEventListener("click",(e)=>{
         e.preventDefault();
         const text = e.target.parentElement.parentElement.nextElementSibling.innerText.trim();
         inputBox.focus();
         inputBox.value= "";
         inputBox.value = text+ " ";
         commentId = e.target.parentElement.previousElementSibling.previousElementSibling.innerText.trim();
         registerBtn.classList.add("hidden");
         editSaveBtn.classList.remove("hidden");
      });
   for(const m of modalSelectDeleteList)
      m.addEventListener("click",(e)=>{
         e.preventDefault();
         commentId = e.target.parentElement.previousElementSibling.previousElementSibling.innerText.trim();
         deleteComment(meetingId, commentId, commentUl);
      });
   for(const m of modalSelectReportList){
      m.addEventListener("click",(e)=>{
         e.preventDefault();
         commentId = e.target.parentElement.previousElementSibling.previousElementSibling.innerText.trim();
         reportComment(commentId, 3, "그냥 맘에 안듬");
      });
   }   
   
   editSaveBtn.addEventListener("click",()=>{
      if(commentId > 0){   
         saveEdit(meetingId, commentId, commentUl,inputBox);
         registerBtn.classList.remove("hidden");
         editSaveBtn.classList.add("hidden");
      }
      else return;
   });   

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