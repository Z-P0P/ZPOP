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

generateRandomString = (num) => {
  const characters ='ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz';
  let result = '';
  const charactersLength = characters.length;
  for (let i = 0; i < num; i++) {
      result += characters.charAt(Math.floor(Math.random() * charactersLength));
  }

  return result;
}
