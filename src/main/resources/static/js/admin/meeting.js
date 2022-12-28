window.addEventListener("load", function() {

	let listSize = 10;
	const adminList = new AdminList(listSize);


});

const AdminList = function(listSize) {
	this.checkedIds = [];
	this.size = listSize;
	const searchList = document.querySelector(".admin-list-container");
	searchList.onclick = this.searchListClickHandler.bind(this);
	this.$searchList = searchList;
	const listContents = document.querySelectorAll(".admin-list__content");
	this.clickedListNum = 0;
	for (let i = 0; i < listContents.length; i++) {
		listContents[i].onclick = () => {
			this.clickedListNum = i;
			console.log(this.clickedListNum);
		}
	}
}

AdminList.prototype.clickCheckBox = function() {
	let id = this.clickedListNum;
	if (this.checkedIds[id] == true) {
		this.checkedIds[id] = false;
	}
	else {
		this.checkedIds[id] = true;
	}
	console.log(`${id}는 ${this.checkedIds[id]} 입니다.`);
	return this.checkedIds[id];
}

AdminList.prototype.searchListClickHandler = function(e) {
	const target = e.target;
	console.log("눌렸음");

	if (target.tagName == "INPUT" && target.getAttribute('type') == "checkbox") {
		if (target.parentElement.classList.contains("table-bar__left-container")) {
			this.clickCheckBoxAll();
		}
		else {
			this.clickCheckBox();
		}
		return;
	}

	this.getDetail();
	setTimeout(()=>{
		document.querySelector("#modal-meeting-details").classList.remove("hidden");		
	},100);
};

AdminList.prototype.clickCheckBoxAll = function() {
	const listCheckboxes = document.querySelectorAll(".list-content__id > input");
	listCheckboxes.forEach(checkbox => {
		let event = new MouseEvent('click', {
			bubbles: true,
			cancelable: true,
			view: window
		});
		checkbox.dispatchEvent(event);
	})
	console.log('모두 체크되었습니다.');
}

AdminList.prototype.getDetail = function() {
	const main = document.querySelector(".main-wrapper");
	
	let id = main.querySelectorAll(".list-content__id > label")[this.clickedListNum].innerText;
	const url = `/admin/meeting/${id}`;
	fetch(url)
		.then(response => response.json())
		.then(data => {
			console.log(data);
			this.updateDetails(data);
		});
};

AdminList.prototype.updateDetails = function(data) {
	const meetingDetails = data.dto;
	const details = document.querySelector(".details");
	const isDeleted = details.querySelector("#is-deleted");
	const isClosed = details.querySelector("#is-closed");	
	if(!meetingDetails.deleted){
		isDeleted.setAttribute("checked", true);		
	}
	if(meetingDetails.closed){
		isClosed.setAttribute("checked", true);
	}
	
	const title = details.querySelector("#title");
	title.value = meetingDetails.title;
	const content = details.querySelector("#content");
	content.value = meetingDetails.content;
	const detailRegion = details.querySelector("#detail-region");
	detailRegion.value = meetingDetails.detailRegion;
	
	const regMember = details.querySelector("#reg-member");
	regMember.innerHTML = 
	`<span class="participant__info" data-id=${meetingDetails.regMemberId}>
		<img src=${meetingDetails.regMemberProfile?
		'/images/profile/${meetingDetails.regMemberProfile}':
		'/images/icon/user-profile.svg'}>
		${meetingDetails.regMemberNickname}
	</span>`
	
	const contactTypeOption = details.querySelector("#contact-type-options");
	contactTypeOption.replaceChildren();
	data.contactTypes.forEach(contactType=>{
		let contactTypeTemplate=`
			<li data-id=${contactType.id}>${contactType.name}</li>
		`
		contactTypeOption.insertAdjacentHTML('beforeend', contactTypeTemplate);
	});

	
	
	
	
	const contact = details.querySelector('#contact');
	contact.value=meetingDetails.contact;
	
	const ageRangeOptions = details.querySelector("#age-range-options");
	ageRangeOptions.replaceChildren();
	data.ageRanges.forEach(ageRange=>{
		let ageRangeTemplate=`
			<li data-id=${ageRange.id}>${ageRange.type}</li>
		`
		ageRangeOptions.insertAdjacentHTML('beforeend', ageRangeTemplate);
	})
	
	const categoryOptions = details.querySelector("#category-options");
	categoryOptions.replaceChildren();
	data.categories.forEach(category=>{
		let categoryTemplate=`
			<li data-id=${category.id}>${category.name}</li>
		`
		categoryOptions.insertAdjacentHTML('beforeend', categoryTemplate);
	})

	const regionOptions = details.querySelector("#region-options");
	regionOptions.replaceChildren();
	data.regions.forEach(region=>{
		let regionTemplate=`
			<li data-id=${region.id}>${region.name}</li>
		`
		regionOptions.insertAdjacentHTML('beforeend', regionTemplate);
	})

	const participantList = details.querySelector("#participants");
	participantList.replaceChildren();
	data.participants.forEach(participant => {
		let participantTemplate = `
					<li>
					<span class="participant__info" data-id=${participant.id}>
						<img src=${participant.profileImagePath?
						'/images/profile/${participant.profileImagePath}':
						'/images/icon/user-profile.svg'}>
							${participant.nickname}
					</span>
					</li>
				`;
		participantList.insertAdjacentHTML('beforeend', participantTemplate);
	});

	const startedAt = details.querySelector("#started-at");
	startedAt.innerText = meetingDetails.startedAt;
	const closedAt = details.querySelector("#closed-at");
	closedAt.innerText = meetingDetails.closedAt;
	const createdAt = details.querySelector("#created-at");
	createdAt.innerText = meetingDetails.createdAt;
	const updatedAt = details.querySelector("#updated-at");
	updatedAt.innerText = meetingDetails.updatedAt;
	const deletedAt = details.querySelector("#deleted-at");
	deletedAt.innerText = meetingDetails.deletedAt;
	initSelectBoxes();
}

