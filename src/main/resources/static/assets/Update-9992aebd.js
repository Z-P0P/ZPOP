import{R as D,h as S,_ as U,z as N,i as O,a as V,U as B,k as R,l,o as T,c as E,b as e,m as c,O as g,Q as I,w as H,g as F,v as q,n as v,f as L,I as A,H as $,p as h,s as j,x as y}from"./index-e04ed277.js";import{L as z}from"./LoadingRoller-20aecab2.js";import{g as J,M as Q,a as X,q as G}from"./quill-generator-f0a34d1e.js";import{M as K}from"./Default-9bfb2fe8.js";class P extends D{constructor(a){super(),this.meetingId=a}init(a,s){super.updateOptionsAfterRequest(a),this.updateDetails(s),this.isLoaded=!0}updateDetails(a){const s=this.inputs;Object.keys(s).forEach(n=>{const t=s[n].parameterName,r=a[t];if(console.log(n,a[n]),t==="startedAt"){s[n].currentValue=this.constructor.changeDateFormat(r);const i=super.constructor.checkTimeValid(r);if(!i.isValid){s[n].message=i.reason,s[n].hasError=!0;return}return}if(t==="title"||t==="detailRegion"||t==="contact"||n==="content"){s[n].currentValue=r;return}if(console.log(t),t==="categoryId"||t==="regionId"||t==="contactTypeId"){const i=a[`${t.slice(0,-2)}Name`];console.log(`${t.slice(0,-2)}Name`),s[n].placeholder=i,console.log(i),s[n].currentValue=r;return}if(t==="genderCategory"){let i="";switch(console.log(r),r){case 0:i="남녀 모두";break;case 1:i="남자만";break;case 2:i="여자만";break}s[n].placeholder=i,s[n].currentValue=r;return}if(t==="ageRangeId"){const i=a.ageRangeType;s[n].placeholder=i,s[n].currentValue=r;return}if(t==="maxMember"){const i=r+" 명";s[n].placeholder=i,s[n].currentValue=r;return}})}changeEditorContent(){const a=this.editor;a.root.innerHTML=this.inputs.content.currentValue}requestMeetingUpdate(){const a=super.getJSONData();S.meeting.putRequest(this.meetingId,a).then(n=>{if(!n.ok)throw new Error;this.modalState.sectionNum=2,this.meetingUrl=`/meeting/${this.meetingId}`}).catch(n=>{this.modalState.sectionNum=0,this.errorMessage=n})}static changeDateFormat(a){return a.slice(0,-3)}}const d=m=>(j("data-v-62d14e3f"),m=m(),y(),m),W=["onSubmit"],Y=d(()=>e("h1",{class:"meeting-form__title"},"모임 수정하기",-1)),Z={class:"meeting-form__default-info"},tt=d(()=>e("legend",null,"1. 모임 기본 정보를 입력해주세요.",-1)),et={class:"meeting-form__details"},nt=d(()=>e("legend",null,"2. 모임에 대해 소개해주세요.",-1)),st=d(()=>e("div",{class:"meeting-form__input"},[e("label",{class:"input-text__label",for:""},"내용"),e("div",{id:"editor"},[e("p",null,"우리의 모임에 대해 소개해주세요!"),e("p",null,[h(" 내용이 "),e("strong",null,"자세하면"),h(),e("u",null,"참여할 확률"),h("이 높아져요. ")]),e("p",null,[e("br")])])],-1)),ot=d(()=>e("div",{class:"meeting-form__buttons"},[e("button",{class:"btn btn-round"},"취소"),e("button",{id:"register-btn",class:"btn btn-round btn-action",type:"submit"},"수정하기")],-1)),at={class:"overflow-x-hidden"},it=d(()=>e("div",{class:"modal__content"},[e("div",{class:"icon-cancel"}),e("div",{class:"register-status__text"},[e("div",{class:"register-status__message"},"모임글 수정에 실패했어요.")])],-1)),lt={class:"modal__content"},rt=d(()=>e("div",{class:"register-status__text"},[e("div",{class:"register-status__message"},"모임글을 수정하고 있어요!"),e("a",{class:"register-status__btn btn btn-semiround",href:"#"},"등록 중")],-1)),ct={class:"modal__content"},ut=d(()=>e("div",{class:"icon-done"},null,-1)),dt={class:"register-status__text"},pt=d(()=>e("div",{class:"register-status__message"},"모임글을 수정했어요!",-1)),_t={__name:"Update",setup(m){const a=N(),s=O(),n=a.params.id,t=V(new P(n));async function r(){try{const o=await S.meeting.getDetailsForUpdate(n);if(!o.ok)throw new A(await o.json());const u=await o.json();t.init(u.options,u.details)}catch(o){o.res.status===404&&s.push("/404"),o.res.status===403&&s.replace("/403")}}r(),t.addDefaultInputs();const i=o=>{console.log(o),t.openStatusModal(),t.setContentInEditor(),t.validateInput(),t.requestMeetingUpdate()},C=o=>{o.target.parentElement.classList.contains("select-box")||t.closeAllSelectBox()},M=o=>{t.closeStatusModal()},b=G,p=o=>{t.updateSelectBox(o)},_=(o,u,x)=>{t.updateOption(o,u,x)},w=(o,u)=>{t.updateDate(o,u)},f=(o,u)=>{t.updateTextInput(o,u)};return B(()=>{t.editor||(t.setEditor(J()),t.changeEditorContent())}),(o,u)=>{const x=R("router-link");return l(t).isLoaded?(T(),E("div",{key:0,class:"meeting-form-container",onClick:C},[e("form",{class:"meeting-form",action:"meeting/register",onSubmit:H(i,["prevent"])},[Y,e("fieldset",Z,[tt,c(g,{onSelectBoxClick:p,onOptionClick:_,input:l(t).inputs.categories},null,8,["input"]),c(Q,{onDateChange:w,input:l(t).inputs.startedAt},null,8,["input"]),c(g,{onSelectBoxClick:p,onOptionClick:_,input:l(t).inputs.regions},null,8,["input"]),c(I,{onTextInput:f,input:l(t).inputs.detailRegion},null,8,["input"]),c(g,{onSelectBoxClick:p,onOptionClick:_,input:l(t).inputs.ageRanges},null,8,["input"]),c(g,{onSelectBoxClick:p,onOptionClick:_,input:l(t).inputs.genderCategories},null,8,["input"]),c(g,{onSelectBoxClick:p,onOptionClick:_,input:l(t).inputs.maxMembers},null,8,["input"]),c(X,{onSelectBoxClick:p,onOptionClick:_,onTextInput:f,selectInput:l(t).inputs.contactTypes,textInput:l(t).inputs.contact},null,8,["selectInput","textInput"])]),e("fieldset",et,[nt,c(I,{onTextInput:f,input:l(t).inputs.title},null,8,["input"]),st]),ot],40,W),F(c(K,{onCloseModal:M},{"modal-body":v(()=>[e("div",at,[e("div",{class:"modal__content-container",style:$({transform:`translateX(${l(t).modalState.sectionNum*-100}%)`})},[it,e("div",lt,[c(z,{isShow:!0}),rt]),e("div",ct,[ut,e("div",dt,[pt,c(x,{class:"register-status__btn btn btn-semiround btn-action",to:{name:"meetingDetail",params:{id:l(t).meetingId}}},{default:v(()=>[h("모임글로 이동")]),_:1},8,["to"])])])],4)])]),"modal-footer":v(()=>[]),_:1},512),[[q,l(t).modalState.isOpened]]),e("input",{type:"file",id:"fileUpload",class:"hidden",onChange:u[0]||(u[0]=(...k)=>l(b)&&l(b)(...k))},null,32)])):L("",!0)}}},xt=U(_t,[["__scopeId","data-v-62d14e3f"]]);export{xt as default};
