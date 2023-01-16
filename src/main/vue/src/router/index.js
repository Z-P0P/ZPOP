import { createRouter, createWebHistory } from "vue-router";
import adminRoute from "./admin";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    adminRoute,
    {
      path: "/",
      component: () => import("@/views/Layout.vue"),
      children: [
        {
          path: "/:pathMatch(.*)",
          component: () => import("@/views/404.vue"),
        },
        {
          path: "login/oauth/:pathMatch(.*)*",
          component: () => import("@/views/meeting/Login.vue"),
        },
        {
          path: "meeting",
          children: [
            {
              path: "list",
              component: () => import("@/views/meeting/List.vue"),
              alias: "/",
            },
            {
              path: "search",
              component: () => import("@/views/meeting/Search.vue"),
              alias: "/search",
            },
            {
              path: ":id(\\d+)", // matches only numbers
              component: () => import("@/views/meeting/Detail.vue"),
            },
            {
              path: "register",
              component: () => import("@/views/meeting/Register.vue"),
            },
            {
              path: "update/:id(\\d+)",
              component: () => import("@/views/meeting/Update.vue"),
            },
          ],
        },
      ],
    },
  ],
});

export default router;
