/**
 * 모달 on
 */
async function showModalByButton(e) {
  e.preventDefault();
  const modal = document.querySelector(e.target.getAttribute("data-modal"));
  if (!modal.classList.contains("hidden")) {
    modal.classList.add("hidden");
    return;
  }
  modal.classList.remove("hidden");
}

/**
 * 모달 off
 */
function hideModalByButton(e) {
  e.preventDefault();
  const modal = document.querySelector(e.target.getAttribute("data-modal"));
  modal.classList.add("hidden");
}

window.addEventListener("load", function () {
  const modalOnBtns = document.querySelectorAll(".modal__on-btn");
  for (onBtn of modalOnBtns) {
    onBtn.onclick = showModalByButton;
  }

  const modalCloseBtns = document.querySelectorAll(".modal__close-btn");
  for (closeBtn of modalCloseBtns) {
    closeBtn.onclick = hideModalByButton;
  }
});
