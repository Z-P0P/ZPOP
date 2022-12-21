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
  		}
  		
  	}	
  
    window.addEventListener("load", (e) => {
        const registerBtn = document.querySelector("#register-btn");

        registerBtn.addEventListener("click",(e) => {
            e.preventDefault();

            const commentText = document.querySelector("#comment-text").value;

            const data = {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    "content":commentText,
                    "writerId":3,
                    "meetingId":1
                })
            }

            fetch("http://localhost:8080/comment", data)
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