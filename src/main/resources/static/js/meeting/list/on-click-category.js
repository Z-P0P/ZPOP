import state from "./state.js";

export default function(e) {
  const categoryId = e.target.dataset.id;
  const selectCategory = state.category;

  if (categoryId == selectCategory) {
    return;
  }

  this[selectCategory].classList.remove("selected");
  e.target.classList.add("selected");

  state.category = categoryId;
  // TODO: 재요청
}