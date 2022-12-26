export default function (e) {
  const ENTER_KEY_CODE = 13;

  if (window.event.keyCode == ENTER_KEY_CODE) {
    const searchKeyword = document.querySelector(
      ".header__search-content--pc"
    ).value;

    if (!searchKeyword) {
      alert("검색어를 입력해주세요.");
      return;
    }

    window.location.href = `/search/${searchKeyword}`;
  }
}
