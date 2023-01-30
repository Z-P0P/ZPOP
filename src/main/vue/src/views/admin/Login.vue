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
    router.push("/admin/home");
  } catch (e) {
    if (e.res.status === 401)
      alert("아이디 또는 비밀번호가 일치하지 않습니다.");
  }
}
</script>

<template>
    <form class="login" action="POST">
      <div class="login__title">Login</div>
      <div class="input-text__content-wrapper add-deco-img-left deco-img-user">
        <input class="input-text__content" v-model="id" type="text" name="id" placeholder="ID" />
      </div>
      <div class="input-text__content-wrapper add-deco-img-left deco-img-lock">
        <input class="input-text__content" v-model="pwd" type="password" name="pwd" placeholder="Password" />
      </div>
      <button class="login__btn" @click.prevent="onClickLoginBtn">Login</button>
    </form>
</template>

<style scoped>
@import url('../../assets/css/form.css');

.login{
  width: 400px;
  height: 196px;
  margin: 0 auto;
  margin-top:275px;
  flex-direction: column;
  display:flex;
  justify-content: center;
}

.login__title{
  width:175px;
  height:80px;
  font-size:44px;
  font-weight:700;
  padding:13.5px 30px;
  color:var(--dark-grey2);
  border-bottom: 5px solid var(--admin-loyal-blue);
  margin-bottom:28px;
}

.input-text__content-wrapper{
  flex: 0 0 46px;
}

.input-text__content{
  width:100%;
  position:absolute;
  padding-left:50px;
  border:none;
  border-bottom: 2px solid var(--admin-loyal-blue);
  border-radius: 0;
}

.input-text__content::placeholder{
  color:var(--admin-grey);
}

.login__btn{
  font-weight:700;
  margin-top: 20px;
  align-self: end;
  cursor: pointer;
}

.add-deco-img-left::before{
  width:2rem;
  height:2rem;
  flex: 0 0 2rem;
}

.deco-img-lock::before{
  width:1.5rem;
  height:1.5rem;
}
</style>
