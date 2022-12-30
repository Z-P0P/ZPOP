window.onload = () => {
  initSelectBoxes();
};

function initSelectBoxes(){
	const selectBoxAll = document.querySelectorAll(".select-box");

  selectBoxAll.forEach((selectBox) => {
    const selectBoxSpan = selectBox.querySelector(".select-box > span");
    if (selectBoxSpan != null) {
      const selectBoxOptions =
        selectBoxSpan.nextElementSibling.querySelectorAll("li");
      for (let option of selectBoxOptions) {
        option.onclick = () => {
          selectBoxSpan.innerText = option.innerText;
          selectBoxSpan.parentElement.querySelector("input").value = option.dataset.id;
        };
      }
    }

    selectBox.status = "closed";
    selectBox.onclick = () => {
      selectBox.closeOthers();

      if (selectBox.status == "closed") {
        selectBox.classList.add("select-box--expended");
        selectBox.status = "opened";
      } else {
        selectBox.classList.remove("select-box--expended");
        selectBox.status = "closed";
      }
    };

    selectBox.closeOthers = function () {
      for (others of selectBoxAll) {
        if (others === this) continue;
        others.classList.remove("select-box--expended");
        others.status = "closed";
      }
    };
  });
}