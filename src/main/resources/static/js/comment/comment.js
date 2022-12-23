	function addNewCommentElement(newText){
  		
  		if(newText != ""){
	  		const template = document.querySelector("#template");
	  		const sourceNode = window.template.content.querySelector("li");
	  		const newNode = sourceNode.cloneNode(true);
	  		const textSpan = newNode.querySelector('.comment__content');
	  		const newContent = document.createTextNode(newText);
	  		textSpan.appendChild(newContent);
	  		const targetNode = document.querySelector(".comment__list li:last-child");
	  		if(targetNode ==null)
	  			template.after(newNode);
	  		else
	  			targetNode.after(newNode);
	  		document.getElementById("comment-text").value = "";//플레이스홀더 되살림.
  		}
  	}	
  
    window.addEventListener("load", (e) => {
		const commentList = document.querySelector(".comment__list");
		const meetingId = document.querySelector("#meeting-id").innerText.trim();
        const registerBtn = document.querySelector("#register-btn");
        
        commentList.onclick = function(e){
			if(!e.target.classList.contains("reply-cnt"))
				return;
	        const parentComment = e.target.parentElement.parentElement;
			const parentId = e.target.previousElementSibling.innerText;				
			console.log(parentId)
			const data ={
				method:"POST",
				headers:{
					"Content-Type":"application/json",
				},
				body:JSON.stringify({
					"meetingId":meetingId,
					"parentCommentId":parentId
				})
			}
			 fetch("http://localhost:8080/meeting/reply", data)
            .then(response => {
				if(response.ok){
		            console.log("여기");
		            return response;
	            }
			})
			.then(data=>data.json())
			.then(json=>{
			let replies = json.resultObject;
			let parentNickname = "";
			for(r of replies){
				if (r.parentNickname != null)
					parentNickname = '@'+r.parentNickname;
			let template= `
			
						<section class="reply">
						<ul class="reply__list">
							<li> 
								<div class="profile">
									<span class="profile__image"></span>
									<span class="profile__nickname profile__nickname">${r.nickname}</span>
									<span class="profile__time">3분전</span>
									<button></button>
								</div>
								<div class="reply-container">
									<span class="reply__to">${parentNickname}</span>
									<span class="reply__content">${r.content}</span>
								</div>
								<div class="reply__replies">
									<span class="hidden"></span>
									<span>답글 달기</span> <span class="hidden"></span>
								</div>
							</li>
							<template id="template">
								<li>
									<div class="profile">
										<span class="profile__image"></span> <span
											class="profile__nickname profile__nickname">쿤님</span> <span
											class="profile__time">방금</span>
										<button></button>
									</div>
									<div class="reply-container">
										<span class="reply__at-symbol">@</span><!--span사이 공백없애기 
               							--><span class="reply__to"></span> <span class="reply__content"></span>
									</div>
									<div id="add-reply" class="reply__replies">
										<span>답글 달기</span>
									</div>
								</li>
							</template>
							</ul>
					</section>
			
			`
			parentComment.insertAdjacentHTML("beforeend",template);
			}
			
			});
		};

        registerBtn.addEventListener("click",(e) => {
            e.preventDefault();

            const commentText = document.querySelector("#comment-text").value;
			console.log(meetingId)
            const data = {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
					"meetingId":meetingId,
                    "content":commentText,
                    "writerId":3,
                })
            }

            fetch("http://localhost:8080/meeting/comment", data)
            .then(response => {
		        const newCommentText = document.getElementById("comment-text").value;
            	if(newCommentText){
	            	if(response.ok){
		            	addNewCommentElement(newCommentText);
	            	}
	            	else alert("시스템 장애로 등록이 안되고 있습니다.");
	            }
	            else alert("댓글을 입력해주세요.");
            });
            
        });
    });