<script setup>
import {defineEmits, reactive, computed, ref, defineProps} from "vue";
import api from "@/api";
import SelectBox from '../meeting/SelectBox.vue';
import ReportCompleteModal from './ReportCompleteModal.vue'

const emit = defineEmits(['closeModal']);
const props = defineProps({
    commentId:Number
})
const isMissed = ref(false);
const isCompleted = ref(false);
const isDuplicated = ref(false);

const selectInput = reactive({
    id : 'reportUser',
    placeholder : '카테고리를 입력해주세요',
    items : [
                {id : 1, name :'욕설 및 무지성 비방 댓글'},
                {id : 2, name :'사행성을 조장하는 댓글'},
                {id : 3, name :'부적절한 링크를 포함'},
                {id : 4, name :'기타'}
            ],
    hasError : false,
    isOpened : false,
    currentValue : 0
    })

const selectBoxClickhandler = (id) => {
    selectInput.isOpened = !selectInput.isOpened;
}

const optionClickhandler = (id,placeholder,dataId) =>{
    selectInput.placeholder  = placeholder;
    selectInput.isOpened = false;
    selectInput.currentValue = dataId;
}

// 사용자 신고를 위한 세가지 데이터
const reportedCommentId = props.commentId;
const commentReportType = computed(()=>{
    return selectInput.currentValue;
})
const commentReportReason = ref("");

function report(reportedCommentId,commentReportType,commentReportReason){
   if(commentReportType==0){
    isMissed.value=true;
    return
   }
    const response = api.report.reportComment(reportedCommentId,commentReportType,commentReportReason);
    response
        .then((data)=>data.json())
        .then((result)=>{
            if(result){
                isCompleted.value = true
            }
            else{
                isCompleted.value = true
                isDuplicated.value = true
            }    
        })
}

function closeReportModal(){
    isCompleted.value = false
    emit('closeModal')
}

</script>

<template>
   <div id="modal-report-member" class="modal-wrapper z-idx-1 modal-report">
      <div class="modal">
        <div class="modal__header">
            <span class="modal__close-btn icon icon-x" data-modal="#modal-report-member" @click.prevent="$emit('closeModal')"></span>
        </div>
        <div class="report-modal__body">
         <div class="meeting-form__input">
            <label class="member-input__label" for="category">신고사유</label>
            <span v-if="isMissed">(필수선택 사항입니다)</span>
            <SelectBox class="selectBox" @selectBoxClick="selectBoxClickhandler" 
            @optionClick="optionClickhandler"
            :option="selectInput"/>
        </div>
        
        <div class="input-text">
            <label for ="member-input__content--id" class="input-text__label">상세사유 (선택)</label>
            <textarea  id="member-input__content--id" placeholder="상세 내용을 적어주세요. (최대 100자)" class="input-text__content" v-model="commentReportReason"></textarea>
        </div>
        <div class="btn-wrapper">
            <span id="cancel-btn" data-modal="#modal-report-member" class="comment__btn btn btn-round modal__close-btn report__cancel-btn" @click.prevent="$emit('closeModal')">취소</span>
            <span id="report-btn" data-modal="#modal-report-member" class="comment__btn btn btn-round member__report-btn" @click.prevent="report(reportedCommentId,commentReportType,commentReportReason)">신고하기</span>
        </div>
    </div>
</div>
</div>
<ReportCompleteModal @closeModal="closeReportModal" v-if="isCompleted" :isDuplicated="isDuplicated"/>
</template>

<style scoped>


@import url(../../assets/css/component/select.css);
@import url(../../assets/css/meeting/component/report-modal.css);
@import url(../../assets/css/meeting/component/modal.css);

/* #cancel-btn{
    width:100px;
} */
.selectBox{
    width:350px;
    margin-top: 10px;
}

.input-text__content{
    border: 2px solid var(--light-grey1);
    border-radius: 5px;
}
#member-input__content--id{
    margin-top: 5px;
    text-indent: 20px;
    padding-top: 10px;
}

.modal__close-btn{
    margin-right: 0px;
}

.meeting-form__input > span {
    color : red;
    margin-left: 10px;
}
</style>
