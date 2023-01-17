<script setup>
  import { setMapStoreSuffix } from 'pinia';
  import api from "@/api";
  import { reactive,ref } from 'vue';
  import { useRoute } from "vue-router";
  import Reply from "./Reply.vue";
  import { defineProps } from 'vue';
  import Comment from './Comment.vue';

  const props = defineProps({
    detail:Object
  });

  
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
					<Comment :comment="comment"/>
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