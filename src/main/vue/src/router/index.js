import { createRouter, createWebHistory } from "vue-router";
import adminRoute from "./admin";
import { useMemberStore } from "../stores/memberStore";

function isAuth (to, from) {
  const memberStore = useMemberStore();
  if(!memberStore.memberInfo.id || !memberStore.memberInfo.nickname) {
    return "/"
  }
}

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
              name: "meetingList",
              component: () => import("@/views/meeting/List.vue"),
              alias: "/",
            },
            {
              path: "search",
              component: () => import("@/views/meeting/Search.vue"),
              alias: "/search",
            },
            {
              name: "meetingDetail",
              path: ":id(\\d+)", // matches only numbers
              component: () => import("@/views/meeting/Detail.vue"),
            },
            {
              name: "registerMeeting",
              path: "register",
              component: () => import("@/views/meeting/Register.vue"),
              beforeEnter: [isAuth],
            },
            {
              name: "updateMeeting",
              path: "update/:id(\\d+)",
              component: () => import("@/views/meeting/Update.vue"),
              beforeEnter: [isAuth],
            },
          ],
        },
        {
          path: "my-profile",
          component: () => import("@/views/member/Profile.vue"),
          beforeEnter: [isAuth],
        },

        {
          path: "my-profile/edit",
          component: () => import("@/views/member/Edit.vue"),
          beforeEnter: [isAuth],
        },
        {
          path: "my-meeting",
          component: () => import("@/views/member/MyMeeting.vue"),
          beforeEnter: [isAuth],
        },
        {
          path: "my-gathering",
          component: () => import("@/views/member/MyGathering.vue"),
          beforeEnter: [isAuth],
        },
      ],
    },
  ],
});

export default router;
