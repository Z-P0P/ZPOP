import api from '@/api/admin';
import { reactive } from 'vue';
import UpdateForm from '../../updateForm';


export default class AdminUpdateForm extends UpdateForm{

    constructor(meetingId){
        super(meetingId);
        this.participants = reactive([]);
        this.regMemberId = 0;
        this.regMemberNickname ="",
        this.regMemberProfile = null,
        this.createdAt = null;
        this.updatedAt = null;
        this.deletedAt = null;
        this.closedAt = null;
        this.closed = null;
        this.deleted = null;
        this.viewCount = 0;
        this.commentCount = 0;
    }

    getRegisteredMeetingDetails(){
        const request = api.meeting.getDetailsForUpdate(this.meetingId);
        request.then(res=>res.json())
        .then(data => {
            const options = data.options;
            const details = data.details;
            const participants = data.participants;
            super.updateOptionsAfterRequest(options);
            super.updateDetails(details);
            this.updateParticipatns(participants);
            this.regMemberId = details.regMemberId;
            this.regMemberNickname =details.regMemberNickname,
            this.regMemberProfile = details.regMemberProfile,
            this.createdAt = details.createdAt;
            this.updatedAt = details.updatedAt;
            this.deletedAt = details.deletedAt;
            this.viewCount = details.viewCount;
            this.commentCount = details.commentCount;
            this.closedAt = details.closedAt;
            this.closed = details.closed;
            this.deleted = details.deleted;
            this.isLoaded = true;
        })
    }
    updateParticipatns(participants){
        this.participants = participants;
        this.maxMember = this.inputs.maxMembers.currentValue;
    }

    deleteMeeting(){
        if(!confirm(`정말로 글을 삭제하시겠습니까?`)){
            return;
        }
        const request = api.meeting.deleteMeeting(this.meetingId, true);
        request.then(res=>res.json())
        .then(data=>{
            alert('모임글이 정상적으로 삭제되었습니다.\n삭제일시: ' + data);
            this.isLoaded = false;
        })
        .catch(err=>{
            alert('모임글 상태 변경에 오류가 발생했습니다.' + err);
        })
    }

    cancelDeleteMeeting(){
        if(!confirm(`정말로 글을 삭제취소하시겠습니까?`)){
            return;
        }
        const request = api.meeting.deleteMeeting(this.meetingId, false);
        request.then(res=>{
            if(res.ok){
                alert('모임글이 정상적으로 삭제취소되었습니다.');
                this.isLoaded = false;
            }
        })
        .catch(err=>{
            alert('모임글 상태 변경에 오류가 발생했습니다.' + err);
        })
    }
    deleteAllMeeting(ids){
        console.log(ids);
        if(!confirm(`정말로 글을 삭제하시겠습니까?\n글 번호:${ids}`)){
            return;
        }
        const request = api.meeting.deleteAllMeeting(ids, true);
        request.then(res=>res.json())
        .then(data=>{
            alert('모임글이 정상적으로 삭제되었습니다.');
            this.isLoaded = false;
        })
        .catch(err=>{
            alert('모임글 상태 변경에 오류가 발생했습니다.' + err);
        })
    }
    cancelDeleteAllMeeting(ids){
        if(!confirm(`정말로 글을 삭제취소하시겠습니까?\n글 번호:${ids}`)){
            return;
        }
        const request = api.meeting.deleteAllMeeting(ids, false);
        request.then(res=>res.json())
        .then((data)=>{
            alert('모임글이 정상적으로 삭제취소되었습니다.');
            this.isLoaded = false;
        })
        .catch(err=>{
            alert('모임글 상태 변경에 오류가 발생했습니다.' + err);
        })
    }
}