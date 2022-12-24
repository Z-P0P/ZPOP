/**
 * 모임 카드 요소 모두 제거
 */
export const removeMeetings = function () {
  hideResultNone();
  const meetingsElement = document.querySelector("#meetings");
  meetingsElement.innerHTML = "";
};

/**
 * 모임 정보를 view에 출력한다
 */
export const insertMeetings = function (meetingsData) {
  if (meetingsData.length === 0) {
    hideLoading();
    return;
  }

  // 받아온 모임 리스트 배열을 돌면서 새 모임 카드 view에 출력
  for (const m of meetingsData) insertMeeting(m);
};

export const insertMeeting = function (meetingData) {
  const meetings = document.querySelector("#meetings");

  const meetingStatus = meetingData.isClosed
    ? '<div class="meeting__status meeting__status--off">모집완료</div>'
    : '<div class="meeting__status meeting__status--on">모집중</div>';

  const template = `
      <li data-id="${meetingData.id}">
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
              ${meetingStatus}
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
        </a>
      </li>`;

  meetings.insertAdjacentHTML("beforeend", template);
};

export const hideLoading = function () {
  const loading = document.querySelector(".lds-roller");
  if (loading.style.visibility === "hidden") return;
  loading.style.visibility = "hidden";
};

export const showLoading = function () {
  const loading = document.querySelector(".lds-roller");
  if (loading.style.visibility === "visible") return;
  loading.style.visibility = "visible";
};

export const showResultNone = function () {
  const resultNone = document.querySelector(".result-none");
  if (!resultNone.classList.contains("hidden")) return;
  resultNone.classList.remove("hidden");
}

export const hideResultNone = function () {
  const resultNone = document.querySelector(".result-none");
  if (resultNone.classList.contains("hidden")) return;
  resultNone.classList.add("hidden");
}
