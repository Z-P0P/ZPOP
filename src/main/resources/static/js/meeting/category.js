window.addEventListener('load', function() {

	let selectedCategory = 0;
	const categories = document.querySelectorAll(".category");
	
	for (let category of categories){
		category.onclick = selectCategory;
	}
	
	function selectCategory(e){
		const categoryId = e.target.dataset.id;
		if (categoryId == selectedCategory){
			return;
		}
		
		categories[selectedCategory].classList.remove("selected");
		e.target.classList.add("selected");
		
		selectedCategory = categoryId;
	}
	
});




