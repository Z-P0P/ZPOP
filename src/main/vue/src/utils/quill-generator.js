export function getQuillEditor(){
	const quill = quillGenerator("#editor", {
		theme: 'snow',
		modules: {
			toolbar: {
				container: [
					[{ 'header': [1, 2, 3, 4, 5, 6, false] }],
					['bold', 'italic', 'underline'],
					[{ 'list': 'ordered' }, { 'list': 'bullet' }],
					[{ 'align': [] }],
					['link', 'image'],
					['clean'],
					[{ 'color': [] }]
				],
				handlers: {
					//커스텀 image툴바 onclick handler
					image: quillImageHandler,
				}
			},
		}
	});
	return quill;
}

function quillGenerator (identifier, toolbarOptionsObj) {
	let ImageBlot = Quill.import('formats/image');
	class CustomImageBlot  extends ImageBlot {
		static create(value) {
			let node = super.create(value);
			// Sanitize url value if desired
			node.setAttribute('src', value.src);
			if(value.dataId){
				node.setAttribute('data-id', value.dataId);				
			}
			// Okay to set other non-format related attributes
			// These are invisible to Parchment so must be static
			return node;
		}

		static value(node) {
			let blot = {};
			blot.src = node.getAttribute('src');
			if (node.getAttribute('data-id')){
				blot.dataId = node.getAttribute('data-id');				
			}
			return blot;
		}
	}
	CustomImageBlot.tagName = 'img';
	CustomImageBlot.blotName = 'customImage';
	Quill.register(CustomImageBlot);

	return new Quill(identifier, toolbarOptionsObj);
}

function quillImageHandler (){
	const fileUploadInput = document.getElementById("fileUpload");
	let event = new MouseEvent("click", { bubbles: true });
	fileUploadInput.dispatchEvent(event);
}

export function quillImageUploadHandler(event) {
	let input = event.target.files[0];
	
	if (!input) {
		return 
	}
	
	if(!isImage(input.name)){
		alert('이미지 형식만 업로드 가능합니다');
		return;
	}
	
	const form = new FormData();
	form.append('file', input);
	form.append('path', '/image/meeting');
	const uploadUrl = '/api/upload';
	const option = {
		method: "POST",
		body: form,
	}

	fetch(uploadUrl, option)
	.then(response => response.json())
	.then(data=>{
		
		const selection = window.getSelection();
		const range = selection.getRangeAt(0);
		let imageTag = document.createElement('IMG');
		imageTag.src = `/image/meeting/${data.name}`;
		imageTag.dataset.id = data.id;
		range.insertNode(imageTag);
		selection.getSelection().modify('move', 'right', 'line');
	})
	// TODO : 예외처리 수정
	.catch((err)=>alert(err))

}

function getExtension(filename) {
  let parts = filename.split('.');
  return parts[parts.length - 1];
}

function isImage(filename) {
 let ext = getExtension(filename);
  switch (ext.toLowerCase()) {
    case 'jpg':
    case 'gif':
    case 'bmp':
    case 'png':
    case 'svg':
      //etc
      return true;
  }
  return false;
}