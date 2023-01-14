/**
 * 내 프로필 조회, 내 프로필 수정 (사용자 사진, 닉네임 인기도 조회)
 */
function getMyProfile(){
    return fetch(`/api/member`);
}

/**
 * 내가 참여한 모임 조회
 */
function getMyMeeting(){
    return fetch(`/api/myMeeting`);
}

/**
 * 
 * 내가 모집한 모임 조회
 */
function getMyGathering(){
    return fetch(`/api/myGathering`);
}

export default {
    getMyProfile,
    getMyMeeting,
    getMyGathering

};