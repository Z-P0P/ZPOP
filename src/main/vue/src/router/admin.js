import AdminLayout from "@/views/admin/Layout.vue";
import AdminHome from "@/views/admin/Home.vue";
import AdminMeeting from "@/views/admin/meeting/Meeting.vue";
import AdminNotification from "@/views/admin/notification.vue";
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
import { useMemberStore } from "@/stores/memberStore";

export const adminLogin = {
  path: "/admin/login",
  component: AdminLogin,
};

export default {
  path: "/admin/",
  component: AdminLayout,
  beforeEnter: async (to, from) => {
    if (to.path.startsWith("/admin")) {
      const memberStore = useMemberStore();
      if (!(await memberStore.isAdmin())) {
        return "/admin/login";
      }
    }
  },
  children: [
    //   //TODO: 관리자
    { path: "home", component: AdminHome },
    {
      path: "meeting",
      component: AdminMeeting,
      children: [
        { path: "list", component: AdminMeetingList },
        { path: "category", component: AdminCategory },
        { path: "region", component: AdminRegion },
      ],
    },
    {
      path: "member",
      component: AdminMember,
      children: [
        { path: "list", component: AdminMemberList },
        { path: "eval", component: AdminEvaluation },
        { path: "participation", component: AdminParticipation },
      ],
    },
    {
      path: "notification",
      component: AdminNotification,
      children: [
        //       { path: "list", component: AdminHome },
      ],
    },
    {
      path: "report",
      component: AdminReport,
      children: [
        { path: "member", component: AdminMemberReport },
        { path: "meeting", component: AdminMeetingReport },
        { path: "comment", component: AdminCommentReport },
      ],
    },
    {
      path: "comment",
      component: AdminComment,
      children: [{ path: "list", component: AdminCommentList }],
    },
  ],
};
