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

/*
* 활성화된 모임글 옵션 조회
*/
async function getActiveOptions(){
  const url = '/api/meeting/register';
  return await fetch(url);
}

async function postMeeting(form){
  const url = '/api/meeting';
  const option = {
    method: "POST",
		body: form,
  }
  return response = await fetch(url);
}


export default {
  getThumbnailList,
  getDetail,
  getActiveOptions,
  postMeeting,
};