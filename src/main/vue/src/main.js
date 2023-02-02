import { createApp } from "vue";
import { createPinia } from "pinia";

import App from "./App.vue";
import router from "./router";

import "./assets/css/reset.css";
import "./assets/css/style.css";
import "./assets/css/deco.css";
import "./assets/css/icon.css";
import "./assets/css/button.css";
import "./assets/css/component/tooltip.css";
import "./assets/css/component/select.css";


const app = createApp(App);
const pinia = createPinia();
app.use(pinia);
app.use(router);

app.mount("#app");
