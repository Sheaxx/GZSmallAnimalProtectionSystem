<template>
  <div id="userBox">
    <span>欢迎你，{{$store.state.username}}！</span>
    <img src="../assets/image/avatar.png" alt="头像" />
  </div>
</template>

<script>
export default {
  data() {
    return {
      
    };
  },
};
</script>

<style>
#userBox {
  position: relative;
  width: 99%;
  height: 20vh;
  font-size: 1.5rem;
}
#userBox span {
  position: absolute;
  top: 6vh;
  left: 75%;
}
#userBox img {
  position: absolute;
  top: 3vh;
  left: 90%;
  width: 65px;
  height: 65px;
}
</style>