<script setup>
import { setMapStoreSuffix } from 'pinia';
import api from "@/api";
import { reactive } from 'vue';
import { useRoute } from "vue-router";
import Reply from "./Reply.vue";
import { defineProps } from 'vue';
const props = defineProps({
  detail:Object
});

var reply = null;
console.log(reply)
  function showReply(comment){
    reply=  getReplyList(comment.id);
  }
    function toggle(e){
      if(e.target.id){
        const id = e.target.id;
        const el = document.querySelector('[data-id='+id+']');
        el.classList.remove("hidden");
        e.target.classList.add("hidden");
        console.log(id.substr(3))
        showReply();
      }
      else {
        const id = e.target.dataset.id;
        const el = document.getElementById(id);
        el.classList.remove("hidden");
        e.target.classList.add("hidden");
      }  
    }
    function hasReply (comment){
      let hasReply = false;
      if(comment.countOfReply>0)
      hasReply = true;
      return hasReply;
    }
    function getReplyList (commentId){
        let arr = [];
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
              arr.push(r);
            }
          })
          return arr;
    }

</script>

<template>
   <section class="comment">
        <h2 class="comment__num">댓글 {{ detail.commentCount }} 개</h2>
        <div class="comment__input-container">
				<textarea class="comment__input" name="comment-text"
                          id="comment-text" placeholder="댓글을 입력하세요."></textarea>
            <div class="comment__btn-container">
                <span class="reply__btn btn btn-round btn-cancel cancel-btn hidden">취소하기</span>
                <span class="comment__btn btn btn-round btn-action modal__on-btn" id="register-btn" data-modal="#dummy-modal">등록하기</span>
                <span class="hidden comment__btn btn btn-round btn-action" id="edit-save-btn">저장하기</span>
            </div>
        </div>
			<ul class="comment__list">
				<li v-for="(comment, index) in detail.comments" :key="index"> 
					<div class="profile select-box">
						<span class="profile__image"></span>
						<span class="profile__nickname">{{ comment.nickname }}</span>
						<span class="profile__time" th:text="${comment.elapsedTime}">{{comment.elapsedTime}}</span>
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
						<span class="pointer underline reply-cnt"  @click="[toggle,showReply(comment)]"  v-if="hasReply(comment)" :id="'id-'+index">답글 {{ comment.countOfReply }}개</span>
            <span class="pointer reply-close hidden" @click="toggle"  v-if="hasReply(comment)" :data-id="'id-'+index">닫기</span>
            <span class="pointer underline reply-write modal__on-btn" data-modal="#dummy-modal">답글 달기</span>
          </div>
            <section class="reply">
              <Reply :replies="reply"/> 
            </section>
        </li>
      </ul>
            <div class="hidden" id="dummy-modal"></div>
    </section>
</template>

<style scoped>
 @import url(@/assets/css/meeting/component/select.css);
 @import url(@/assets/css/meeting/component/profile-box.css);
 @import url(@/assets/css/meeting/component/comment.css);

</style>