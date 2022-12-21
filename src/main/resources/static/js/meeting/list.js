window.addEventListener("load", function () {
  // 상태 관리
  const state = {
    searchKeyword: null, // 검색어
    isToggleOn: document.querySelector("#toggle").checked, // 토글 ON OFF 유무
    regionOption: null, // 지역 옵션
  };

  // --------------------------------------------------- 사용자 검색 --------------------------------------------------------------
  const searchBar = document.querySelector("#search-bar-input");
  searchBar.onkeyup = function (e) {
    const ENTER_KEY_CODE = 13;

    if (window.event.keyCode == ENTER_KEY_CODE) {
      state.searchKeyword = document.querySelector("#search-bar-input").value;

      const data = {};
      // 토글 checked === true -> 모집 중인 모임만 보기
      if (state.isToggleOn) data.isClosed = false;
      data.keyword = state.searchKeyword;

      const url = generateUrl(data);

      requestMeetings(url).then((meetings) => {
        removeMeetingElements();
        // 검색결과가 없을 때
        if(meetings.length === 0) {
          const searchKeyword = document.querySelector(".search-keyword");
          searchKeyword.innerText = state.searchKeyword;

          const resultNoneElement = document.querySelector(".result-none");
          if (resultNoneElement.classList.contains("hidden"))
            resultNoneElement.classList.remove("hidden");

          return;
        }
        // 검색결과가 있을 때
        else {
          const resultNoneElement = document.querySelector(".result-none");
          if (!resultNoneElement.classList.contains("hidden"))
            resultNoneElement.classList.add("hidden");
        }
        insertMeetingsElements(meetings);
      });
    }
  };

  // ------------------------------------------ 모집 중인 모임만 보기 토글 ON/OFF ------------------------------------------------
  const toggleElement = document.querySelector("#toggle");
  toggleElement.onclick = function (e) {
    state.isToggleOn = e.target.checked;

    const data = {};
    // 토글 checked === true -> 모집 중인 모임만 보기
    if (state.isToggleOn) data.isClosed = false;
    // 사용자가 검색한 검색어가 있을경우
    if (state.searchKeyword) data.keyword = state.searchKeyword;

    const url = generateUrl(data);

    requestMeetings(url).then((meetings) => {
      removeMeetingElements();
      insertMeetingsElements(meetings);
    });
  };

  // ------------------------------------------ infinite scroll 다음 모임 리스트 요청 ------------------------------------------
  let timer; // 쓰로틀링 타이머
  window.onscroll = function () {
    const { scrollHeight, scrollTop, clientHeight } = document.documentElement;

    if (!timer && scrollTop + clientHeight > scrollHeight - 5) {
      timer = setTimeout(() => {
        timer = null;

        const data = {};
        // 토글 checked === true -> 모집 중인 모임만 보기
        if (state.isToggleOn) data.isClosed = false;
        // 사용자가 검색한 검색어가 있을경우
        if (state.searchKeyword) data.keyword = state.searchKeyword;
        // 마지막 meeting id
        const lastMeetingId = meetings.lastElementChild.dataset.id;
        data.startId = lastMeetingId;

        const url = generateUrl(data);

        requestMeetings(url).then((meetings) => {
          insertMeetingsElements(meetings);
        });
      }, 1300);
    }
  };

  /**
   * data 객체를 기반으로 페이징 요청 url을 리턴한다
   */
  function generateUrl(data) {
    const searchParams = new URLSearchParams(data).toString();
    return `/meeting/api/list?${searchParams}`;
  }

  /**
   * 모임 리스트 내의 li 모두 삭제
   */
  function removeMeetingElements() {
    const meetingsElement = document.querySelector("#meetings");
    meetingsElement.innerHTML = "";
  }

  /**
   * 서버에 모임 리스트를 요청한다
   */
  function requestMeetings(url) {
    return fetch(url)
      .then((res) => res.json())
      .catch((e) => {
        alert("잠시후에 시도해주세요"); //TODO: 처리
      });
  }

  function insertMeetingsElements(meetingData) {
    // 다음 데이터가 없을시 로딩 숨김
    if (meetingData.length === 0) {
      const loadingElement = document.querySelector(".lds-roller");
      loadingElement.style.visibility = "hidden";
      return;
    }

    // 받아온 모임 리스트 배열을 돌면서 새 모임 카드 화면에 출력
    for (const m of meetingData) insertMeeting(m);
  }

  /**
   * 받아온 모임 리스트를 모임 ul에 자식 li로 생성한다
   */
  function insertMeeting(meetingData) {
    const meetingElement = document.createElement("li");

    meetingElement.setAttribute("data-id", meetingData.id);

    const closedElement = meetingData.isClosed
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
