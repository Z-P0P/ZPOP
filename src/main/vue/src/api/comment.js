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
/*
답글 등록
*/
async function registerReply(dataJSONStr){
        const url = '/api/reply';
        const option = {
          method: "POST",
          headers: {"Content-Type": "application/json"},
              body: dataJSONStr,
        }
        return await fetch(url,option);
      }
/*
댓글 삭제
*/
async function deleteComment(commentId){
        const option = {
          method: "DELETE",
          headers: {"Content-Type": "application/json"},
              body: "",
        }
        return await fetch(`/api/comment/${commentId}`,option);
      }

export default {
    getReplyList,
    registerComment,
    registerReply,
    deleteComment
}