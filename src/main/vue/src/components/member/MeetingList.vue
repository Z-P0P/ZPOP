<script>
export default {
  name: "MeetingThumbnail",
  props: {
    meeting: Object,
  },
};
</script>

<template>


  <div class="meeting">
    <router-link :to="`/meeting/${meeting.id}`">
      <div class="meeting__header">
        <div class="meeting__header-detail">
          <span class="meeting__category">{{ meeting.category}}</span>
          <span class="meeting__region add-deco-img-left deco-img-location">
            {{ meeting.region }}</span
          >
          <span class="meeting__date">{{ meeting.startedAt }}</span>
        </div>
        <h3>{{ meeting.title }}</h3>
      </div>
      <div class="meeting__body">
        <ul class="meeting__tags">
          <li>#{{ meeting.ageRange }}</li>
          <li>#{{ meeting.maxMember }}명</li>
          <li>#{{ meeting.genderCategory }}</li>
        </ul>
        <div class="meeting__body-detail">
          <div class="status status__on" v-if="!meeting.isClosed">모집중</div>
          <div class="status status__off" v-else>모집완료</div>
          <div class="meeting__body-detail-more">
            <div class="meeting__views add-deco-img-left deco-img-eyes">
              {{ meeting.viewCount }}
            </div>
            <div
              class="meeting__comments add-deco-img-left deco-img-speech-bubble"
            >
              {{ meeting.commentCount }}
            </div>
          </div>
        </div>
      </div>
    </router-link>
  </div>
<!-- 
    <section class="main__content">
    <div class="title-container">
      <span>🤝🏻</span>
      <h3 class="my-meeting__title">내가 참여한 모임</h3>
    </div>
    <ul id="meetings" class="meetings">
      <li  >
        <a class="meeting"  >
          <div class="meeting__header">
            <div class="meeting__info-detail-more">
                  <span class="meeting__category"
                  >카테고리</span
                  >
              <span
                      class="meeting__region add-deco-img-left deco-img-location"
                      
              >지역</span
              >
              <span class="meeting__date" 
              >11.01(일)</span
              >
            </div>
            <h3 class="meeting__title" >제목</h3>
          </div>




          <div class="meeting__info" >

            <ul class="meeting__tags">
              <li >연령대</li>
              <li >인원</li>
              <li >성별</li>
            </ul>
            <div class="meeting__info-detail">
              <div
                      class="meeting__status meeting__status--on"
                      
              >
                모집중
              </div>
              <div
                      class="meeting__status meeting__status--off"
                     
              >
                모집완료
              </div>
              <div class="meeting__info-detail-more">
                <div
                        class="meeting__views add-deco-img-left deco-img-eyes"
                      
                >
                  조회수
                </div>
                <div
                        class="meeting__comments add-deco-img-left deco-img-speech-bubble"
                        
                >
                  댓글수
                </div>
              </div>
            </div>
          </div>
            <div class="meeting__info-rate" >
              <div class="modal__on-btn btn btn-semiround rate" data-modal="#modal-eval-notification">평가하기</div>
              </div>
        </a>
   
      </li>
    </ul>
  </section> -->
</template>

<style scoped>
 @import url(../../assets/css/member/meeting-list.css);
</style>