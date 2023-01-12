/**
 * 모임 상세 조회
 */
function getDetail(id) {
    return fetch(`/api/meeting/${id}`);
}

export default {
    getDetail,
};