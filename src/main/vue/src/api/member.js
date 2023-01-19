function getProfile(id){
    return fetch(`/api/member/${id}`)
}
    
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
 * 내가 모집한 모임 조회
 */
function getMyGathering(){
    return fetch(`/api/myGathering`);
}

/**
 * 모임 평가를 위한 참여자 가져오기
 */
function getParticipant(meetingId){
    return fetch(`/api/partList/${meetingId}`)
}

/**
 * 모임을 평가하기 
 */
function rateMeeting(){

}

export default {
    getMyProfile,
    getMyMeeting,
    getMyGathering,
    getParticipant,
    getProfile,
};