import state from "./state.js";
import requestMeetings from "./request-meetings.js";
import {
  insertMeetings,
  removeMeetings,
} from "./view.js";
import generateUrl from "./generate-url.js";

export default function (e) {
  const ENTER_KEY_CODE = 13;

  if (window.event.keyCode == ENTER_KEY_CODE) {
    const searchKeyword = document.querySelector("#search-bar-input").value;
    if(!searchKeyword.replace(/ /g, '')) {
      alert("검색어를 입력해주세요.");
      return;
    }
      
    window.location.href = `/search/${searchKeyword}`;
  }
}
