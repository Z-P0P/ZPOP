async function getDetailsForUpdate(id){
    const url = `/api/admin/meeting/${id}`;
    return await fetch(url);
}

// getDeleted가 true이면 삭제, false이면 삭제 취소
async function deleteMeeting(id, getDeleted){
    const url = `/api/admin/meeting/${id}?getDeleted=${getDeleted}`;
    const option = {
        method:'DELETE'
    }
    return await fetch(url, option);
}

// getDeleted가 true이면 삭제, false이면 삭제 취소
async function deleteAllMeeting(ids, getDeleted){
    const url = `/api/admin/meeting?ids=${ids}&getDeleted=${getDeleted}`;
    const option = {
        method:'DELETE'
    }
    return await fetch(url, option);
}

export default{
    getDetailsForUpdate,
    deleteMeeting,
    deleteAllMeeting,
}