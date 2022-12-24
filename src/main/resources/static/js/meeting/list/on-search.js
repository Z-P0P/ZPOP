import state from "./state.js";
import requestMeetings from "./request-meetings.js";
import { hideResultNone, insertMeetings, removeMeetings, showResultNone } from "./view.js";
import { generateUrl } from "./util.js";

export default function (e) {
  const ENTER_KEY_CODE = 13;

  if (window.event.keyCode == ENTER_KEY_CODE) {
    const searchKeyword = document.querySelector("#search-bar-input").value;
    state.searchKeyword = searchKeyword;

    const parameter = {};
    parameter.keyword = searchKeyword;
    // 토글 checked === true -> 모집 중인 모임만 보기
    if (state.isToggleOn) parameter.isClosed = false;

    const url = generateUrl(parameter);

    requestMeetings(url).then((meetings) => {
      removeMeetings();

      // 검색결과가 없을 때
      if (meetings.length === 0) {
        const searchKeyword = document.querySelector(".search-keyword");
        searchKeyword.innerText = state.searchKeyword;

        showResultNone();
        return;
      }
      // 검색결과가 있을 때
      hideResultNone();
      insertMeetings(meetings);
    });
  }
}
