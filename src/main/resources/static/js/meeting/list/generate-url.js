/**
 * 페이징 요청 url을 리턴
 * @param {object} state 상태 객체
 * @param {boolean} isScrollEvent 스크롤 이벤트 여부
 */
export default function (state, isScrollEvent) {
  const { isToggleOn, regions, category, searchKeyword } = state;
  const meetings = document.querySelector("#meetings");
  const parameter = {};
  let lastMeetingId;

  // 토글 checked === true -> 모집 중인 모임만 보기
  if (isToggleOn) parameter.isClosed = false;

  if (searchKeyword) parameter.keyword = searchKeyword;

  // 지역 옵션이 선택되었다면
  if (state.regions && state.regions.length !== 0) parameter.regions = regions;

  // 특정 카테고리가 선택되었다면
  if (category && category != 0) parameter.category = category;
  
  // 무한 스크롤 이벤트라면
  if (isScrollEvent && meetings.lastElementChild) {
    lastMeetingId = meetings.lastElementChild.dataset.id;
    parameter.start = lastMeetingId;
  }
  
  const searchParams = new URLSearchParams(parameter).toString();

  return `/meeting/api/list?${searchParams}`;
}

