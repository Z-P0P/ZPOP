window.addEventListener('load', function(){
	
	const loginModal = document.querySelector('#modal-register');
    const validateNicknameBtn = loginModal.querySelector('#validate-nickname-btn');
    const setNicknameBtn = loginModal.querySelector('#set-nickname-btn');
    
    validateNicknameBtn.onclick = ()=> {
        const nickname = loginModal.querySelector(".input-text__content").value;
        let result = validateNickname(nickname);
    }
    setNicknameBtn.onclick = ()=>{
        const nickname = loginModal.querySelector(".input-text__content").value;
        let result = setNickname(nickname);
    }
});

function validateNickname(nickname){
	console.log(nickname);
    const url = '/register/nickname/validation';
    const options = {
        method: "POST",
        headers : {
            "Content-Type": "application/x-www-form-urlencoded",
        },
        body :new URLSearchParams({
            'nickname' : nickname
        })
    }
    fetch(url, options)
    .then(res => {
        console.log(res.statusText);
        return res.text();
    })
    .then(data => {
		console.log(data);
	});
}

function setNickname(nickname){
	console.log(nickname);
    const url = '/register/nickname/set';
    const options = {
        method: "POST",
        headers : {
            "Content-Type": "application/x-www-form-urlencoded",
        },
        body :new URLSearchParams({
            'nickname' : nickname
        })
    }
    fetch(url, options)
    .then(res => {
        console.log(res.statusText);
        return res.text();
    })
    .then(data => {
		if (data=="nickname_created"){
			location.href = "/login"
		}
	});
}

