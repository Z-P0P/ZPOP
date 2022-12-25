window.addEventListener(("load"), function () {
  // 검색 페이지 && 데스크탑에서 보이는 헤더 검색바
  const searchBar = this.document.querySelector(".header__search-bar--pc-wrap");
  const pathParam = location.pathname;

  // 검색 페이지가 아니라면 searchBar에 hidden 추가
  if(!pathParam.includes("/search")) {
    if(searchBar.classList.contains("hidden")) return;

    searchBar.classList.add("hidden");
  }

  if(searchBar.classList.remove("hidden"));
});