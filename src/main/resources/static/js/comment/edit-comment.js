import {getComment} from "./comment.js";
//댓글 수정,신고,삭제 기능 구현


export function addListenerToCommentKebob(meetingId,commentUl,inputBox,registerBtn,editSaveBtn){
   const modalSelectEditList = document.querySelectorAll('[data-id="comment-edit"]');
   const modalSelectDeleteList = document.querySelectorAll('[data-id="comment-delete"]');
   const modalSelectReportList = document.querySelectorAll('[data-id="comment-report"]');
   var commentId = 0; 
   
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
         const cancelBtn = document.querySelector(".cancel-btn");
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
         deleteComment(commentId,meetingId,commentUl,inputBox,registerBtn,editSaveBtn);
      });
   for(const m of modalSelectReportList){
      m.addEventListener("click",(e)=>{
         e.preventDefault();
         commentId = e.target.closest("li").dataset.id;
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
            }
            else alert("시스템 장애로 등록이 안되고 있습니다.");
      });
}

function deleteComment(commentId,meetingId,commentUl,inputBox,registerBtn,editSaveBtn){
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
               addListenerToCommentKebob(meetingId,commentUl,inputBox,registerBtn,editSaveBtn);
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
			 console.log("신고가 등록됨")
            return response;
         }
         else alert("시스템 장애로 등록이 안되고 있습니다.");
      })
      .then(data=>data.json())
      .then(json=>{
         
      });
}