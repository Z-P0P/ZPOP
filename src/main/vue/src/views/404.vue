<script>
export default {};
</script>

<template>
  <div class="content-wrap">
    <img src="../../public/images/nothing.svg" alt="noting image" />
    <h1>아무것도 없어요!</h1>
    <router-link to="/" class="btn btn-semiround btn-action home">
      홈으로</router-link
    >
  </div>
</template>

<style scoped>
.content-wrap {
  display: flex;
  width: 100%;
  height: 100vh;
  align-items: center;
  justify-content: center;
  flex-direction: column;
}
.content-wrap > h1 {
  margin-top: 1rem;
}

.content-wrap a {
  margin-top: 2rem;
}
</style>
