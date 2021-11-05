<template>
  <div id="userBox">
    <span class="userInfo">欢迎你，{{ nowUser }}！</span>
    <img src="../assets/image/avatar.png" alt="头像" @click="showExit = true" />

    <div class="coverBox" v-show="showExit">
      <div class="exitBox">
        <div class="exitContent">
          <i class="el-icon-warning-outline"></i>
          <span>确定退出登录吗？</span>
        </div>
        <div class="exitButton">
          <el-button type="warning" plain @click="showExit = false"
            >取消</el-button
          >
          <el-button type="warning" @click="exit">确定</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      nowUser:"",
      showExit: false,
    };
  },
  methods: {
    exit() {
      window.localStorage.removeItem("username");
      this.$router.replace("/login");
      this.$message.success("退出登录成功");
    },
  },
  created() {
    this.nowUser = window.localStorage.getItem("username");
  }
};
</script>

<style>
#userBox {
  position: relative;
  width: 99%;
  height: 20vh;
  font-size: 1.5rem;
}
#userBox .userInfo {
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
#userBox .coverBox {
  position: absolute;
  z-index: 999;
  width: 99.7vw;
  height: 99.8vh;
  top: 0vh;
  left: 0vw;
  background: rgba(0, 0, 0, 0.3);
}
.exitBox {
  position: absolute;
  width: 28%;
  height: 15%;
  background: #fff;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  padding: 3% 3% 0 3%;
}
.exitContent {
  font-size: 18px;
  width: 100%;
}
.exitContent i {
  font-size: 25px;
}
.exitButton {
  position: relative;
  top: 30%;
  left: 70%;
}
</style>