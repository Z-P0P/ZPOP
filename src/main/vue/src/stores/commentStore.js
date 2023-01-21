import { defineStore } from 'pinia';
import api from "@/api";

export const useCommentStore = defineStore('comment',
    {
        state:()=>({
            comment:{
                id: 0,
                replyList:[]
            },
            meetingId:0,
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
            }
        }
    });