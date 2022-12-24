import state from "./state.js";
import requestMeetings from "./request-meetings.js";
import { insertMeetings } from "./view.js";
import { generateUrl } from "./util.js";

export default function () {
  const THROTTLING_TIMER = 1300;
  const { scrollHeight, scrollTop, clientHeight } = document.documentElement;

  if (!state.timer && scrollTop + clientHeight > scrollHeight - 5) {
    state.timer = setTimeout(() => {
      state.timer = null;

      const parameter = {};
      // 토글 checked === true -> 모집 중인 모임만 보기
      if (state.isToggleOn)
        parameter.isClosed = false;
      // 사용자가 검색한 검색어가 있을경우
      if (state.searchKeyword)
        parameter.keyword = state.searchKeyword;

      const lastMeetingId =
        document.querySelector("#meetings").lastElementChild.dataset.id;
      parameter.startId = lastMeetingId;

      const url = generateUrl(parameter);

      requestMeetings(url).then((meetings) => {
        insertMeetings(meetings);
      });
    }, THROTTLING_TIMER);
  }
};