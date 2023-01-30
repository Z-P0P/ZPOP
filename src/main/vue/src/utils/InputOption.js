export default class InputOption{
    constructor(id,items,title,placeholder,parameterName){
        this.id = id;
        this.items = items;
        this.title = title;
        this.isOpened = (items) ? false : null;
        this.placeholder = placeholder;
        this.message = '';
        this.parameterName = parameterName;
        this.hasError = false;
        this.currentValue = '';
    }
}