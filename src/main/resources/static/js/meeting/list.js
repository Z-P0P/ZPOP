window.addEventListener("load", function () {
  let timer; // 쓰로틀링 타이머

  // 스크롤 위치가 밑에 닿으면 다음 모임 리스트 요청
  window.onscroll = function () {
    const { scrollHeight, scrollTop, clientHeight } = document.documentElement;

    if (!timer && scrollTop + clientHeight > scrollHeight - 5) {
      timer = setTimeout(() => {
        timer = null;
        getNextMeetings();
      }, 1300);
    }
  };

  /**
   * 받아온 모임 리스트를 html 모임 ul에 더한다.
   */
  function getNextMeetings() {
    // 화면에 있는 모임 리스트 중 마지막 모임 id 얻기
    const lastMeetingId = meetings.lastElementChild.dataset.id;

    const url = `http://localhost:8080/meeting/api/list?startId=${lastMeetingId}`;

    fetch(url)
      .then((res) => res.json())
      .then((data) => {
        // 다음 데이터가 없을시 로딩 숨김
        if (data.length === 0) {
          const loadingElement = document.querySelector(".lds-roller");
          loadingElement.style.visibility = "hidden";
          return;
        }

        // 받아온 모임 리스트 배열을 돌면서 새 모임 카드 화면에 출력
        for (const m of data) createMeetingCard(m);
      })
      .catch((e) => {
        alert("잠시후에 시도해주세요"); //TODO: 처리
      });
  }

  /**
   * 받아온 모임 리스트를 모임 ul에 자식 li로 생성한다
   */
  function createMeetingCard(meetingData) {
    const meetingElement = document.createElement("li");

    meetingElement.setAttribute("data-id", meetingData.id);

    const closedElement = meetingData.closed
      ? '<div class="meeting__status meeting__status--off">모집완료</div>'
      : '<div class="meeting__status meeting__status--on">모집중</div>';

    meetingElement.innerHTML = `
  <a class="meeting" href="/meeting/${meetingData.id}">
    <div class="meeting__header">
      <div class="meeting__info-detail-more">
        <span class="meeting__category">${meetingData.category}</span>
        <span class="meeting__region add-deco-img-left deco-img-location"
          >${meetingData.region}</span
        >
        <span class="meeting__date">${meetingData.startedAt}</span>
      </div>
      <h3 class="meeting__title">${meetingData.title}</h3>
    </div>

    <div class="meeting__info">
      <ul class="meeting__tags">
        <li>#${meetingData.ageRange}</li>
        <li>#${meetingData.maxMember}명</li>
        <li>#${meetingData.genderCategory}</li>
      </ul>
      <div class="meeting__info-detail">
        ${closedElement}
        <div class="meeting__info-detail-more">
          <div class="meeting__views add-deco-img-left deco-img-eyes">
            ${meetingData.viewCount}
          </div>
          <div class="meeting__comments add-deco-img-left deco-img-speech-bubble">
            ${meetingData.commentCount}
          </div>
        </div>
      </div>
    </div>
  </a>`;
    //TODO: meetingsElement.insertAdjacentHTML("beforeend", template);
    // 모임 <ul>에 meetingElement 추가
    const meetingsElement = document.querySelector("#meetings");
    meetingsElement.appendChild(meetingElement);
  }
});
