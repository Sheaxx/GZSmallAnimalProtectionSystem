<template>
  <div id="app">
    <div class="routerView">
      <router-view></router-view>
    </div>
    <User></User>
    <Menu></Menu>
  </div>
</template>

<style>
@import "../src/assets/style/reset.css";

body {
  background: #fcfaf1;
}
#app {
  position: relative;
}
#app .routerView {
  position: absolute;
  top: 20vh;
  left: 8vw;
  width: 87vw;
  height: 70vh;
  /* background: rosybrown; */
}
</style>

<script>
import Menu from "./components/menu.vue";
import User from "./components/userBox.vue";
export default {
  components: { Menu, User },
};
</script>