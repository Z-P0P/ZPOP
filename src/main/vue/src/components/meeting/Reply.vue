<script>
export default {
  name: "Reply",
  props: {
    reply: Object,
  },
};
</script>

<template>
    <div class="profile select-box">
        <span class="profile__image"></span>
        <span class="profile__nickname profile__nickname">{{ reply.nickname }}</span>
        <span class="profile__time">${r.elapsedTime}</span>
        <button></button>
        <div class="modal-select select-box__options comment__kebob z-idx-1">
            <div class="modal-select__contents modal__on-btn" data-id="comment-report" data-modal="#modal-report-comment">답글 신고
                <span class="icon icon-siren-red"></span>
            </div>
            </div>
        </div>
    <div class="reply-container">
        <span class="reply__to">${parentNickname}</span>
        <span class="reply__content">${r.content}</span>
    </div>
        <div class="reply__replies">
        <span class="pointer underline modal__on-btn reply-to-reply" data-modal="#dummy-modal">답글 달기</span> 
    </div>
</template>