<template>
  <div id="menu">
    <el-menu
      background-color="#fff"
      text-color="#909399"
      active-text-color="#ff7f41"
      class="el-menu-vertical-demo"
      @open="handleOpen"
      @close="handleClose"
      :collapse="isCollapse"
      default-active="/user"
      router
    >
      <el-button type="warning" icon="el-icon-arrow-right" circle @click="changeCollapse" v-if="isCollapse"></el-button>
      <el-button type="warning" icon="el-icon-arrow-left" circle @click="changeCollapse" v-if="!isCollapse"></el-button>
      <el-menu-item index="/user">
        <i class="el-icon-user-solid"></i>
        <span>个人中心</span>
      </el-menu-item>
      <el-menu-item index="/notice">
        <i class="el-icon-message-solid"></i>
        <span>公告</span>
      </el-menu-item>
      <el-menu-item index="/information">
        <i class="el-icon-s-management"></i>
        <span>小动物科普</span>
      </el-menu-item>
      <el-menu-item index="/adoption">
        <i class="el-icon-s-claim"></i>
        <span>领养</span>
      </el-menu-item>
      <el-menu-item index="/project">
        <i class="el-icon-s-flag"></i>
        <span>志愿者项目</span>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script>
export default {
  data() {
    return {
      isCollapse: true,
    };
  },
  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    },
    changeCollapse() {
      this.isCollapse = !this.isCollapse;
    }
  },
};
</script>

<style>
#menu {
  position: absolute;
  z-index: 998;
  top: 0;
  left: 0;
}
.el-menu-vertical-demo.el-menu--collapse.el-menu {
  min-height: 99.5vh;
  box-shadow: 0px 0px 10px 5px rgba(0, 0, 0, 0.2);
}
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 99.5vh;
  box-shadow: 0px 0px 10px 5px rgba(0, 0, 0, 0.2);
}
.el-button.el-button--warning.is-circle {
  background: #ff7f41;
  position: relative;
  left: 12.5px;
  top: 10px;
  margin-bottom: 30px;
}
.el-menu-item:hover,
.el-menu-item:active {
  background-color: rgb(243, 241, 241)!important;
}
</style>