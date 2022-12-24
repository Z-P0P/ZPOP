import state from "./state.js";
import onClickCategory from "./on-click-category.js";
import onClickRegion from "./on-click-region.js";
import onClickToggle from "./on-click-toggle.js";
import onSearch from "./on-search.js";
import infiniteScroll from "./infinite-scroll.js";

window.addEventListener("load", function () {
  // 사용자 검색 
  const searchBar = document.querySelector("#search-bar-input");
  searchBar.onkeyup = onSearch;

  // 카테고리
  const categories = document.querySelectorAll(".category");
  for (const category of categories) {
    category.onclick = onClickCategory.bind(categories);
  }

  // 지역
  const regions = document.querySelectorAll(".select-box__options > li");
  for (const region of regions) {
    region.onclick = onClickRegion;
  }

  // 모집 중인 모임만 보기 토글 ON/OFF
  const toggle = document.querySelector("#toggle");
  toggle.onclick = onClickToggle;

  // 무한스크롤 다음 모임 리스트 요청
  window.onscroll = infiniteScroll;
});
