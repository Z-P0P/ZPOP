<template>

  <article>
    <header>
      <div class="category-wrap">
        <span class="category">{{ article.category}}</span>
      </div>
      <div class="title-container">
        <h2 class="title">{{ article.title }}</h2>
        <img src="" alt="" />
      </div>

      <div class="start-datetime-container">
        <img src="" alt="" />
        <span>{{ article.startedAt }}</span>
      </div>
      <div class="region-container">
        <img src="" alt="" />
        <span>{{ article.region }}</span>
      </div>
      <div class="region-detail-wrap">
        <span>{{ article.detailRegion }}</span>
      </div>
    </header>

    <div class="content-container">
      <span> {{ article.content }}</span>
      <ul class="tags">
        <li>#{{ article.ageRange }}</li>
        <li>#{{ article.maxMember }}명</li>
        <li>#{{ article.genderCategory }}</li>
      </ul>
      <div class="views">{{ article.viewCount }}</div>
      <!-- TODO: 버튼 컴포넌트화 -->
      <div class="control-btn-wrap">
        <span>참여하기</span>
      </div>
    </div>
  </article>

</template>

<script>
export default {
  name: "MeetingArticle",
  //props 받는다.. 
  props: {
    article: Object,
  },
};
</script>

<style scoped>
  @import url(@/assets/css/meeting/article.css);
</style>