<template>
    <nav class="admin-nav">
        <ul class="admin-nav__lists">
            <li v-for="(category,index) in categories" :key="index"
                class="admin-nav__list" :class="{'admin-nav__list--selected':(categoryStatus == index)}">
                <router-link :to="category.link">{{ category.name }}</router-link>
            </li>
        </ul>
    </nav>
</template>

<script setup>

const props = defineProps(['categories','categoryStatus']);

</script>

<style scoped>
.admin-nav-wrapper{
    /* width: 1200px;
    margin-left: auto;
    margin-right: auto;
    padding-bottom: 1rem; */
    
}

.admin-nav__lists{
    font-size: 20px;
    border-bottom: 3px solid var(--admin-light-grey);
    padding:1rem 0rem;
    color: var(--admin-loyal-blue);
    /* margin: 0 10rem; */
}

.admin-nav{
    width: 90%;
}



.admin-nav__list{
    cursor: pointer;
    display: inline;
    padding: 0 1rem
}

.admin-nav__list--selected{
    border-bottom: 2px solid var(--admin-loyal-blue);
    padding-bottom: 1rem;
    font-weight: bold;
}
</style>