export default {
  isToggleOn: document.querySelector("#toggle").checked, // 토글 ON OFF 유무
  category: 0, // 카테고리
  timer: null, // 쓰로틀링 타이머
  searchKeyword: null
};