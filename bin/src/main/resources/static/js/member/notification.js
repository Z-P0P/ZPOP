function sendAll(){
		const notifications = document.querySelectorAll(".notification");
		const noNotification = document.querySelector(".notification--none");
		if(noNotification != null)
			return;
			
		for(n of notifications){
			if(n.getAttribute("data-type")==1){
				const type = 1;
				const readAt = true;
				const data = {
		            method: "POST",
		            headers: {
		                "Content-Type": "application/json",
		            },
		            body: JSON.stringify({
						readAt,
		                type
	          	  })
       	 		}
			fetch("/notification/type", data)
			}
			else{
				 const id = n.getAttribute("data-id");
				 const readAt = true;	
				 const data = {
	                method: "POST",
	                headers: {
	                    "Content-Type": "application/json",
	                },
	                body: JSON.stringify({
						id,
	                    readAt
	                })
	            }
			fetch("/notification/read", data)	
			}
		}
	}

	function readAll(){
		const headerNotification = document.querySelector("#header-notification");
		const notificationCancelBtn = document.querySelector(".readAll-btn");
		const upperBlank = document.querySelector("#upper-blank");
		const notifications = document.querySelectorAll(".notification");
		const container = document.querySelector(".notification-container");
		//알림 모두 없애기
		headerNotification.classList.remove("header__notification-on");
		headerNotification.classList.add("header__notification");
		
		for(const n of notifications)
			n.remove();	
		
		// 안내문구 추가
		let template=`<div class="notification notification--none";">받은 알림이 없네요!</div>`;
		container.insertAdjacentHTML("afterbegin",template);
		notificationCancelBtn.classList.add("deactivated-btn");
		notificationCancelBtn.innerHTML="모두 읽음";
	}

	function readOne(e){
		e.preventDefault();
		let deleteTarget = e.target;
		for (deleteTarget; !deleteTarget.classList.contains('notification');
			deleteTarget = deleteTarget.parentElement);
			
		let notificationModal = deleteTarget.parentElement;
		notificationModal.removeChild(deleteTarget);
		
		const notifications = document.querySelectorAll(".notification");
		if(notifications.length == 0)
			readAll();
		
		 const id = deleteTarget.getAttribute("data-id");
		 const readAt = true;	
		 const data = {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
					id,
                    readAt
                })
            }
			
		fetch("/notification/read", data)
		.then(response=>{
			window.location.href=deleteTarget.getAttribute("data-url");
		})
	}

    window.addEventListener("load", (e) => {
        const notificationBtn = document.querySelector("#notification-btn");
		const notificationCancelBtn = document.querySelector(".readAll-btn");
		const modalCloseBtn = document.querySelector(".modal-close-btn-div");
		const upperBlank = document.querySelector("#upper-blank");
		const headerNotification = document.querySelector("#header-notification");
		const container = document.querySelector(".notification-container");
		const type = [];
		if(!notificationBtn) return;
		 fetch("/notification")
		            .then((response) => {
						return response.json();
					})
					.then((data)=>{
						for (let d of data){
							
							switch(d.type){
								case 1: typeStatement = "😃 평가하지 않은 모임이 있어요 !";
									break;
								case 2: typeStatement = "😃 내 모임에 새로운 참여자가 있어요 !";
									break;
								case 3: typeStatement = "💬 내 모임에 댓글이 달렸어요 !";
									break;
								case 4: typeStatement = "💬 내 댓글에 답글이 달렸어요 ! ";
									break;
							}
							// 배열에 타입을 넣어주기
							type.push(d.type);
							
							if(d.type!=1){
								let div = document.createElement("DIV");
								
								div.classList.add("notification");
								div.onclick = readOne;
								div.innerHTML=`${typeStatement}<p>${d.elapsedTime}</p>`;
								div.setAttribute("data-id",d.id);
								div.setAttribute("data-url",d.url);
								container.insertAdjacentElement('afterbegin', div);
							}
						}
						
					if(type.includes(1)){
//							var count = 0;
//							for(t of type)
//								if(t==1) count++;
							
							let template = `
							<div class="notification eval-div" data-type ="1" data-url="/member/me/meeting">😎 평가하지 않은 모임이 있어요 !<p>이동하기</p></div>
							`;
							container.insertAdjacentHTML('afterbegin',template);
						    const evalDiv = document.querySelector(".eval-div");
							evalDiv.addEventListener("click",(e)=>{
								e.preventDefault();
								let deleteTarget = e.target;
								for (deleteTarget; !deleteTarget.classList.contains('notification');
									deleteTarget = deleteTarget.parentElement);
									
								let notificationModal = deleteTarget.parentElement;
								notificationModal.removeChild(deleteTarget);
								
								const type = 1;
								const readAt = true;
								const data = {
					                method: "POST",
					                headers: {
					                    "Content-Type": "application/json",
					                },
					                body: JSON.stringify({
										readAt,
					                    type
					              	  })
					           	 }
								fetch("http://localhost:8080/notification/type", data)
								.then(response=>{
									window.location.href=deleteTarget.getAttribute("data-url");
								})
								
							});
						}
							// 알림 아이콘 변경 
					const notification = document.querySelectorAll(".notification");
					if(notification.length!=0){
						headerNotification.classList.remove("header__notification");
						headerNotification.classList.add("header__notification-on");
					}
					else if(notification.length==0)
						readAll();
					})
		  			
        
        // 모두 읽기 버튼
        notificationCancelBtn.addEventListener("click",(e)=>{
			 e.preventDefault();
			 sendAll();
			 readAll();
		})
    });