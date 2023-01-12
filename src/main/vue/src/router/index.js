import { createRouter, createWebHistory } from "vue-router";
import adminRoute from "./admin";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      component: () => import("@/views/meeting/List.vue"),
    },
    adminRoute,
    { path: "/login/oauth/:pathMatch(.*)*", component: () => import ("@/views/meeting/Login.vue")},
    { path: '/meeting/register', component: () => import("@/views/meeting/Register.vue")},
    { path: '/meeting/update/:id(\\d+)', component: () => import("@/views/meeting/Update.vue")},
  ],
});

export default router;
