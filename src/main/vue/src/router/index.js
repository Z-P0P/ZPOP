import { createRouter, createWebHistory } from "vue-router";
import MeetingDetail from "@/views/meeting/Detail.vue";

const routes = [
    {
        path: "/meeting/:id(\\d+)", // matches only numbers
        component: MeetingDetail,
        methods: {},
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;