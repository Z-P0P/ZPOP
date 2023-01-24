<script setup>
import { ref } from "vue";
import Header from "@/components/header/header.vue";
import LoginModal from '@/components/modal/LoginModal.vue';
import ModalError from "@/components/modal/ServerError.vue";

let onError = ref(false);

const isLoginOpened = ref(false);
const loginModalHandler = () => {
  isLoginOpened.value = !isLoginOpened.value;
}

function showErrorModal() {
  onError.value = true;
}

function closeErrorModal() {
  onError.value = false;
}


</script>

<template>
  <Header/>
  <main>
    <router-view @throw="showErrorModal"></router-view>
    <LoginModal/>
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
