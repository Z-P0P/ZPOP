import { createRouter, createWebHistory } from "vue-router";
import adminRoute from "./admin";
import { useMemberStore } from "../stores/memberStore";

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
              name: 'meetingList',
              component: () => import("@/views/meeting/List.vue"),
              alias: "/",
            },
            {
              path: "search",
              component: () => import("@/views/meeting/Search.vue"),
              alias: "/search",
            },
            {
              name: 'meetingDetail',
              path: ":id(\\d+)", // matches only numbers
              component: () => import("@/views/meeting/Detail.vue"),
            },
            { 
              name: "registerMeeting",
              path: "register",
              component: () => import("@/views/meeting/Register.vue"),
            },
            {
              name: "updateMeeting",
              path: "update/:id(\\d+)",
              component: () => import("@/views/meeting/Update.vue"),
            },
          ],
        },
      ],
    },
  ],
});

router.beforeEach((to, from, next) =>{
  const store = useMemberStore();
  const memberInfo = store.memberInfo;
  
  // if ((to.name ==='registerMeeting' || to.name ==='updateMeeting') 
  //       && memberInfo.nickname === null){
  //   next({path: '/', params: {login : false}});
  // }

  next();
})

export default router;
