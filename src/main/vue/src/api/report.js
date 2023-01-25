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

export default {
    reportUser
};