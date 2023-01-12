<script setup>
import { RouterLink, RouterView } from "vue-router";
import Detail from "./views/meeting/Detail.vue";
import Article from "./components/meeting/Article.vue";
import Header from './components/header/header.vue'
import Buttons from "./components/Buttons.vue";
</script>

<template>
  <Header/>
  <router-view/>
</template>
