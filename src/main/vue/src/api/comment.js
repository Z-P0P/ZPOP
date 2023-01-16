/*
답글 리스트 조회
*/
function getReplyList(commentId){
    return fetch(`/api/reply?groupId=${commentId}`)
}

export default {
    getReplyList
}