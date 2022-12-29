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
import quillGenerator from "../utils/quill-generator.js";


window.addEventListener("load", function () {
  const toolbarOptions = ['bold', 'italic', 'underline', 'strike', 'link', 'image'];
  const quill = quillGenerator("#editor", {
      theme: 'snow',
      modules: {
        toolbar: toolbarOptions,
      },
  });
  
  const registerBtn = document.querySelector("#register-btn");

  registerBtn.onclick = function (e) {

    e.preventDefault();
	
    const categoryId = document.querySelector("#categoryId").dataset.id;
    const regionId = document.querySelector("#regionId").dataset.id;
    const ageRangeId = document.querySelector("#ageRangeId").dataset.id;
    const maxMember = document.querySelector("#maxMember").dataset.id;
	const genderCategory = document.querySelector("#genderCategory").dataset.id;
    const contactTypeId = document.querySelector("#contactTypeId").dataset.id;
	const startedAt = document.querySelector("#startedAt").value;
    const title = document.querySelector("#meeting-title").value;
    const content = document.querySelector("#editor").value;
    const detailRegion = document.querySelector("#detailRegion").value;
    const contact = document.querySelector("#contact").value;
    const option = {
      method: "POST", // 또는 'PUT'
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        regMemberId: 3,
        categoryId: categoryId,
        regionId: regionId,
        ageRangeId: ageRangeId,
        detailRegion: detailRegion,
        contact: contact,
        contactTypeId: contactTypeId,
        title:title,
        maxMember: maxMember,
        startedAt:startedAt,
        content:"메리크리스마스",
        genderCategory:genderCategory
      })
    }
      fetch("register",option)
      .then((response) => response.text())
      .then((data) => {
        console.log('성공:', data);
        alert("성공");
        if(data=="성공")
        	location.href = "/login";
      })
      .catch((error) => {
        console.error('실패:', error);
        alert("실패");
      });




  }
})
