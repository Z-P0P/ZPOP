/*
답글 리스트 조회
*/
function getReplyList(commentId){
    return fetch(`/api/reply?groupId=${commentId}`)
}
/*
댓글 등록
*/
async function registerComment(dataJSONStr){
        const url = '/api/comment';
        const option = {
          method: "POST",
          headers: {"Content-Type": "application/json"},
              body: dataJSONStr,
        }
        return await fetch(url,option);
      }

export default {
    getReplyList,
    registerComment
}