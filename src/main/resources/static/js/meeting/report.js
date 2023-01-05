function initCommentModal(){
	const commentSelectBoxSpan = document.querySelector(".comment-select-box > span");
	const reasonValue = document.querySelector("#comment-input__content--id");
	reasonValue.value='';
	commentSelectBoxSpan.innerHTML="카테고리를 선택하세요";
	reportType = 0;
}

function initMemberModal(){
	const memberSelectBoxSpan = document.querySelector(".member-select-box > span");
	const reasonValue = document.querySelector("#member-input__content--id");
	reasonValue.value='';
	memberSelectBoxSpan.innerHTML="카테고리를 선택하세요";
	reportType = 0;
}

function initMeetingModal(){
	const meetingSelectBoxSpan = document.querySelector(".meeting-select-box > span");
	const reasonValue = document.querySelector("#meeting-input__content--id");
	reasonValue.value='';
	meetingSelectBoxSpan.innerHTML="카테고리를 선택하세요";
	reportType = 0;
}

function addCompleteModal(){
	const reportCompleteModal = document.querySelector(".report-modal-wrapper");
	const reportCompleteBtn = document.querySelector(".report-complete__btn");
	reportCompleteModal.classList.remove("hidden");
	reportCompleteBtn.onclick = ()=> reportCompleteModal.classList.add("hidden");
}

function addHTML(target){
			 	
	let template=`
	<span id="alert-statement" style="color:red;">(필수선택 항목입니다) </span>
	`;
	target.insertAdjacentHTML("afterend", template);
}

window.addEventListener("load", (e)=>{
	
	const meetingReportBtn = document.querySelector(".meeting__report-btn");
	const commentReportBtn = document.querySelector(".comment__report-btn");//신고모달 우측 버튼
	const memberReportBtn = document.querySelector(".member__report-btn");
	const meetingSelectBoxInput = document.querySelector(".meeting-select-box > input");
	const commentSelectBoxInput = document.querySelector(".comment-select-box > input");
	const memberSelectBoxInput = document.querySelector(".member-select-box > input");
	const meetingSpan = document.querySelector(".meeting-span");
	const commentSpan = document.querySelector(".comment-span");
	const memberSpan = document.querySelector(".member-span");
	var reportType = 0;
	
	meetingSpan.onclick = function(e){
		e.preventDefault();
		e.target.classList.add("select-span");
	}
	commentSpan.onclick = function(e){
		e.preventDefault();
		e.target.classList.add("select-span");
	}
	memberSpan.onclick = function(e){
		e.preventDefault();
		e.target.classList.add("select-span");
	}
	
	// 모임 신고 
	
    meetingReportBtn.addEventListener("click",(e) => {
            e.preventDefault();
          
          const meetingReportReason = document.querySelector("#meeting-input__content--id").value;
          const alertStatement = document.querySelector("#alert-statement");
		  reportType = parseInt(meetingSelectBoxInput.value);
          
 		  if(alertStatement!=null)
			   alertStatement.remove();
		   
 		  if(reportType==0){
			   const reportReasonLabel = document.querySelector(".input__label");
			   addHTML(reportReasonLabel);
			   return;
		   }
 	
            const data = {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    reportType,
                    reportReason : meetingReportReason
                })
            }
			
			const id = document.querySelector(".meeting-id").dataset.id;
            fetch(`/report/meeting/${id}`, data)
            .then((response)=>{
				return response;
			})
			.then(data=>data.json())
			.then(result =>{
				if(result){
					initMeetingModal();
					addCompleteModal();
				}
				else{
					const completeContent = document.querySelector(".report-complete-content");
					completeContent.innerHTML = "중복 신고는 불가능합니다";
					initMeetingModal();
					addCompleteModal();
				}
			})
        })
        
     // 댓글 신고
        
     commentReportBtn.addEventListener("click",(e) => {
	          e.preventDefault();
			    
	          const commentReportReason = document.querySelector("#comment-input__content--id").value;
	          const alertStatement = document.querySelector("#alert-statement");
			  reportType = parseInt(commentSelectBoxInput.value);  
	 		
	 		  if(alertStatement!=null)
				   alertStatement.remove();
			   
	 		  if(reportType==0){
				   const reportReasonLabel = document.querySelector(".input__label");
				   addHTML(reportReasonLabel);
				   return;
			   }
	 	
	            const data = {
	                method: "POST",
	                headers: {
	                    "Content-Type": "application/json",
	                },
	                body: JSON.stringify({
	                    typeId : reportType,
	                    reason : commentReportReason
	                })
	            }
				
				const commentId = document.querySelector("#modal-report-comment").dataset.id;
	            fetch(`/report/comment/${commentId}`, data)
	            .then((response)=>{
					return response;
				})
				.then(data=>data.json())
				.then(result =>{
					if(result){
						initCommentModal();
						addCompleteModal();
					}
					else{
						const completeContent = document.querySelector(".report-complete-content");
						completeContent.innerHTML = "중복 신고는 불가능합니다";
						initCommentModal();
						addCompleteModal();
					}
				})
	        })
	        
	   // 사용자 신고
	        
       memberReportBtn.addEventListener("click",(e) => {
	            e.preventDefault();
	            
	          const memberReportReason = document.querySelector("#member-input__content--id").value;
	          const alertStatement = document.querySelector("#alert-statement");
			  reportType = parseInt(memberSelectBoxInput.value);
	          
	 		  if(alertStatement!=null)
				   alertStatement.remove();
			   
	 		  
	 		  if(reportType==0){
				   const reportReasonLabel = document.querySelector(".input__label");
				   addHTML(reportReasonLabel);
				   return;
			   }
	 	
	            const data = {
	                method: "POST",
	                headers: {
	                    "Content-Type": "application/json",
	                },
	                body: JSON.stringify({
	                    reportType,
	                    reportReason : memberReportReason
	                })
	            }
				// ============== 테스트용 id =====================
				const memberId = 5;
	            fetch(`/report/member/${memberId}`, data)
	             .then((response)=>{
					return response;
				})
				.then(data=>data.json())
				.then(result =>{
					if(result){
						initMemberModal();
						addCompleteModal();
					}
					else{
						const completeContent = document.querySelector(".report-complete-content");
						completeContent.innerHTML = "중복 신고는 불가능합니다";
						initMemberModal();
						addCompleteModal();
					}
				})
	        })
})