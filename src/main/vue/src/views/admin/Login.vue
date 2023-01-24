<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import adminApi from "@/api/admin";
import { useMemberStore } from "@/stores/memberStore";
import { ServerException } from "@/utils/ServerException";

const router = useRouter();
const memberStore = useMemberStore();

let id = ref("");
let pwd = ref("");

async function onClickLoginBtn() {
  try {
    const res = await adminApi.auth.login({
      name: id.value,
      pwd: pwd.value,
    });
    if (!res.ok) throw new ServerException(await res.json());
    const data = await res.json();
    memberStore.setAdmin(data);
    router.replace("/admin/home");
  } catch (e) {
    if (e.res.status === 401)
      alert("아이디 또는 비밀번호가 일치하지 않습니다.");
  }
}
</script>

<template>
  <div>
    <form action="POST">
      <input v-model="id" type="text" name="id" placeholder="id" />
      <input v-model="pwd" type="password" name="pwd" placeholder="password" />
      <button @click.prevent="onClickLoginBtn">login</button>
    </form>
  </div>
</template>

<style></style>
