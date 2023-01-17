import quillGenerator from "../utils/quill-generator.js";


window.addEventListener("load", function() {

	const quill = quillGenerator("#editor", {
		theme: 'snow',
		modules: {
			toolbar: {
				container: [
					[{ 'header': [1, 2, 3, 4, 5, 6, false] }],
					['bold', 'italic', 'underline'],
					[{ 'list': 'ordered' }, { 'list': 'bullet' }],
					[{ 'align': [] }],
					['link', 'image'],
					['clean'],
					[{ 'color': [] }]
				],
				handlers: {
					//커스텀 image툴바 onclick handler
					image: imageHandler,
				}
			},
		}
	});
	const fileUploadInput = document.getElementById("fileUpload");
	const meetingForm = document.querySelector(".meeting-form");
	const messages = meetingForm.querySelectorAll('.input__message');
	const allSelectBoxOptions = meetingForm.querySelectorAll('.select-box__options > li');
	const inputs = meetingForm.querySelectorAll('input');
	let isRequesting = false;
	fileUploadInput.onchange = fileUploadHandler;

	// 셀렉트박스가 한 번이라도 선택되면 그 아래의 에러 메시지지를 초기화시키는 event 등록
	// 추후 refectoring 필요함
	allSelectBoxOptions.forEach(option => {
		option.addEventListener('click', function(e) {
			let target = e.target;
			for (target; target.tagName != "DIV"; target = target.parentElement);
			const message = target.nextElementSibling;
			message.innerText = '';
		})
	})

	// input[type=text] 에 내용이 한번이라도 입력하면 그 아래의 메시지를 초기화시키는 event 등록
	const allInputTextBoxes = meetingForm.querySelectorAll('.input-text__content');
	allInputTextBoxes.forEach(input => {
		input.onkeypress = function(e) {
			let target = e.target;
			for (target; target.tagName != "DIV"; target = target.parentElement);
			const message = target.nextElementSibling;
			message.innerText = '';
		}
	})

	// 등록하기 버튼 누를 때의 로직
	meetingForm.addEventListener('submit', function(e) {
		e.preventDefault();

		// 메시지 출력 전 기존에 출력된 모든 메시지 초기화
		messages.forEach(message => message.innerText = '');

		let data = {};
		inputs.forEach(input => {
			if (input.name) {
				data[input.name] = input.value;
			}
		})
		


		let count = 0;
		let isBlanked = false;
		// form 데이터 중에 공백이 있으면 에러메시지 출력
		for (let i in data) {
			if (data[i] == '') {
				messages[count].innerText = "값이 없어요";
				isBlanked = true;
			}
			count++;
		}
		//if (isBlanked) return;


		const submitURL = '/meeting/register';
		const parser = new DOMParser();
		const quillContentDOM = parser.parseFromString(quill.root.innerHTML, 'text/html').body;
		const attachedImages = quillContentDOM.querySelectorAll('img');

		count = 0;
		let images = [];
		attachedImages.forEach(image => {
			if (image.dataset.id == undefined) {
				return;
			}
			images.push({
				id: image.dataset.id,
				});
		});
		data.images = images;

		const content = quillContentDOM.innerHTML;
		data.content = content;

		const option = {
			method: "POST",
			headers: {"Content-Type": "application/json"},
			body: JSON.stringify(data),
		}
	
		
		
		// 추후 예외처리 필요함
		showModal();
		if (isRequesting) {
			
			return;
		}
		isRequesting = true;
		fetch(submitURL, option)
			.then(response => {
				
				if (response.ok) {
					return response.json();
				}
				else {
					showError();
					isRequesting = false;
				}
			})
			.then(data => {
				showSuccess(data.meetingId);
			})
			.catch(() => {
				showError();
				isRequesting = false;
			})
	});
});

/*
// 이미지 파일이름 설정 시 붙는 날짜,시간을 반환
function getDateTime() {
	let now = new Date();
	let year = now.getFullYear();
	let month = now.getMonth() + 1;
	let day = now.getDate();
	let hour = now.getHours();
	let minute = now.getMinutes();
	let second = now.getSeconds();
	let millisecond = now.getMilliseconds();

	if (month.toString().length == 1) {
		month = '0' + month;
	}
	if (day.toString().length == 1) {
		day = '0' + day;
	}
	if (hour.toString().length == 1) {
		hour = '0' + hour;
	}
	if (minute.toString().length == 1) {
		minute = '0' + minute;
	}
	if (second.toString().length == 1) {
		second = '0' + second;
	}
	if (millisecond.toString().length == 1) {
		millisecond = '00' + second;
	}
	if (millisecond.toString().length == 2) {
		millisecond = '0' + second;
	}

	let dateTime = `${year}${month}${day}_${hour}${minute}${second}${millisecond}`;
	return dateTime;
}
*/

function showModal(isRequesting) {
	const registerMeetingModal = document.querySelector("#modal-register-meeting");
	registerMeetingModal.classList.remove('hidden');
	const registerStatusText = registerMeetingModal.querySelector(".register-status__message");

	if (isRequesting) {

	}

	const registerStatusIcon = registerMeetingModal.querySelector(".register-status__icon");
	registerStatusIcon.classList.add('lds-roller');
	registerStatusIcon.classList.remove('icon-cancel');
	registerStatusIcon.classList.remove('icon-done');
}

function showError() {
	const registerStatus = document.querySelector(".register-status");
	const registerStatusText = registerStatus.querySelector(".register-status__message");
	const registerStatusBtn = registerStatus.querySelector(".register-status__btn");
	const registerStatusIcon = registerStatus.querySelector(".register-status__icon");
	registerStatusIcon.classList.remove("lds-roller");
	registerStatusIcon.classList.add("icon-cancel");
	registerStatusText.innerText = "모임글 등록에 실패했어요.";
}

function showSuccess(meetingId) {
	const registerStatus = document.querySelector(".register-status");
	const registerStatusText = registerStatus.querySelector(".register-status__message");
	const registerStatusBtn = registerStatus.querySelector(".register-status__btn");
	const registerStatusIcon = registerStatus.querySelector(".register-status__icon");
	registerStatusIcon.classList.remove("lds-roller");
	registerStatusIcon.classList.add("icon-done");
	registerStatusText.innerText = "모임글을 등록했어요!";
	registerStatusBtn.classList.add("btn-action");
	registerStatusBtn.href = `/meeting/${meetingId}`;
	registerStatusBtn.innerText = "모임글로 이동";
}

function imageHandler() {
	const fileUploadInput = document.getElementById("fileUpload");
	let event = new MouseEvent("click", { bubbles: true });
	fileUploadInput.dispatchEvent(event);
	
}

function fileUploadHandler(e) {
	let input = this.files[0];
	
	if (!input) {
		return 
	}
	
	if(!isImage(input.name)){
		return;
	}
	
	const form = new FormData();
	form.append('file', input);
	form.append('path', '/image/meeting');
	const uploadUrl = '/upload';
	const option = {
		method: "POST",
		body: form,
	}

	fetch(uploadUrl, option)
	.then(response => response.json())
	.then(data=>{
		
		const selection = window.getSelection();
		const range = selection.getRangeAt(0);
		let imageTag = document.createElement('IMG');
		imageTag.src = `/image/meeting/${data.name}`;
		imageTag.dataset.id = data.id;
		range.insertNode(imageTag);
		selection.getSelection().modify('move', 'right', 'line');
	})

}

function getExtension(filename) {
  var parts = filename.split('.');
  return parts[parts.length - 1];
}

function isImage(filename) {
  var ext = getExtension(filename);
  switch (ext.toLowerCase()) {
    case 'jpg':
    case 'gif':
    case 'bmp':
    case 'png':
    case 'svg':
      //etc
      return true;
  }
  return false;
}

