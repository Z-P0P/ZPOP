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
              
        };
      }
    }

    selectBox.status = "closed";
    selectBox.onclick = () => {
      selectBox.closeOthers();

      if (selectBox.status == "closed") {
        selectBox.classList.add("select-box--expanded");
        selectBox.status = "opened";
      } else {
        selectBox.classList.remove("select-box--expanded");
        selectBox.status = "closed";
      }
    };

    selectBox.closeOthers = function () {
      for (others of selectBoxAll) {
        if (others === this) continue;
        others.classList.remove("select-box--expanded");
        others.status = "closed";
      }
    };
  });
};
