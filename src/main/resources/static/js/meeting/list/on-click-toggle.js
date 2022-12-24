import state from "./state.js";
import requestMeetings from "./request-meetings.js";
import { insertMeetings, removeMeetings } from "./view.js";
import { generateUrl } from "./util.js";

export default function (e) {
  state.isToggleOn = e.target.checked;

  const parameter = {};
  // 토글 checked === true -> 모집 중인 모임만 보기
  if (state.isToggleOn) parameter.isClosed = false;
  // 사용자가 검색한 검색어가 있을경우
  if (state.searchKeyword) parameter.keyword = state.searchKeyword;

  const url = generateUrl(parameter);

  requestMeetings(url).then((meetings) => {
    removeMeetings();
    insertMeetings(meetings);
  });
}
