 window.addEventListener("load", (e) => {
        document.querySelectorAll(".reply__replies").forEach((addReply)=>{
	        addReply.addEventListener("click",(e) => {
	            e.preventDefault();
	            const parent = e.target.parentElement; //답글링크 컨테이너 객체
            	restoreAddLink();
	            parent.classList.add("hidden"); //답글링크 감춰 중복클릭 방지
	            document.querySelectorAll(".reply__input-container").forEach((item)=>item.remove());//답글입력창 삭제
	        	let template =  // text input box를 동적으로 추가 
	        		`
					<div class="reply__input-container"> 
				        <textarea
				        id="reply-text"
				        class="reply__input"
				        name="reply-input"
				        placeholder="답글을 입력하세요."
				        ></textarea>
				        <span id="register-btn" class="reply__btn btn btn-round">등록하기</span>
		    		 </div> 
		    		 `;
	        	const parentId = e.target.previousElementSibling.innerHTML;
	        	const groupId = e.target.nextElementSibling.innerHTML;
	        	const parentNickname = parent.previousElementSibling.previousElementSibling.children[1].innerHTML;
	        	
	        	parent.insertAdjacentHTML("afterend",template);//클릭된 특정 답글링크의 위치아래에 inputbox추가.
	        	const registerBtn = document.querySelector("#register-btn");
	        	
	        	registerBtn.addEventListener("click",(e) => {
	                e.preventDefault();
					restoreAddLink();
	                const replyText = document.querySelector("#reply-text").value;
	
	                const data = {
	                    method: "POST",
	                    headers: {
	                        "Content-Type": "application/json",
	                    },
	                    body: JSON.stringify({
	                        "content":replyText,
	                        "writerId":2,
	                        "meetingId":1,
	                        "perentCommentId":parentId,
	                        "groupId":groupId
	                    })
	                }
	
	                fetch("http://localhost:8080/reply", data)
	                .then(response => {
	    		        const newReplyText = document.getElementById("reply-text").value;
	                	if(newReplyText){
	    	            	if(response.ok){
	    		            	addNewReplyElement(newReplyText, parentNickname);
	    	            	}
	    	            	else alert("시스템 장애로 등록이 안되고 있습니다.");
	    	            }
	    	            else alert("댓글을 입력해주세요.");
	                });
	            });//end of registerBtn event handler
	  		}); //end of addReply.addEventListener
	  	}); //end of foreach of addReply
  	});//end of window.load
  	
  	//새 답글 추가
  	function addNewReplyElement(newText,parentNickname){
	    if(newText != ""){
	        const sourceNode = window.template.content.querySelector("li");
	        const newNode = sourceNode.cloneNode(true);
	        const textSpan = newNode.querySelector('.reply__content');
	        const nicknameSpan = newNode.querySelector('.reply__to');
	        const textNode = document.createTextNode(newText);
	        const nicknameNode = document.createTextNode(parentNickname);
	        textSpan.appendChild(textNode);
	        nicknameSpan.appendChild(nicknameNode);
	        const targetNode = document.querySelector(".reply__list").lastElementChild;
	        targetNode.after(newNode);
	        document.querySelector(".reply__input-container").remove();
	    }
	}
	// 감춰진 답글링크 복구
	function restoreAddLink(){
		document.querySelectorAll(".reply__replies").forEach((item)=>{
					item.classList.remove("hidden"); 
				});
	}	
  
  