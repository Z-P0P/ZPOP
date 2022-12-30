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
			}
		}
	});

	const meetingForm = document.querySelector(".meeting-form");
	const messages = meetingForm.querySelectorAll('.input__message');
	const allSelectBoxOptions = meetingForm.querySelectorAll('.select-box__options > li');
	
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


		const submitURL = '/meeting/register';
		const data = new FormData(e.target);
		const parser = new DOMParser();
		const quillContentDOM = parser.parseFromString(quill.root.innerHTML, 'text/html').body;
		const attachedImages = quillContentDOM.querySelectorAll('img');

		// 등록된 글의 이미지를 찾아서 이미지 이름을 'ZPOP_날짜시간_이미지순서' 로 저장
		// base64 이미지는 그대로 메모리에 냅두고, 이미지를 파일로 바꿔서 form 데이터에 저장/
	
		let count = 0;
		attachedImages.forEach(base64Img => {
			let fileName = `${getDateTime()}_${count}`;
			let formAttachedImage = dataURLtoFile(base64Img.src, fileName);
			fileName = formAttachedImage.name;
			data.append('images', formAttachedImage);
			base64Img.src = fileName;
			count++;
		});

		// 바뀐 내용을 문자열로 바꿔서 form 데이터에 반영
		const content = quillContentDOM.innerHTML;

		data.append('content', content);

		count = 0;
		let isBlanked = false;
		// form 데이터 중에 공백이 있으면 에러메시지 출력
		data.forEach((value, key) => {
			if (value == '') {
				messages[count].innerText = "값이 없어요";
				isBlanked = true;
			}
			count++;
		})
		if (isBlanked) return;

		const option = {
			method: "POST",
			body: data,
		}
				
		// 추후 예외처리 필요함
		fetch(submitURL, option)
		.then(response => response.body)
		.then(data=>console.log(data));

	});
});


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


// base64 이미지를 File Object로 변환
function dataURLtoFile(dataurl, filename) {
let arr = dataurl.split(','), mime = arr[0].match(/:(.*?);/)[1],
    bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
    while(n--){
        u8arr[n] = bstr.charCodeAt(n);
    }
	let extension = mime.split('/')[1];
    return new File([u8arr], `ZPOP_${filename}.${extension}`, {type:mime});
}