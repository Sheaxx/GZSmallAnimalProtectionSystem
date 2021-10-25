<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<style>
@import "../src/assets/style/reset.css";

</style>

<script>
import Login from "./views/login.vue";
import Index from "./views/index.vue";
export default {
  components: { Index, Login },
};
</script>