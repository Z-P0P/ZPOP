
window.addEventListener("load",
    function () {
        let modalBtn = document.querySelector("#rateBtn");
        let modalUi = document.querySelector(".modal__body");
        modalBtn.onclick = function (e) {
            e.preventDefault();
            const dataId = this.getAttribute("data-id");
            fetch(`http://localhost:8080/member/eval/${dataId}`)
            .then(response => {
                if (response.ok) {
                    return response;
                }
            })
            .then(response => response.json())
            .then(json => {
                let jsonObject = JSON.parse(JSON.stringify(json[0]));
                const title = jsonObject.title;

                for(var i=0; i<json.length; i++){
                    let jsonObject =JSON.parse(JSON.stringify(json[i]))
                    let nickname = jsonObject.nickname;
            
                }

                let template =` <div class="rate-container">
                                  <h1 class="rate__title" >${title}</h1>
                                  <div class="rate__emoji-list">
                                    <div class="rate__emoji">
                                      <span class="icon icon-bad"> bad</span>
                                      <p>인기도 -1</p>
                                    </div>
  
                                      <div class="rate__emoji">
                                        <span class="icon icon-good"> good</span>
                                        <p>인기도 +1</p>
                                      </div>
                        
                                      <div class="rate__emoji">
                                        <span class="icon icon-excellent"> excellent</span>
                                        <p>인기도 +3</p>
                                      </div>
                                  </div>
                              `;

                modalUi.insertAdjacentHTML("beforeend", template);

                for(var i=0; i<json.length; i++){
                    let jsonObject =JSON.parse(JSON.stringify(json[i]))
                    let nickname = jsonObject.nickname;
                    let template2 =`   <div class =rate__slider>
                                          <span>${nickname}</span>
                                        <input type="range" step="50" value="50" min="0" max="100">
                                     </div>`;

                                   modalUi.insertAdjacentHTML("beforeend", template2);

                            }                                 

                let template3 =`     
                <div class ="btn-box">
                  <div class="btn btn-semiround">완료</div>
                </div> </div> </div> </div>`

                modalUi.insertAdjacentHTML("beforeend", template3);

                showModalByButton(e); 

               })

    } }// onclick function 종료
);