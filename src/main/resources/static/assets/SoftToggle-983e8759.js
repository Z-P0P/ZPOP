import{_ as d,k as l,o as n,c as _,m as g,n as m,b as e,t as s}from"./index-e04ed277.js";const h={name:"MeetingThumbnail",props:{meeting:Object}},u={class:"meeting"},r={class:"meeting__header"},f={class:"meeting__header-detail"},v={class:"meeting__category"},y={class:"meeting__region add-deco-img-left deco-img-location"},b={class:"meeting__date"},k={class:"meeting__body"},p={class:"meeting__tags"},x={class:"meeting__body-detail"},C={key:0,class:"status status__on"},T={key:1,class:"status status__off"},w={class:"meeting__body-detail-more"},B={class:"meeting__views add-deco-img-left deco-img-eyes"},O={class:"meeting__comments add-deco-img-left deco-img-speech-bubble"};function S(o,i,t,c,A,D){const a=l("router-link");return n(),_("div",u,[g(a,{to:`/meeting/${t.meeting.id}`},{default:m(()=>[e("div",r,[e("div",f,[e("span",v,s(t.meeting.category),1),e("span",y,s(t.meeting.region),1),e("span",b,s(t.meeting.startedAt),1)]),e("h3",null,s(t.meeting.title),1)]),e("div",k,[e("ul",p,[e("li",null,"#"+s(t.meeting.ageRange),1),e("li",null,"#"+s(t.meeting.maxMember)+"명",1),e("li",null,"#"+s(t.meeting.genderCategory),1)]),e("div",x,[t.meeting.isClosed?(n(),_("div",T,"모집완료")):(n(),_("div",C,"모집중")),e("div",w,[e("div",B,s(t.meeting.viewCount),1),e("div",O,s(t.meeting.commentCount),1)])])])]),_:1},8,["to"])])}const I=d(h,[["render",S],["__scopeId","data-v-c40444dd"]]);const M={class:"soft-toggle-wrap"},N={class:"soft-toggle__switch"},V=["checked"],j=e("span",{class:"soft-toggle__slider"},null,-1),R={__name:"SoftToggle",props:{isOn:{type:Boolean,default:!1}},setup(o){const i=o;return(t,c)=>(n(),_("div",M,[e("label",N,[e("input",{type:"checkbox",checked:i.isOn},null,8,V),j])]))}};export{I as T,R as _};
