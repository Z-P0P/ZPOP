
function getNotification(id){
    return fetch(`/api/notification/${id}`)
}

function readOne(id){
    const readAt = true;
    const data = {
                    method: "POST",
                    headers: {"Content-Type": "application/json"},
                    body: JSON.stringify({
                        id,
                        readAt
                    })
                }
    return fetch(`/api/notification/read/${id}`, data)
}

function readAll(id){
    const readAt = true;
    const data = {
                    method: "POST",
                    headers: {"Content-Type": "application/json"},
                    body: JSON.stringify({
                        id,
                        readAt
                    })
                }
    return fetch(`/api/notification/readAll/${id}`, data)
}

export default {
    getNotification,
    readOne,
    readAll
};