<script setup>
import Header from "../../components/admin/Header.vue";
import {ref} from 'vue';

const currentMenuStatus = ref(0);

const changeHeaderSelected = (menu) => {
  let value;
  switch(menu){
    case "home":
    value = 0;
    break;
    case "notification":
    value = 1;
    break;
    case "meeting":
    value = 2;
    break;
    case "member":
    value = 3;
    break;
    case "comment":
    value = 4;
    break;
    case "report":
    value = 5;
    break;
  }
  currentMenuStatus.value = value;
}


</script>

<template>
    <div class="main-wrapper">
      <main>
        <router-view @menuChanged="changeHeaderSelected"></router-view>
        <Header :status="currentMenuStatus"/>
      </main>
    </div>
</template>

<style>
@import url(../../assets/css/admin/component/status.css);
@import url(../../assets/css/admin/component/button.css);
@import url(../../assets/css/admin/component/admin-list.css);
@import url(../../assets/css/admin/component/table-bar.css);
@import url(../../assets/css/admin/component/select.css);

.main-wrapper{
  margin-top: 30px;
  margin-left:97px;
  margin-right:57px;
  display: flex;
}

main{
  flex-grow: 1;
  margin-left: auto;
  margin-right: auto;
  padding-bottom: 1rem;
}
</style>
