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
        {
          path: "my-profile",
          component: () => import("@/views/member/Profile.vue"),
        },

        {
          path: "my-profile/edit",
          component: () => import("@/views/member/Edit.vue")
        },
        {
          path: "my-meeting",
          component: () => import("@/views/member/MyMeeting.vue")
        },
        {
          path: "my-gathering",
          component: () => import("@/views/member/MyGathering.vue")
        },
      ],
    },
  ],
});

export default router;
