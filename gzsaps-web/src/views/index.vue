<template>
  <div id="index">
    <div class="routerView">
      <router-view></router-view>
    </div>
    <User></User>
    <Menu></Menu>
  </div>
</template>

<script>
import Menu from "../components/menu.vue";
import User from "../components/userBox.vue";
export default {
  components: { Menu, User },
};
</script>

<style>
body {
  width: 100%;
  height: 100%;
  background: #fdf2d6;
  position: relative;
}
.routerView {
  position: absolute;
  top: 20vh;
  left: 8vw;
  width: 87vw;
  height: 70vh;
  /* background: rosybrown; */
}
</style>