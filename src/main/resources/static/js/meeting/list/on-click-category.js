import state from "./state.js";
import requestMeetings from "./request-meetings.js";
import { insertMeetings, removeMeetings } from "./view.js";
import generateUrl from "./generate-url.js";

export default function(e) {
  const categoryId = e.target.dataset.id;
  const selectCategory = state.category;

  if (categoryId == selectCategory) {
    return;
  }

  this[selectCategory].classList.remove("selected");
  e.target.classList.add("selected");

  state.category = categoryId;

  const url = generateUrl(state);

  requestMeetings(url).then((meetings) => {
    removeMeetings();
    insertMeetings(meetings);
  });
}