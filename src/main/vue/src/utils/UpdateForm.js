import api from '@/api';
import { RegisterForm } from './RegisterForm';


export default class UpdateForm extends RegisterForm{
    constructor(meetingId){
        super();
        this.meetingId = meetingId;
    }
    getRegisteredMeetingDetails(){
        const request = api.meeting.getDetailsForUpdate(this.meetingId);
        request.then(res=>res.json())
        .then(data => {
            const options = data.options;
            const details = data.details;
            super.updateOptionsAfterRequest(options);
            this.updateDetails(details);
            this.isLoaded = true;
        })
    }
    updateDetails(details){
        const inputs = this.inputs;
        Object.keys(inputs).forEach(key=>{
            const parameterName = inputs[key].parameterName;
            const registeredInput = details[parameterName];
            console.log(key, details[key]);
            if (parameterName === 'startedAt'){
                inputs[key].currentValue = this.constructor.changeDateFormat(registeredInput);
                const result = super.constructor.checkTimeValid(registeredInput);
                if (!result.isValid) {
                    inputs[key].message = result.reason;
                    inputs[key].hasError = true;
                    return;
                }
                return;
            }

            if (parameterName === 'title' || parameterName === 'detailRegion' || parameterName === 'contact' || key === 'content'){
                inputs[key].currentValue = registeredInput;
                return;
            }
            console.log(parameterName);
            if (parameterName === 'categoryId' || parameterName === 'regionId' || parameterName === 'contactTypeId'){
                const placeholder = details[`${parameterName.slice(0,-2)}Name`];
                console.log(`${parameterName.slice(0,-2)}Name`);
                inputs[key].placeholder = placeholder;
                console.log(placeholder);
                inputs[key].currentValue = registeredInput;
                return;
            }

            if (parameterName === 'genderCategory'){
                let placeholder = '';
                console.log(registeredInput);
                switch(registeredInput){
                    case 0: 
                    placeholder = '남녀 모두'
                    break;
                    case 1:
                    placeholder = '남자만'
                    break;
                    case 2:
                    placeholder = '여자만'
                    break;
                }
                inputs[key].placeholder = placeholder;
                inputs[key].currentValue = registeredInput;                
                return;
            }
            if (parameterName === 'ageRangeId'){
                const placeholder = details['ageRangeType'];
                inputs[key].placeholder = placeholder;
                inputs[key].currentValue = registeredInput;
                return;
            }

            if (parameterName === 'maxMember'){
                const placeholder = registeredInput+ ' 명';
                inputs[key].placeholder = placeholder;
                inputs[key].currentValue = registeredInput;
                return;
            }
        })
    }
    changeEditorContent(){
        const quill = this.editor;
        quill.root.innerHTML = this.inputs.content.currentValue;
    }
    requestMeetingUpdate(){
        const dataJSONStr = super.getJSONData();
        const request = api.meeting.putRequest(this.meetingId, dataJSONStr);
        request.then((res)=>{
            if (!res.ok){
                throw new Error();
            }
            this.modalState.sectionNum = 2;
            this.meetingUrl = `/meeting/${this.meetingId}`
        })
        .catch((err)=>{
            this.modalState.sectionNum = 0;
            this.errorMessage = err;
        })
    }
    // YYYY-MM-DDTHH:MM:SS => YYYY-MM-DDTHH:MM 로 변환 (second 제거)
    static changeDateFormat(dateString){
        return  dateString.slice(0,-3);
    }
}