function reportUser(memberId,memberReportType,memberReportReason){

    const data = {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            reportType : memberReportType,
            reportReason : memberReportReason
        })
    }
    return fetch(`/api/report/member/${memberId}`, data)
}

function reportMeeting(reportedMeetingId, meetingReportType, meetingReportReason){
    const meetingData = {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            reportType : meetingReportType,
            reportReason : meetingReportReason
        })
    }
    return fetch(`/api/report/meeting/${reportedMeetingId}`, meetingData)
}

function reportComment(reportedCommentId, commentReportType, commentReportReason){
    const data = {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            reportType : commentReportType,
            reportReason : commentReportReason
        })
    }
    return fetch(`/api/report/comment/${reportedCommentId}`, data)
}



export default {
    reportUser,
    reportMeeting,
    reportComment
};