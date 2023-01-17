<script setup>
  import api from "@/api";
  import { reactive,ref } from 'vue';
  import { useRoute } from "vue-router";
  import Reply from "./Reply.vue";
  import { defineProps } from 'vue';
  const props = defineProps({
    comment:Object
  });

  const reply = reactive([]);

  const toggle = (e)=>{
    if(e.target.id){
      const id = e.target.id;
      const el = document.querySelector('[data-id='+id+']');
      el.classList.remove("hidden");
      e.target.classList.add("hidden");
      getReplyList(id.substr(3));
    }
    else {
      const id = e.target.dataset.id;
      const el = document.getElementById(id);
      el.classList.remove("hidden");
      e.target.classList.add("hidden");
      reply.length = 0;
    }  
  }
  function hasReply (comment){
    let hasReply = false;
    if(comment.countOfReply>0)
    hasReply = true;
    return hasReply;
  }
  function getReplyList (commentId){
      const route = useRoute();
      fetch(`/api/reply?groupId=${commentId}`)
        .then(res=>{
          if(res.ok)  {
            console.log('답글을 불러왔습니다')
            return res;
          }
          else 
            alert("서버장애로 읽어들일 수 없습니다");
        })
        .then(res=>res.json())
        .then(data=>{
          for(const r of data.resultObject) { 
            reply.push(r);
          }
        })
  }

</script>

<template>
    <div class="profile select-box">
            <span class="profile__image"></span>
            <span class="profile__nickname">{{ comment.nickname }}</span>
            <span class="profile__time">{{comment.elapsedTime}}</span>
            <button></button>
            <th:block th:if="${comment.isMyComment}">
                    <div sec:authorize="isAuthenticated()" th:replace="~{inc/meeting-detail-modal::writer}"></div>
            </th:block>
            <th:block th:if="${!comment.isMyComment}">
                    <div sec:authorize="isAuthenticated()" th:replace="~{inc/meeting-detail-modal::reader}"></div>
            </th:block>
          </div>
          <span class="comment__content">{{ comment.content }}</span>
          <div class="comment__replies">
						<span class="pointer underline reply-cnt"  @click="toggle"  v-if="hasReply(comment)" :id="'id-'+comment.id">답글 {{ comment.countOfReply }}개</span>
            <span class="pointer reply-close hidden" @click="toggle"  v-if="hasReply(comment)" :data-id="'id-'+comment.id">닫기</span>
            <span class="pointer underline reply-write modal__on-btn" data-modal="#dummy-modal">답글 달기</span>
          </div>
            <section class="reply">
              <Reply :replies="reply"/> 
            </section>
</template>
<style scoped>
 @import url(@/assets/css/meeting/component/select.css);
 @import url(@/assets/css/meeting/component/profile-box.css);
 @import url(@/assets/css/meeting/component/comment.css);

</style>