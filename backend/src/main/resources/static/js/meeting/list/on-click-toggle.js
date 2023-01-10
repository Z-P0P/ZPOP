import state from "./state.js";
import requestMeetings from "./request-meetings.js";
import { insertMeetings, removeMeetings } from "./view.js";
import generateUrl from "./generate-url.js";

export default function (e) {
  state.isToggleOn = e.target.checked;

  const url = generateUrl(state);

  requestMeetings(url).then((meetings) => {
    removeMeetings();
    insertMeetings(meetings);
    if (meetings.length === 0) {
      showResultNone();
    }
  });
}
