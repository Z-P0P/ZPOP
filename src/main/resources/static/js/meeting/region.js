window.addEventListener('load', function() {

	let currentRegionCount = 0;
	const MAX_REGION_COUNT = 3;
	const regionSelectedStatus = [];

	const meetingActivateOption = document.querySelector(".meeting-activate-option");
	const regionCategoryOptions = document.querySelectorAll(".region-category .select-box__options > li");
	const meetingOptions = document.querySelector(".meeting__options");
	
	for (let option of regionCategoryOptions) {
		option.onclick = addRegion;
	}
	
	function addRegion(event) {
		
		
		console.log(currentRegionCount);
		if (currentRegionCount >= MAX_REGION_COUNT){
			return;			
		}
		
		
		let regionId = event.target.dataset.id;		
		console.log(regionSelectedStatus[regionId]);
		if (regionSelectedStatus[regionId]){
			return;
		}
		
		const region = event.target.innerText;

		const template = `
	    <div class="meeting-region" data-id=${regionId}>
        	<span>${region}</span>
        	<img src="/images/icon/x-white.svg">
    	</div>
	`
		currentRegionCount++;
		
		meetingActivateOption.insertAdjacentHTML("beforebegin", template);
		
		
		
		const cancelBtn = meetingOptions.querySelectorAll(".meeting-region")[currentRegionCount-1].querySelector("img");
		cancelBtn.onclick = removeRegion;
		regionSelectedStatus[regionId] = true;
	
	}
	
	function removeRegion(e){
		currentRegionCount--;	
		const selectedRegion = e.target.parentElement;
		const regionId = selectedRegion.dataset.id;
		meetingOptions.removeChild(selectedRegion);
		regionSelectedStatus[regionId] = false;
	}
});




