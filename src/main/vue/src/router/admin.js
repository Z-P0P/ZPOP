import AdminLayout from "@/views/admin/Layout.vue";
import AdminHome from "@/views/admin/home/Home.vue";
import AdminMeeting from "@/views/admin/meeting/Meeting.vue";
import AdminBanner from "@/views/admin/banner/Banner.vue";
import AdminReport from "@/views/admin/report/Report.vue";
import AdminMemberReport from "@/views/admin/report/Member.vue";
import AdminMeetingReport from "@/views/admin/report/Meeting.vue";
import AdminCommentReport from "@/views/admin/report/Comment.vue";
import AdminMeetingList from "@/views/admin/meeting/List.vue";
import AdminCategory from "@/views/admin/meeting/Category.vue";
import AdminRegion from "@/views/admin/meeting/Region.vue";
import AdminComment from "@/views/admin/comment/Comment.vue";
import AdminCommentList from "@/views/admin/comment/List.vue";
import AdminMember from "@/views/admin/member/Member.vue";
import AdminMemberList from "@/views/admin/member/List.vue";
import AdminEvaluation from "@/views/admin/member/Evaluation.vue";
import AdminParticipation from "@/views/admin/member/Participation.vue";
import AdminLogin from "@/views/admin/Login.vue";
import AdminBannerList from "@/views/admin/banner/list.vue"

import { useMemberStore } from "@/stores/memberStore";

export const adminLogin = {
  path: "/admin/login",
  component: AdminLogin,
};

export default {
  path: "/admin/",
  component: AdminLayout,
  redirect: '/admin/home/dashboard',
  beforeEnter: async (to, from) => {
    if (to.path.startsWith("/admin")) {
      const memberStore = useMemberStore();
      if (!(await memberStore.isAdmin())) {
        return "/admin/login";
      }
    }
  },
  children: [
    //
    { 
      path: "home", 
      component: null,
      redirect: '/admin/home/dashboard',
      children: [
        { path: "dashboard", component: AdminHome},
      ],
    },
    {
      path: "meeting",
      component: AdminMeeting,
      redirect: '/admin/meeting/list',
      children: [
        { path: "list", component: AdminMeetingList},
        { path: "category", component: AdminCategory },
        { path: "region", component: AdminRegion },
      ],
    },
    {
      path: "member",
      component: AdminMember,
      redirect: '/admin/member/list',
      children: [
        { path: "list", component: AdminMemberList },
        { path: "eval", component: AdminEvaluation },
        { path: "participation", component: AdminParticipation },
      ],
    },
    {
      path: "banner",
      component: AdminBanner,
      redirect: '/admin/banner/list',
      children: [
              { path: "list", component: AdminBannerList },
      ],
    },
    {
      path: "report",
      component: AdminReport,
      redirect: '/admin/report/member',
      children: [
        { path: "member", component: AdminMemberReport },
        { path: "meeting", component: AdminMeetingReport },
        { path: "comment", component: AdminCommentReport },
      ],
    },
    {
      path: "comment",
      component: AdminComment,
      redirect: '/admin/comment/list',
      children: [{ path: "list", component: AdminCommentList }],
    },
  ],
};
