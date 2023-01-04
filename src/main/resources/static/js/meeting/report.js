function addHTML(target){
			 	
	let template=`
	<span id="alert-statement" style="color:red;">(필수선택 항목입니다) </span>
	`;
	target.insertAdjacentHTML("afterend", template);
}

window.addEventListener("load", (e)=>{
	
	const meetingReportBtn = document.querySelector(".meeting__report-btn");
	const commentReportBtn = document.querySelector(".comment__report-btn");
	const memberReportBtn = document.querySelector(".member__report-btn");
	const selectBoxAll = document.querySelectorAll(".select-box");
	const meetingSelectBoxInput = document.querySelector(".meeting-select-box > input");
	const commentSelectBoxInput = document.querySelector(".comment-select-box > input");
	const memberSelectBoxInput = document.querySelector(".member-select-box > input");
	var reportType = 0;
	
	// 모임 신고 
	
    meetingReportBtn.addEventListener("click",(e) => {
            e.preventDefault();
            
		    switch(meetingSelectBoxInput.value){
				 	 case null: reportType = 0;
				   	break; 
				     case "사행성 글": reportType = 1;
				   	break; 
				   	 case "부적절한 게시글 제목/내용": reportType = 2;
				   	break; 
				   	 case "판매 권유 및 상행위 글": reportType = 3;
				   	break; 
				   	 case "기타": reportType = 4;
				   	break; 
			   }
 		
          const meetingReportReason = document.querySelector("#meeting-input__content--id").value;
          const alertStatement = document.querySelector("#alert-statement");
          
 		  if(alertStatement!=null){
			   alertStatement.remove();
		   }
 		  
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
			
			const id = 5;
//			const id = document.querySelector(".meeting-id").dataset.id;
//			console.log(id);
            fetch(`http://localhost:8080/report/${id}`, data)
            .then((response)=>{
				const reasonValue = document.querySelector("#meeting-input__content--id");
				const modal = document.querySelector('#modal-report-meeting');
				reasonValue.value='';
				meetingSelectBoxInput.value = null;
				reportType = 0;
				modal.classList.add("hidden");
			})
        })
        
     // 댓글 신고
        
     commentReportBtn.addEventListener("click",(e) => {
	            e.preventDefault();
			    switch(commentSelectBoxInput.value){
					 	 case null: reportType = 0;
					   	break; 
					     case "욕설/유해성 문구": reportType = 1;
					   	break; 
					   	 case "스팸/광고": reportType = 2;
					   	break; 
					   	 case "기타": reportType = 3;
					   	break; 
				   }
	 		
	          const commentReportReason = document.querySelector("#comment-input__content--id").value;
	          const alertStatement = document.querySelector("#alert-statement");
	          
	 		  if(alertStatement!=null){
				   alertStatement.remove();
			   }
	 		  
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
	                    reportReason : commentReportReason
	                })
	            }
	
	            fetch("http://localhost:8080/report/comment-test", data)
	            .then((response)=>{
					const reasonValue = document.querySelector("#comment-input__content--id");
					const modal = document.querySelector('#modal-report-comment');
					reasonValue.value='';
					commentSelectBoxInput.value = null;
					reportType = 0;
					modal.classList.add("hidden");
				})
	        })
	        
	     // 사용자 신고
	        
//         memberReportBtn.addEventListener("click",(e) => {
//	            e.preventDefault();
//	            
//			    switch(memberSelectBoxInput.value){
//					 	 case null: reportType = 0;
//					   	break; 
//					   	 case "부적절한 닉네임": reportType = 1;
//					   	break; 
//					     case "물건 판매 권유 등 상행위": reportType = 2;
//					   	break; 
//					   	 case "기타": reportType = 3;
//					   	break; 
//				   }
//	 		
//	          const memberReportReason = document.querySelector("#member-input__content--id").value;
//	          const alertStatement = document.querySelector("#alert-statement");
//	          
//	 		  if(alertStatement!=null){
//				   alertStatement.remove();
//			   }
//	 		  
//	 		  if(reportType==0){
//				   const reportReasonLabel = document.querySelector(".input__label");
//				   addHTML(reportReasonLabel);
//				   return;
//			   }
//	 	
//	            const data = {
//	                method: "POST",
//	                headers: {
//	                    "Content-Type": "application/json",
//	                },
//	                body: JSON.stringify({
//	                    reportType,
//	                    reportReason : memberReportReason
//	                })
//	            }
//	
//	            fetch("http://localhost:8080/report/member-test", data)
//	            .then((response)=>{
//					const reasonValue = document.querySelector("#member-input__content--id");
//					const modal = document.querySelector('#modal-eval-notification');
//					reasonValue.value='';
//					memberSelectBoxInput.value = null;
//					reportType = 0;
//					modal.classList.add("hidden");
//				})
//	        })
})