<script setup>
import { RouterLink, RouterView } from "vue-router";
import Detail from "./views/meeting/Detail.vue";
import Article from "./components/meeting/Article.vue";
</script>

  <Header/>
  <router-view/>
</template>

<script setup>
import Header from './components/header/header.vue'
</script>