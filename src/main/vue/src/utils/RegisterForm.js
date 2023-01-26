import InputOption from "./InputOption";
import api from '@/api';
import {reactive} from 'vue'

const localTimeToday = new Date();

const localTimeOneMonthAfter = new Date(localTimeToday.getTime() + (30*24*60*60*1000));

export class RegisterForm{
	constructor(){
		this.isLoaded = false;
		this.inputs = reactive({});
		this.editor = null;
		this.modalState = reactive({
			isOpened : false,
			sectionNum : 1,
		});
        this.meetingUrl = '#';
        this.errorMessage = '';
	}

	addInput(input){
        this.inputs[input.id] = input;
	}

	addDefaultInputs(){
		const genderCategories = {
			items : [{ id : 0, name : '남녀 모두'}, { id : 1, name : '남자만'}, {id : 2, name : '여자만'}],
			title : '성별',
			placeholder : '성별을 선택해주세요.',
			parameterName : 'genderCategory',
		}
	
		const maxMembers = {
			items : [{id : 2, name : '2 명'}, {id : 3, name : '3 명'}, {id : 4, name : '4 명'}, {id : 5, name : '5 명'}, {id : 6, name : '6 명'}],
			title : '모집 인원',
			placeholder : '모집 인원을 선택하세요',
			parameterName: 'maxMember',
		}
			   
		const title = {
			title : '제목',
			parameterName : 'title',
			placeholder : '제목을 입력해주세요.',
		}
	
		const detailRegion = {
			title : '상세장소',
			parameterName : 'detailRegion',
			placeholder : '상세장소를 입력해주세요.',
		};
	
		const startedAt = {
			title : '시작일자',
			parameterName : 'startedAt',
			placeholder : '시작일자를 선택해주세요.',
		}
	
		const contact = {
			title : '오픈채팅방 링크',
			parameterName : 'contact',
			placeholder : '오픈채팅방 링크를 입력해주세요.'
		}
	
		const content = {
			title : '내용',
			parameterName : 'content'
		}
	
		const inputs = {genderCategories, maxMembers, title
						,detailRegion ,startedAt, contact ,content}
	
		Object.keys(inputs).forEach(key=>{
			const input = inputs[key];
            const inputOption = new InputOption(key, input.items, input.title, 
                                                     input.placeholder, input.parameterName)
			this.addInput(inputOption);
		})
	}

    requestInputOptions(){
        const request = api.meeting.getActiveOptions();
        request
        .then(res => res.json())
        .then(options => {
            this.updateOptionsAfterRequest(options)
            this.isLoaded = true;
        })
        .catch((err) => { alert('모임글 옵션을 불러오는데 실패했습니다.'+err) });
    }

    updateOptionsAfterRequest(inputOptions){
        Object.keys(inputOptions).forEach((key) => {
            let title;
            let placeholder;
            let parameterName;
            switch (key) {
                case 'categories':
                    title = '카테고리';
                    placeholder = '카테고리를 선택해주세요.';
                    parameterName = 'categoryId'
                    break;
                case 'ageRanges':
                    title = '연령대';
                    placeholder = '연령대를 선택해주세요.';
                    parameterName = 'ageRangeId';
                    break;
                case 'regions':
                    title = '지역';
                    placeholder = '지역을 선택해주세요.';
                    parameterName = 'regionId';
                    break;
                case 'contactTypes':
                    title = '연락방법';
                    placeholder = '연락방법을 선택해주세요.';
                    parameterName = 'contactTypeId'
                    break;
            }
            const input = new InputOption(key,inputOptions[key],title,placeholder,parameterName);
            this.addInput(input);
        })
    }

    validateInput(){
        const inputs = this.inputs;
        Object.keys(inputs).forEach(key=>{
            const currentValue = inputs[key].currentValue;
            const parameterName = inputs[key].parameterName;
            if(currentValue === ""){
                const title = inputs[key].title;
                const errorMessage = 
                (['카테고리','시작일자','연령대'].indexOf(title)!=-1)?
                `${title}를 선택하세요.`:
                (['지역','성별','모집 인원','연락방법'].indexOf(title)!=-1)?
                `${title}을 선택하세요`:
                (['제목'].indexOf(title)!=-1)?
                `${title}을 입력하세요`: `${title}를 입력하세요`
    
                inputs[key].hasError = true;
                inputs[key].message = errorMessage;
            }
            if (parameterName ==="startedAt" && currentValue !== ""){
                const result = this.constructor.checkTimeValid(currentValue);
                
                inputs[key].hasError = !result.isValid;
                inputs[key].message = result.reason;
            }
        })

        for (let key of Object.keys(inputs)){
            if(inputs[key].hasError){
                return false;
            }
        }
        return true;
    }

    closeAllExcept(id){
        const inputs = this.inputs;
        Object.values(inputs)
        .filter(input=>input.isOpened !== null)
        .forEach(input => {
            if(input.id !== id){
                input.isOpened = false
            }
        });
    }

    closeStatusModal(){
        this.modalState.isOpened = false;
        this.modalState.sectionNum = 1;
    }

    openStatusModal(){
        this.modalState.isOpened = true;
    }

    postMeeting(){
        this.openStatusModal();
        const request = api.meeting.postRequest(this.getJSONData());
        request
        .then(res => {
            if (res.status === 400){
                throw new Error('오류: 입력값을 확인해주세요');
            }
            if (res.status === 500){
                throw new Error('서버에 오류가 발생했습니다.');
            }
            return res.json();
        })
        .then(data => {
            this.modalState.sectionNum = 2;
            this.meetingUrl = `/meeting/${data.meetingId}`
        })
        .catch(err=>{
            this.modalState.sectionNum = 0;
            this.errorMessage = err.message;
        })
    }
    setContentInEditor(){
        const quill = this.editor
        this.inputs.content.currentValue = quill.root.innerHTML;
    }
    setEditor(editor){
        this.editor = editor;
    }
    getJSONData(){
        const data = {};
        const inputs = this.inputs;

        Object.keys(inputs).forEach(key=>{
            const input = inputs[key];
            data[input.parameterName] = input.currentValue;
        })
        
        data['images'] = this.getImageTagsInContent();
        
        const dataJSONStr = JSON.stringify(data);
        return dataJSONStr;
    }
    getImageTagsInContent(){
        const parser = new DOMParser();
        const quillContentDOM = parser.parseFromString(this.content, 'text/html').body;
        const attachedImages = quillContentDOM.querySelectorAll('img');
        let images = [];
        attachedImages.forEach(image => {
            if (image.dataset.id == undefined) {
                return;
            }
            images.push(
                    {
                        id: image.dataset.id,
                    }   
                );
        });
        return images;
    }
    closeAllSelectBox(){
        const inputs = this.inputs;
        Object.keys(inputs).forEach(key => {
            if (inputs[key].isOpened === null) return

            inputs[key].isOpened = false;
        })
    }
    updateSelectBox(id){
        const input = this.inputs[id];
        this.closeAllExcept(id);
        input.isOpened = !input.isOpened;
        input.hasError = false;
    }
    updateOption(id, placeholder, value){
        const input = this.inputs[id];
        input.placeholder = placeholder;
        input.currentValue = value;
        input.isOpened = !input.isOpened;
    }

    updateDate(id, inputDate){
        const input = this.inputs[id];
        input.hasError = false;
        const result = this.constructor.checkTimeValid(inputDate);
        if (!result.isValid) {
            input.message = result.reason;
            input.hasError = true;
            return;
        }
        input.currentValue = inputDate;
    }

    updateTextInput(id, currentValue){
        const input = this.inputs[id];
        input.hasError = false;
        input.currentValue = currentValue;
    }

    static checkTimeValid(inputTimeString){
        const result = {
            isValid : false,
            reason : '',
        }
        const inputTime = new Date(inputTimeString);
        if (inputTime < localTimeToday) {
            result.reason = '시작일자는 오늘날짜 이후로 선택해주세요.';
            return result;
        }
        if (inputTime > localTimeOneMonthAfter){
            result.reason = '시작일자는 한 달이내의 날짜로 선택해주세요.';
            return result;
        }
    
        result.isValid = true;
        return result;
    }
}