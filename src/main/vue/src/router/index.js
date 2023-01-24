import { createRouter, createWebHistory } from "vue-router";
import adminRoute, { adminLogin } from "./admin";
import { useMemberStore } from "@/stores/memberStore";
import { useLoginModalStore } from "@/stores/loginModalStore";

async function isAuth(to, from) {
  const memberStore = useMemberStore();
  const loginModalStore = useLoginModalStore();

  // 로그인 안한상태로 페이지 이동일 경우,
  // 요청 페이지를 로컬스토리지에 저장한다. 그 후 로그인 모달을 띄운다.
  if (!(await memberStore.isAuthenticated())) {
    localStorage.setItem("redirect-route", to.path);
    loginModalStore.show();
    return "/";
  }
}

// function redirectRouteAfterLogin(to, from) {
//   // 로컬 스토리이제 저장된 리다이렉트 라우트가 있다면,
//   // 로그인 성공 후 해당 라우트로 이동한다.
//   const redirectRoute = localStorage.getItem("redirect-route");
//   if (from.path.includes("?login=") && redirectRoute) {
//     localStorage.removeItem("redirect-route");
//     router.push(redirectRoute);
//   }
// }

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    adminLogin,
    adminRoute,
    {
      path: "/",
      component: () => import("@/views/Layout.vue"),
      children: [
        {
          path: "/:pathMatch(.*)",
          component: () => import("@/views/404.vue"),
        },
        // {
        //   path: "login/oauth/:pathMatch(.*)*",
        //   component: () => import("@/views/LoginProc.vue"),
        // },
        {
          path: "meeting",
          children: [
            {
              path: "list",
              name: "meetingList",
              component: () => import("@/views/meeting/List.vue"),
              alias: "/",
              // beforeEnter: [redirectRouteAfterLogin],
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
