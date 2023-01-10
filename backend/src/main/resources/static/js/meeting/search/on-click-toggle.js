import state from "./state.js";
import generateUrl from "../list/generate-url.js";
import { insertMeetings, removeMeetings } from "../list/view.js";
import requestMeetings from "../list/request-meetings.js";

export default function (e) {
  state.isToggleOn = e.target.checked;

  const url = generateUrl(state);

  requestMeetings(url).then((meetings) => {
    removeMeetings();
    insertMeetings(meetings);
  });
}
