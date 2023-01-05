export default function(identifier, toolbarOptionsObj) {
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