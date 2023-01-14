<script setup>
import Header from "@/components/header/header.vue";
</script>

<template>
  <Header />
  <main>
    <router-view></router-view>
  </main>
</template>

<style>
main {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
}
</style>
