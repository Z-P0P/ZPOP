import { defineStore } from 'pinia';
import api from "@/api";

export const useCommentStore = defineStore('comment',
    {
        state: () => ({
            commentList: [],
            selectModalStatus:{}, //closed 가 true
            meetingId:0
        }),
        actions: {
            getCommentList(meetingId) {
                return api.comment.getCommentList(meetingId)
                    .then(res => {
                        if (res.ok) {
                            return res;
                        }
                        else
                            alert("서버장애로 읽어들일 수 없습니다");
                    })
                    .then(res => res.json())
            },
            addComment(comment) {
                this.commentList.push(comment)
            },
            initSelectModal() {
                this.selectModalStatus = {};
                for (const c of this.commentList) {
                        this.selectModalStatus[c.id] = true;
                }
            },
            openSelectModal(commentId) {
                this.selectModalStatus = {};
                for (const c of this.commentList) {
                    if(c.id == commentId)
                        this.selectModalStatus[c.id] = false;
                    else
                        this.selectModalStatus[c.id] = true;
                }
            },
            closeSelectModal(commentId){
                this.selectModalStatus[commentId] = true;
            },
            async reloadComment(mtStore, id){
                const data = await this.getCommentList(id);
                mtStore.refreshComment(data.resultObject);
                this.initSelectModal();
            },
            setMeetingId(id){
                this.meetingId = id;
            }
        }
    });