window.onload = () => {
  const selectBoxAll = document.querySelectorAll(".select-box");

  selectBoxAll.forEach((selectBox) => {
    const selectBoxInput = selectBox.querySelector(".select-box > input");
    if (selectBoxInput != null) {
      const selectBoxOptions =
        selectBoxInput.nextElementSibling.querySelectorAll("li");
      for (let option of selectBoxOptions) {
        option.onclick = () => {
          selectBoxInput.value = option.innerText;
          selectBoxInput.dataset.id = option.dataset.id;
              console.log(selectBoxInput.dataset.id);
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
};
