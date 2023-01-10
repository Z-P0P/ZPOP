window.addEventListener("load", function(){

    const pagination = document.querySelector(".pagination");
    const selection = pagination.querySelector(".page-selector");

    selection.onclick = function(e){
        if(e.target.nodeName == "A"){
            e.preventDefault();
            console.log(e.target.classList);

            e.target.classList.toggle("selected");
        }

    }

})