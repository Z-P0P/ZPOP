import { createApp } from "vue";
import { createPinia } from "pinia";
// session 유지될 때 까지 저장하기 위한 pinia 플러그인
import piniaPersist from 'pinia-plugin-persist'

import App from "./App.vue";
import router from "./router";

import "./assets/css/reset.css";
import "./assets/css/style.css";
import "./assets/css/deco.css";
import "./assets/css/icon.css";
import "./assets/css/button.css"


const app = createApp(App);
const pinia = createPinia();
pinia.use(piniaPersist);
app.use(pinia);
app.use(router);

app.mount("#app");
