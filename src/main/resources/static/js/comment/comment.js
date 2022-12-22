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
		const meetingId = document.querySelector("#meeting-id").innerText.trim();
        const registerBtn = document.querySelector("#register-btn");
        const readReply = document.querySelector("#comment-id");
        const parentId = document.querySelector("#comment-id").innerText;
        
        readReply.addEventListener("click",()=>{
			console.log(document.querySelector("#comment-id"))
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
		            //populateReplyFragment();
	            }
			});
		});
		const click = new Event('click');
		readReply.dispatchEvent(click);

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