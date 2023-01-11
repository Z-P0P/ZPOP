/**
 * 모임 상세 조회
 */
function getDetail(id) {
  return fetch(`${process.env.VUE_APP_API}/api/meeting/${id}`);
}

export default {
  getDetail,
};
