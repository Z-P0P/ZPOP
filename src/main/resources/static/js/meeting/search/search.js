import state from "./state.js";
import onClickToggle from "./on-click-toggle.js";
import onSearch from "./on-search.js";
import { showResultNone } from "../list/view.js";
import onScroll from "../list/on-scroll.js";

window.addEventListener("load", function () {
  // path에있는 검색어 state에 저장
  const path = decodeURI(window.location.pathname);
  const searchKeyword = path.split("/search/")[1];
  state.searchKeyword = searchKeyword;

  // 사용자 검색
  const searchBar = document.querySelector(".header__search-content--pc");
  searchBar.onkeyup = onSearch;

  // 모집 중인 모임만 보기 토글 ON/OFF
  const toggle = document.querySelector("#toggle");
  toggle.onclick = onClickToggle;

  // 무한스크롤 다음 모임 리스트 요청
  window.onscroll = onScroll;

  // 검색 결과가 비었는지 확인
  const isEmpty = document.querySelector("#meetings").innerHTML.trim() == "";
  if (isEmpty) showResultNone();
});
