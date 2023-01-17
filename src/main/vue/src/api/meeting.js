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

async function postRequest(dataJSONStr){
  const url = '/api/meeting';
  const option = {
    method: "POST",
    headers: {"Content-Type": "application/json"},
		body: dataJSONStr,
  }
  return await fetch(url,option);
}

async function getDetailsForUpdate(id){
  const url = `/api/meeting/update/${id}`;
  return await fetch(url);
}

async function putRequest(id, dataJSONStr){
  const url = `/api/meeting/${id}`;
  const option = {
    method: "PUT",
    headers: {"Content-Type": "application/json"},
		body: dataJSONStr,
  }
  return await fetch(url,option);
}


export default {
  getThumbnailList,
  getDetail,
  getActiveOptions,
  postRequest,
  getDetailsForUpdate,
  putRequest,
};