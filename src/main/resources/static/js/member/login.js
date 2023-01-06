window.addEventListener('load', function(){
	Kakao.init('bc0043a07c01b34f2f4e3c5b5b926641');
	
	const naverLoginBtn = document.getElementById('naver-login-btn');
	
	const serviceURL = "https://nid.naver.com/oauth2.0/authorize";
	const clientId = "fXPYssaX_SOB2qThvRtF"; 
	const redirectUrl = encodeURI("http://localhost:8080/login/oauth/naver"); 
	const responseType = "code";
	const state = encodeURI(generateRandomString(10));
	const requestUrl = `${serviceURL}?response_type=${responseType}&client_id=${clientId}&state=${state}&redirect_uri=${redirectUrl}`;
	naverLoginBtn.onclick=function(){
	
		location.href=requestUrl;
	
	};

});

// naver 로그인에 필요한 state 파라미터를 생성하는 함수. 예시를 위해 위에서는 10개의 랜덤 문자열을 사용함
generateRandomString = (num) => {
  const characters ='ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz';
  let result = '';
  const charactersLength = characters.length;
  for (let i = 0; i < num; i++) {
      result += characters.charAt(Math.floor(Math.random() * charactersLength));
  }

  return result;
}
