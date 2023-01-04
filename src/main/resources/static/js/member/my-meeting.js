//함수 고립화
//onclick 이 할당되면 각 객체마다 함수를 지니게 됨
window.addEventListener("load",
    function () {
        const rateBtns = document.querySelectorAll(".rate");
        // let modalBtn = document.querySelector("#rateBtn");
        const rateUi = document.querySelector(".modal__body");
      
        for(const btn of rateBtns){
          btn.onclick = showRateModal;
        }
        //함수를 객체 하나씩 가지고있게됨
          
        function showRateModal(e) {
          e.preventDefault();
          rateUi.innerHTML="";
          const dataId = e.target.dataset.id;
          fetch(`http://localhost:8080/member/eval/${dataId}`)
          .then(response => {if (response.ok) {return response;}})
          .then(response => response.json())
          .then(json => {
                          let jsonObject = JSON.parse(JSON.stringify(json[0]));
                          const title = jsonObject.title;
        /* =========================   ♻ template rendering... ====================== */
                          let header =` <div class="rate-container">
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

                          rateUi.insertAdjacentHTML("beforeend", header);

                          // nickname의 갯수만큼 렌더링 반복
                          for(let i=0; i<json.length; i++){
                              let jsonObject =JSON.parse(JSON.stringify(json[i]));
                              let nickname = jsonObject.nickname;
                              let body =` <div class =rate__slider>
                                            <span>${nickname}</span>
                                            <input type="range" step="50" value="50" min="0" max="100">
                                          </div>
                                        `;

                              rateUi.insertAdjacentHTML("beforeend", body);
                              }                                 

                          let footer =`     
                                      <div class ="btn-box">
                                        <div class="btn btn-semiround">완료</div>
                                      </div> </div> </div> </div>`

                          rateUi.insertAdjacentHTML("beforeend", footer);
          /* =====================   ♻ template rendering done ❗ ======================= */
              
                          showModalByButton(e);

          
              //기다리기 dom 로드? 생성 ?비동기
              // showModalByButton(e).then(res=>{
              //   const input = document.querySelector("input[type=range]");
              //   console.log(input);
              // }); 
              
              const rangeInputs = document.querySelectorAll('input[type="range"]');
           
                        });  //fetch 요청 완료
                  } //showRateModal 종료
           }); //load, click 이벤트 종료