//함수 고립화
//onclick 이 할당되면 각 객체마다 함수를 지니게 됨
window.addEventListener("load", function () {
  


  const rateBtns = document.querySelectorAll(".rate");
        // let modalBtn = document.querySelector("#rateBtn");
        const rateUi = document.querySelector(".modal__body");
        for(const btn of rateBtns){
          btn.onclick = showRateModal;
        }
          
        function showRateModal(e) {

          let meetingId;
          let meetingTitle;
          let participantsId = [];
          let rateValue = [];
          let evals = [];
          //참여자 평가정보
          //evalList zzzz
          //evalInfo
          e.preventDefault();

          rateUi.innerHTML="";
          const dataId = e.target.dataset.id;
          fetch(`/member/eval/${dataId}`)
          .then(response => {if (response.ok) {return response;}})
          .then(response => response.json())
          .then(json => {//정보받아올때 json 으로 온 것 -> javascript 객체로 하기위해 response.json()
                          console.log(json);
                          // let jsonObject = JSON.parse(JSON.stringify(json[0]));

                          
                          const memberId = parseInt(rateUi.dataset.id);
                          meetingTitle = json[0].title;
                          meetingId = json[0].meetingId;
        /* =========================   ♻ template rendering... ====================== */
                          let header =` <div class="rate-container">
                                            <h1 class="rate__title" >${meetingTitle}</h1>
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
                              // console.log(memberId == jsonObject.participantId);

                              if(memberId == json[i].participantId)
                              continue;

                              let nickname = json[i].nickname;
                              let body =` <div class =rate__slider>
                                            <span>${nickname}</span>
                                            <input type="range" step="50" min="0" max="100" data-rate=${i+1} 
                                            >
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
              


              showModalByButton(e).then(res=>{
                e.preventDefault()
                const rangeInputs = document.querySelectorAll('input[type="range"]');
                for(const input of rangeInputs){
                  input.oninput = bgChange;
                }

                function bgChange(e) {
                  e.preventDefault();
                  let inputId =  e.target.dataset.rate;
                  const targetInput = document.querySelector(`[data-rate="${inputId}"]`);
                  let value = targetInput.value;
                  let color = 'linear-gradient(90deg, rgb(98, 179, 185)' + value + '% , rgb(235, 235, 235)' + value + '%)';
                  targetInput.style.background = color;

                 
                }



                  for (let participant of json){
                      if(memberId == participant.participantId)
                          continue;
                      participantsId.push(participant.participantId);

                  }


                let submitBtn = document.querySelector('#modal-eval-notification .btn-box > div');
                submitBtn.onclick = () => {
                  for (const r of rangeInputs) {
                    //0, 50, 100
                      let result = 0;
                      if (parseInt(r.value) === 0) result = -1;
                      if (parseInt(r.value) === 50) result = 1;
                      if (parseInt(r.value) === 100) result = 3;

                    rateValue.push(result);

                  }

                  for (let i = 0; i < rateValue.length; i++){
                      let eval = {
                          evaluateeId : participantsId[i],
                          result : rateValue[i],
                      }
                      evals.push(eval);
                  }


                  let list = {
                      meetingId : meetingId,
                      evaluateeId : participantsId,
                      evals : evals,
                  };

                  console.log(`meetingId: ${meetingId}`);
                  console.log(`evaluateeId : ${participantsId}`);
                  console.log(`rateValue : ${rateValue}`);
                    console.log(list);
                    fetch("/member/rate", {
                        method: 'POST',
                        mode: 'cors',
                        cache: 'no-cache',
                        credentials: 'same-origin',
                        headers: {
                            'Content-Type': 'application/json',
                            // 'Content-Type': 'text/plain;charset=UTF-8',
                        },
                        redirect: 'follow',
                        referrer: 'no-referrer',
                        body: JSON.stringify(list)

                    }).then((response)=>console.log("ok"))

                  hideModalByButton(e);
                  // reqRate(meetingTitle);
                }
                
               
             

              }); 
              
           
                        });  //fetch 요청 완료
                  } //showRateModal 종료

        
        function reqRate(meetingTitle) {
          console.log(meetingTitle);

          // let myJson = {
          //     "meetingTitle" : meetingTitle
          // };
          fetch("http://localhost:8080/member/rate", {
            method: 'POST',
            mode: 'cors',
            cache: 'no-cache', 
            credentials: 'same-origin',
            headers: {
                'Content-Type': 'application/json',
                // 'Content-Type': 'text/plain;charset=UTF-8',
            },
            redirect: 'follow', 
            referrer: 'no-referrer',
            body: meetingTitle
            
        })//fetch 실행이 끝나면 then의 내용 실행
        //응답을 JSON 형태로 파싱한다
        // .then(response => response.json()) 
        .then(console.log("susese"))
        }


           }); //load, click 이벤트 종료