import { defineStore } from "pinia";

/**
 * 모임 상세 조회
 */
export const useMeetingDetailStore = defineStore("meetingDetail", {
  state: () => ({
    id: null,
    regMemberId: null,
    title: "",
    content: "",
    ageRange: "",
    categoryName: "",
    genderCategory: "",
    regionName: "",
    detailRegion: "",
    maxMember: 0,
    startedAt: "",
    textStartedAt: "",
    viewCount: 0,
    closed: null,
    participants: [],
    commentCount: 0,
    comments: [],
    myMeeting: false,
    hasParticipated: false,
  }),
  actions: {
    init(data) {
      this.id = data.id || null;
      this.regMemberId = data.regMemberId || null;
      this.title = data.title || "";
      this.content = data.content || "";
      this.ageRange = data.ageRange || "";
      this.categoryName = data.categoryName || "";
      this.genderCategory = data.genderCategory || "";
      this.regionName = data.regionName || "";
      this.detailRegion = data.detailRegion || "";
      this.maxMember = data.maxMember || 0;
      this.startedAt = data.startedAt || "";
      this.textStartedAt = data.textStartedAt || "";
      this.viewCount = data.viewCount || 0;
      this.closed = data.closed || null;
      this.participants = data.participants || [];
      this.commentCount = data.commentCount || 0;
      this.comments = data.comments || [];
      this.myMeeting = data.myMeeting || false;
      this.hasParticipated = data.hasParticipated || false;
    },
    increaseCommentCount() {
      this.commentCount++;
    },
    removeParticipant(removeTargetId) {
      this.participants = this.participants.filter(
        (p) => p.participantId !== removeTargetId
      );
    },
  },
});
