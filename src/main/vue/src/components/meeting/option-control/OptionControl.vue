<script setup>
import Category from "./Category.vue";
</script>

<template>
  <div class="option-control-wrap">
    <section class="option-control">
      <Category @selectCategory="categoryId" />
      <div class="options-detail">
        <!-- <Region/> -->
        <!-- <Toggle/> -->
      </div>
    </section>
  </div>
</template>

<style scoped>
@media (min-width: 768px) {
  .option-control-wrap {
    max-width: 1200px;
  }
  .option-control {
    /* max-width: 1200px; */
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: flex-end;
  }

  .option-control {
    justify-content: center;
    flex-direction: column;
  }
}

@media (min-width: 1200px) {
  .option-control {
  }
}
</style>
