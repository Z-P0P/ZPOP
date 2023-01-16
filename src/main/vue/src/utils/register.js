const localTimeToday = new Date();

const localTimeOneMonthAfter = new Date(localTimeToday.getTime() + (30*24*60*60*1000));

export function checkTimeValid(inputTimeString){
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

export function validateInput(inputs){
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
			const result = checkTimeValid(currentValue);
			inputs[key].hasError = !result.isValid;
			inputs[key].message = result.reason;
		}
	})
}

export function addDefaultInputs(addedInputs) {
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
           
    const meetingTitle = {
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

	const inputs = {genderCategories, maxMembers, meetingTitle
					,detailRegion ,startedAt, contact ,content}

	Object.keys(inputs).forEach(key=>{
		const input = inputs[key];
		addInput(addedInputs, key, input.items, input.title, input.placeholder, input.parameterName)
	})
}

export function closeAllExcept(inputs, parameterName){
	Object.values(inputs)
		.filter(input=>input.isOpened !== null)
		.forEach(input => {
			if(input.parameterName !== parameterName){
				input.isOpened = false
			}
		});
}


export function addInput(inputs, propertyName, items=null, 
						title, placeholder=null, parameterName){
	const option = {
		id: propertyName,
		items: items,
		title: title,
		isOpened : (items) ? false : null,
		placeholder:placeholder,
		message: '',
		parameterName : parameterName,
		hasError: false,
		currentValue: '',
	}
	inputs[propertyName] = option;
}

export function getKeyFromParameterName(inputs, parameterName){
	let result = null;
	Object.keys(inputs).forEach(key=>{
		if(inputs[key].parameterName == parameterName){
			result = key;
			return result;
		}
	})
}