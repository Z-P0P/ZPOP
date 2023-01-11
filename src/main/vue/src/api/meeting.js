/**
 * 모임 상세 조회
 */
function getThumbnailList(data) {
  const params = new URLSearchParams(data).toString();
  return fetch(`/api/meeting/list?${params}`);
}

export default {
  getThumbnailList,
};
