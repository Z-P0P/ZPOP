<template>

  <article>
    <header>
      <div class="category-wrap">
        <span class="category">{{ article.categoryName}}</span>
      </div>
      <div class="title-container">
        <h2 class="title">{{ article.title }}</h2>
        <span class="kebab icon icon-kebab"></span>
        <img src="" alt="" />
      </div>

      <div class="start-datetime-container">
        <span class="icon icon-calender16"></span>
        <span>{{ article.textStartedAt }}</span>
      </div>
      <div class="region-container">
        <span class="icon-location16"></span>
       
        <span>{{ article.region }}</span>
        <span>{{ article.regionName }}</span>
      </div>
      <div class="region-detail-wrap">
        <span>{{ article.detailRegion }}</span>
      </div>
    </header>

    <div class="content-container">
      <span> {{ article.content }}</span>
      <ul class="tags">
        <li>#{{ article.ageRange }} 선호</li>
        <li>#{{ article.maxMember }} 명</li>
        <li>#{{ article.genderCategory }}</li>
      </ul>
      <div class="views">조회수 {{ article.viewCount }}회</div>
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

<style >
  @import url(../../assets/css/meeting/article.css);
  ::article {
    background-color: antiquewhite;
  }
</style>