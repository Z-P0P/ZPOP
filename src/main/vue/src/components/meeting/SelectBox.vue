<template>
    <div class="select-box select-box--input" :class="{'select-box--error':option.hasError, 'select-box--expanded' : option.isOpened}" @click="onSelectBoxClick">
        <span class="pseudo-alternative"></span>
        <span>{{ option.placeholder }}</span>
        <ul v-show="option.isOpened" class="select-box__options" id="select-box">
            <li @click.stop="onOptionClick" v-for="(item, index) in option.items" :key="index" :data-id="item.id">{{ item.name || item.type }}</li>
        </ul>
    </div>
</template>

<script>
export default {
    props: ['option'],
    emits: ['selectBoxClick', 'optionClick'],
    setup(props,context) {
        const option = props.option;
        const onSelectBoxClick = () => {
            context.emit('selectBoxClick', option.id);

        };
        const onOptionClick = (e) => {
            context.emit('optionClick', option.id, e.target.innerText, e.target.dataset.id);
        }

        return{
            onOptionClick,
            onSelectBoxClick,
        }

    }
}
</script>

<style scoped>
@import url('@/assets/css/admin/component/select.css');


.pseudo-alternative{
    position:absolute;
    border: none;
    width:16px;
    height:16px;
    right:20px;
    padding:0;
}
</style>