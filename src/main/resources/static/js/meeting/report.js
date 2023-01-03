 switch (reportType){
 	case 1 :
 	 // 모임 신고
	  function addHTML(target){
			 	
				let template=`
				<span id="alert-statement" style="color:red;">(필수선택 항목입니다) </span>
				`;
				target.insertAdjacentHTML("afterend", template);
			}
	
	    window.addEventListener("load", (e) => {
	        const reportBtn = document.querySelector("#report-btn");
	        const selectBoxAll = document.querySelectorAll(".select-box");
	        const selectBoxInput = document.querySelector(".select-box > input");
	        var reportType = 0;
	     
	        reportBtn.addEventListener("click",(e) => {
	            e.preventDefault();
	            
			    switch(selectBoxInput.value){
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
	 		
	          const reportReason = document.querySelector("#input__content--id").value;
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
	                    reportReason
	                })
	            }
	
	            fetch("http://localhost:8080/report/meeting-test", data)
	            .then((response)=>{
					const reasonValue = document.querySelector("#input__content--id");
					const modal = document.querySelector('#modal-eval-notification');
					reasonValue.value='';
					selectBoxInput.value = null;
					reportType = 0;
					modal.classList.add("hidden");
				})
	        })
	    });
 		break;
 		
 		case 2:
 			 // 댓글 신고
		 function addHTML(target){
				 	
					let template=`
					<span id="alert-statement" style="color:red;">(필수선택 항목입니다) </span>
					`;
					target.insertAdjacentHTML("afterend", template);
				}
		
		    window.addEventListener("load", (e) => {
		        const reportBtn = document.querySelector("#report-btn");
		        const selectBoxAll = document.querySelectorAll(".select-box");
		        const selectBoxInput = document.querySelector(".select-box > input");
		        var reportType = 0;
		     
		        reportBtn.addEventListener("click",(e) => {
		            e.preventDefault();
		            
				    switch(selectBoxInput.value){
						 	 case null: reportType = 0;
						   	break; 
						     case "욕설/유해성 문구": reportType = 1;
						   	break; 
						   	 case "스팸/광고": reportType = 2;
						   	break; 
						   	 case "기타": reportType = 3;
						   	break; 
					   }
		 		
		          const reportReason = document.querySelector("#input__content--id").value;
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
		                    reportReason
		                })
		            }
		
		            fetch("http://localhost:8080/report/comment-test", data)
		            .then((response)=>{
						const reasonValue = document.querySelector("#input__content--id");
						const modal = document.querySelector('#modal-eval-notification');
						reasonValue.value='';
						selectBoxInput.value = null;
						reportType = 0;
						modal.classList.add("hidden");
					})
		        })
		    });
 		break;
 
 		case 3:
 		// 사용자 신고
		    
		 function addHTML(target){
				 	
					let template=`
					<span id="alert-statement" style="color:red;">(필수선택 항목입니다) </span>
					`;
					target.insertAdjacentHTML("afterend", template);
				}
		
		    window.addEventListener("load", (e) => {
		        const reportBtn = document.querySelector("#report-btn");
		        const selectBoxAll = document.querySelectorAll(".select-box");
		        const selectBoxInput = document.querySelector(".select-box > input");
		        var reportType = 0;
		     
		        reportBtn.addEventListener("click",(e) => {
		            e.preventDefault();
		            
				    switch(selectBoxInput.value){
						 	 case null: reportType = 0;
						   	break; 
						   	 case "부적절한 닉네임": reportType = 1;
						   	break; 
						     case "물건 판매 권유 등 상행위": reportType = 2;
						   	break; 
						   	 case "기타": reportType = 3;
						   	break; 
					   }
		 		
		          const reportReason = document.querySelector("#input__content--id").value;
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
		                    reportReason
		                })
		            }
		
		            fetch("http://localhost:8080/report/member-test", data)
		            .then((response)=>{
						const reasonValue = document.querySelector("#input__content--id");
						const modal = document.querySelector('#modal-eval-notification');
						reasonValue.value='';
						selectBoxInput.value = null;
						reportType = 0;
						modal.classList.add("hidden");
					})
		        })
		    });
 		break;
}