import state from "./state.js";
import requestMeetings from "./request-meetings.js";
import { hideLoading, hideResultNone, insertMeetings, showLoading, showResultNone } from "./view.js";
import generateUrl from "./generate-url.js";

export default function () {
  const THROTTLING_TIMER = 1300;
  const { scrollHeight, scrollTop, clientHeight } = document.documentElement;

  if (isMeetingsUlEmtpy()) {
    hideLoading();
    showResultNone();
    return;
  }
  showLoading();

  if (!state.timer && scrollTop + clientHeight > scrollHeight - 5) {
    state.timer = setTimeout(() => {
      state.timer = null;

      const isScrollEvent = true;
      const url = generateUrl(isScrollEvent);

      requestMeetings(url).then((meetings) => {
        hideResultNone();
        insertMeetings(meetings);
      });
    }, THROTTLING_TIMER);
  }

  /**
   * 모임 리스트(ul)내의 li가 없다면 true, 아니라면 false 리턴
   */
  function isMeetingsUlEmtpy() {
    return document.querySelector("#meetings").innerHTML.trim() == "";
  }
};