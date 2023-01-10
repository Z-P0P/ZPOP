window.addEventListener('load', function() {

	const loginModal = document.querySelector('#modal-register');
	const validateNicknameBtn = loginModal.querySelector('#validate-nickname-btn');
	const setNicknameBtn = loginModal.querySelector('#set-nickname-btn');
	const nicknameInput = loginModal.querySelector('input');
	const inputMessage = loginModal.querySelector('.input__message');
	const delay = 300;
	const modalContentContainer = loginModal.querySelector('.modal__content-container');
	const decoClass = ['input-text__content-wrapper--error','input-text__content-wrapper--correct'];
	let timer;

	nicknameInput.onkeydown = function(e) {
		nicknameInput.parentElement.classList.remove(...decoClass);
		nicknameInput.parentElement.classList.remove(...decoClass);

		setNicknameBtn.classList.remove('btn-action');
		inputMessage.innerText = '';
		if (timer) {
			clearTimeout(timer);
		}
		timer = setTimeout(function() {
			validateNickname(e.target.value);
		}, delay);
	};

	setNicknameBtn.onclick = setNickname;

	function validateNickname(nickname) {
		if (nickname.length == 0) return;
		const nicknameValidExp = /^(?=.*[a-zA-Z0-9가-힣])[a-zA-Z0-9가-힣]{0,10}$/;

		if (!nicknameValidExp.test(nickname)) {
			inputMessage.innerText = '닉네임은 한글, 숫자, 영어만 조합해주세요!'
			nicknameInput.parentElement.classList.add('input-text__content-wrapper--error');
			return;
		}
		const form = new FormData();
		form.append('nickname', nickname);
		const url = '/register/nickname/validation';
		const options = {
			method: "POST",
			body: form,
		}
		fetch(url, options)
			.then(response => {
				if (response.ok) {
					return response.json();
				}
				throw Error();
			})
			.then(data => {
				let result = data.result;
				if (result == "NICKNAME_VALID") {
					inputMessage.innerText = '사용할 수 있는 닉네임이에요!';
					setNicknameBtn.classList.add('btn-action');
					nicknameInput.parentElement.classList.add('input-text__content-wrapper--correct');
					return;
				}
				if (result == "NICKNAME_ALREADY_USED") {
					inputMessage.innerText = '이미 사용중인 닉네임이에요!';
					nicknameInput.parentElement.classList.add('input-text__content-wrapper--error');
					return;
				}
			})
			.catch(err => {

			})
	}

	function setNickname(e) {
		if (!e.target.classList.contains('btn-action')) return;
		const nickname = nicknameInput.value;
		const form = new FormData();
		form.append('nickname', nickname);
		
		const url = '/register/nickname/set';
		const options = {
			method: "POST",
			body: form,
		}
		fetch(url, options)
			.then(res => {
				if (!res.ok){
					throw new Error();
				}
				return res.json();				
			})
			.then(data => {
				if (data.result == "FORMAT_NOT_ALLOWED"){
					inputMessage.innerText = '잘못된 닉네임 형식이에요.';
					nicknameInput.parentElement.classList.add('input-text__content-wrapper--error');
				}
				else if (data.result == "NICKNAME_ALREADY_USED"){
					inputMessage.innerText = '이미 사용중인 닉네임이에요!';
					nicknameInput.parentElement.classList.add('input-text__content-wrapper--error');
				}
				else{
					modalContentContainer.classList.add('modal__content-container--content1');				
				}
			})
			.catch(()=> {
				inputMessage.innerText = '닉네임 설정에 오류가 발생했어요!';
				nicknameInput.parentElement.classList.add('input-text__content-wrapper--error');
			});
	}

});



