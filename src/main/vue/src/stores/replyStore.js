import { defineStore } from 'pinia';
import api from "@/api";

export const useReplyStore = defineStore('reply',
    {
        state:()=>({
                comments:{}, 
        }),
        actions:{
            getReplyList (commentId){
               return api.comment.getReplyList(commentId)
                    .then(res=>{
                    if(res.ok)  {
                        return res;
                    }
                    else 
                        alert("서버장애로 읽어들일 수 없습니다");
                    })
                    .then(res=>res.json())
            },
            addComment(comment) {
                comment['replyList'] = [];
                comment['selectModalStatus'] = {};
                this.comments[comment.id] = comment;
            },
            initSelectModal(commentId) {
                this.comments[commentId].selectModalStatus={};
                for (const r of this.comments[commentId].replyList) {
                    this.comments[commentId].selectModalStatus[r.id] = true;
                }
            },
            openSelectModal(replyId, commentId) {
                this.comments[commentId].selectModalStatus={};
                for (const r of this.comments[commentId].replyList) {
                    if(r.id == replyId)
                    this.comments[commentId].selectModalStatus[r.id] = false;
                    else
                    this.comments[commentId].selectModalStatus[r.id] = true;
                }
            },
            closeSelectModal(replyId, commentId){
                this.comments[commentId].selectModalStatus[replyId] = true;
            },
            async fillRepliesToComment(commentId){
                const data = await this.getReplyList(commentId);
                for(const r of data.resultObject)
                    this.comments[commentId].replyList.push(r);
            },
            reloadReply(commentId){
                this.comments[commentId].replyList.length = 0;
                this.fillRepliesToComment(commentId);
            }
        }
    });