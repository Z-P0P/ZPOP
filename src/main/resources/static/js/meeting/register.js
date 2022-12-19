// meeting에서 register.html에서 사용하는 JS 파일입니다.


// 등록하기 버튼을 누른다.
// 사용자가 입력한 값을 추출한다.
// 서버에 요청을 JSON형태로 보낸다. (POST) (/meeting)
// Responsebody , RestController
// const meetingDate = document.querySelector("#meeting-date").value;
// const meetingRegion = document.querySelector("#meeting-region").value;
// const meetingDetailRegion = document.querySelector("#meeting-detail-region").value;
// const meetingAgeRange = document.querySelector("#meeting-ageRange").value;
// const meetingGender = document.querySelector("#meeting-gender").value;
// const meetingOpenNum = document.querySelector("#meeting-open-num").value;
// const meetingContatc = document.querySelector("#meeting-contact").value;
// const meetingOpenChatLink = document.querySelector("#meeting-openchat-link").value;




// console.log(meetingTitle);
// console.log(meetingCategory);
// console.log(meetingDate);
// console.log(meetingRegion);
// console.log(meetingDetailRegion);
// console.log(meetingAgeRange);
// console.log(meetingGender);
// console.log(meetingOpenNum);
// console.log(meetingContatc);
// console.log(meetingOpenChatLink);


window.addEventListener("load", function () {
  const registerBtn = document.querySelector("#register-btn");

  registerBtn.onclick = function (e) {

      e.preventDefault();

      const meetingTitle = document.querySelector("#meeting-title").value;
      const link = document.querySelector("#meeting-openchat-link").value;
      const data = { 
    method: 'POST', // 또는 'PUT'
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({meetingTitle:meetingTitle,
                              link : link})
  };

      fetch('//http://localhost:8080/meeting/test',data)
      .then((response) => {
        console.log('성공:', data);
        alert("등록에 성공했습니다!!");
      })
      .catch((error) => {
        console.error('실패:', error);
        alert("주문에 실패했습니다!");
      });
      // method




  }
})
