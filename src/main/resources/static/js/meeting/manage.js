/**
 * 
 */
 
 function getParticipant(){
	fetch(`/meeting/${meetingId}/participant`)
	.then(response => {
		if (response.ok) {
			return response;
		}
		else console.log(response)
	})
	.then(data => data.json())
	.then(participants => {
		let count = participants.length;
		participantCount.innerText = count;
		for (const p of participants) {
			let template = `
				<li>
		            <div class="participant__info">
		                <img src="/images/girl.svg">  
		                <span>${p.nickname}</span>
		            </div>
	            </li>
			`
			participantUl.insertAdjacentHTML("beforeend", template);
		}
	});
	
}