<script setup>
import { ref } from "vue";
import Header from "@/components/header/header.vue";
import ModalError from "../components/modal/ServerError.vue";

let onError = ref(false);

function showErrorModal() {
  onError.value = true;
}

function closeErrorModal() {
  onError.value = false;
}
</script>

<template>
  <Header />
  <main>
    <router-view @throw="showErrorModal"></router-view>
    <ModalError v-if="onError" @closeModal="closeErrorModal"></ModalError>
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
