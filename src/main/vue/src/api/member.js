function getProfile(id){
    return fetch(`/api/member/${id}`)
}


export default {
    getProfile,
};