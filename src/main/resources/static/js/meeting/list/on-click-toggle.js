import state from "./state.js";
import requestMeetings from "./request-meetings.js";
import { insertMeetings, removeMeetings } from "./view.js";
import { generateUrl } from "./util.js";

export default function (e) {
  state.isToggleOn = e.target.checked;

  const url = generateUrl();

  requestMeetings(url).then((meetings) => {
    removeMeetings();
    insertMeetings(meetings);
  });
}
