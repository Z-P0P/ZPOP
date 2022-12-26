window.addEventListener(("load"), function () {
  // 검색 페이지 && 데스크탑에서 보이는 헤더 검색바
  const searchBar = this.document.querySelector(".header__search-bar--pc-wrap");
  const searchBarModal = document.querySelector(".modal__search-bar");
  const pathParam = location.pathname;
  const ENTER_KEY_CODE = 13;

  // 검색 페이지가 아니라면 searchBar에 hidden 추가
  handleDesktopSearchBar();

  searchBarModal.onkeyup = function(e) {
    if(window.event.keyCode !== ENTER_KEY_CODE) 
      return;

    const searchKeyword = searchBarModal.value;
    if(!searchKeyword.replace(/ /g, '')) {
      alert("검색어를 입력해주세요.");
      return;
    }
      
    window.location.href = `/search/${searchKeyword}`;
  };

  function handleDesktopSearchBar () {
      if (!pathParam.includes("/search")) {
        if (searchBar.classList.contains("hidden")) return;

        searchBar.classList.add("hidden");
      }

      if (searchBar.classList.remove("hidden"));
  }
});