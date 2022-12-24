import state from "./state.js";
import requestMeetings from "./request-meetings.js";
import { insertMeetings } from "./view.js";
import { generateUrl } from "./util.js";

export default function () {
  const THROTTLING_TIMER = 1300;
  const { scrollHeight, scrollTop, clientHeight } = document.documentElement;

  if (!state.timer && scrollTop + clientHeight > scrollHeight - 5) {
    state.timer = setTimeout(() => {
      state.timer = null;
      
      const isScrollEvent = true;
      const url = generateUrl(isScrollEvent);

      requestMeetings(url).then((meetings) => {
        insertMeetings(meetings);
      });
    }, THROTTLING_TIMER);
  }
};