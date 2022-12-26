import state from "./state.js";
import requestMeetings from "./request-meetings.js";
import {
  insertMeetings,
  removeMeetings,
} from "./view.js";
import { generateUrl } from "./util.js";

export default function (e) {
  const ENTER_KEY_CODE = 13;

  if (window.event.keyCode == ENTER_KEY_CODE) {
    // const searchKeyword = document.querySelector("#search-bar-input").value;
    // state.searchKeyword = searchKeyword;

    // const url = generateUrl();

    // requestMeetings(url).then((meetings) => {
    //   removeMeetings();

    //   // 검색결과가 없을 때
    //   if (meetings.length === 0) {
    //     const searchKeyword = document.querySelector(".search-keyword");
    //     searchKeyword.innerText = state.searchKeyword;

    //     showSearchResultNone();
    //     return;
    //   }
    //   // 검색결과가 있을 때
    //   hideSearchResultNone();
    //   insertMeetings(meetings);
    // });
  }
}
