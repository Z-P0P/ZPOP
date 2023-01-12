/**
 * 모임 썸네일 리스트 조회
 */
function getThumbnailList(data) {
  const params = new URLSearchParams(data).toString();
  return fetch(`/api/meeting/list?${params}`);
}

/**
 * 모임 상세 조회
 */
function getDetail(id) {
  return fetch(`/api/meeting/${id}`);
}

export default {
  getThumbnailList,
  getDetail,
};