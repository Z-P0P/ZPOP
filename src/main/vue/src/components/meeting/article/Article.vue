<template>
  <article>
    <header>
      <div class="category-wrap">
        <span class="category">{{ article.categoryName }}</span>
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
      <span v-html="article.content"></span>
      <ul class="tags">
        <li>#{{ article.ageRange }} 선호</li>
        <li>#{{ article.maxMember }} 명</li>
        <li>#{{ article.genderCategory }}</li>
      </ul>
      <div class="views">조회수 {{ article.viewCount }}회</div>
      <div class="control-btn-wrap">
        <RoundDisabled v-if="article.closed">
          <template #content> 모집완료 </template>
        </RoundDisabled>
        <Round v-else-if="article.myMeeting">
          <template #content> 마감하기 </template>
        </Round>
        <Round v-else-if="article.hasParticipated">
          <template #content> 참여취소 </template>
        </Round>
        <Round v-else @click.prevent="">
          <template #content> 참여하기 </template>
        </Round>
      </div>
    </div>
  </article>
</template>

<script setup>
import { defineProps } from "vue";
import Round from "@/components/button/Round.vue";
import RoundDisabled from "@/components/button/RoundDisabled.vue";

const props = defineProps({
  article: Object,
});
</script>

<style>
@import url(@/assets/css/meeting/article.css);
</style>
