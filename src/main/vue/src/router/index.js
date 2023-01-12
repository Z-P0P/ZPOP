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
  ],
});

export default router;
