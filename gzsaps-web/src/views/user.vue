<template>
  <div id="user">
    <div id="userInfo">
      <el-descriptions
        class="descriptions"
        title="个人信息"
        :column="2"
        border
        v-if="readOnly"
      >
        <template slot="extra">
          <el-button
            type="warning"
            size="small"
            icon="el-icon-edit"
            @click="openEdit"
            >编辑个人信息</el-button
          >
        </template>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user"></i>
            用户名
          </template>
          {{ user.username }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-mobile-phone"></i>
            联系方式
          </template>
          {{ user.tel }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-postcard"></i>
            真实姓名
          </template>
          {{ user.realname }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-suitcase"></i>
            身份
          </template>
          <el-tag size="small" type="warning">{{ user.role }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-office-building"></i>
            地址
          </template>
          {{ user.address }}
        </el-descriptions-item>
      </el-descriptions>

      <el-form ref="user" :model="user" label-width="80px" v-else>
        <el-form-item label="用户名">
          <el-input v-model="user.username" disabled></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="user.password" show-password></el-input>
        </el-form-item>
        <el-form-item label="联系方式">
          <el-input v-model="user.tel"></el-input>
        </el-form-item>
        <el-form-item label="真实姓名">
          <el-input v-model="user.realname"></el-input>
        </el-form-item>
        <el-form-item label="用户身份">
          <el-tag type="warning">{{ user.role }}</el-tag>
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="user.address"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="warning" @click="editUser">修改</el-button>
          <el-button type="warning" plain @click="cancelEdit">取消</el-button>
        </el-form-item>
      </el-form>
    </div>

    <div id="userAdoption">
      <div class="title">个人领养信息</div>
      <el-timeline v-if="adoptions.length">
        <el-timeline-item
          v-for="(item, index) in adoptions"
          :key="index"
          :timestamp="item.timestamp"
        >
          {{ item.content }}
        </el-timeline-item>
      </el-timeline>
      <el-empty description="暂无数据" :image-size="50" v-else></el-empty>
    </div>
    <div id="userProject">
      <div class="title">个人志愿者项目</div>
      <el-timeline v-if="projects.length">
        <el-timeline-item
          v-for="(item, index) in projects"
          :key="index"
          :timestamp="item.timestamp"
        >
          {{ item.content }}
        </el-timeline-item>
      </el-timeline>
      <el-empty description="暂无数据" :image-size="100" v-else></el-empty>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      readOnly: true,
      user: {
        username: "",
        realname: "",
        password: "",
        address: "",
        tel: "",
        role: "",
      },
      adoptions: [],
      projects: [],
    };
  },
  methods: {
    //修改个人信息
    editUser() {
      let that = this;
      let obj = {};
      Object.assign(obj, this.user);
      if(obj.role == "普通用户") {
        obj.role = 0;
      } else if (obj.role == "志愿者") {
        obj.role = 1;
      } else {
        obj.role = 2;
      }
      this.$ajax
        .put("http://localhost:8081/user/update", obj)
        .then(function (res) {
          if (res.data == "success") {
            that.$store.commit("setUser", obj);
            that.$message({
              message: "修改成功",
              type: "success",
            });
          }
        }).catch((err) => console.log(err));
      this.readOnly = true;
    },
    //打开修改
    openEdit() {
      this.readOnly = false;
    },
    //取消修改
    cancelEdit() {
      this.readOnly = true;
    },
  },
  created() {
    console.log(this.$store.state.user);
    let that = this;
    Object.assign(this.user, this.$store.state.user);
    if (this.user.role == 0) {
      this.user.role = "普通用户";
    } else if (this.user.role == 1) {
      this.user.role = "志愿者";
    } else {
      this.user.role = "管理员";
    }
    this.$ajax
      .get("http://localhost:8081/adoption/findAll/1/8")
      .then((res) => {
        that.adoptions = res.data.content.slice(0, 5);
      })
      .catch((error) => console.log(error));
    this.$ajax
      .get("http://localhost:8081/project/findAll/1/8")
      .then((res) => {
        that.projects = res.data.content;
      })
      .catch((error) => console.log(error));
  },
};
</script>

<style>
#userInfo {
  width: 57%;
  background: #fff;
  padding: 2% 5% 4% 3%;
  border-radius: 25px;
  box-shadow: 0px 0px 10px 5px rgba(0, 0, 0, 0.2);
}
#userAdoption {
  width: 61%;
  margin-top: 2%;
  padding: 2%;
  padding-bottom: 1%;
  background: #fff;
  border-radius: 25px;
  box-shadow: 0px 0px 10px 5px rgba(0, 0, 0, 0.2);
  overflow: hidden;
  min-height: 30%;
  max-height: 50%;
}
#user .title {
  font-size: 16px;
  font-weight: 700;
  margin-bottom: 25px;
}
#userProject {
  position: absolute;
  width: 25%;
  height: 90%;
  top: 0;
  right: 0;
  background: #fff;
  padding: 3%;
  padding-bottom: 1%;
  border-radius: 25px;
  box-shadow: 0px 0px 10px 5px rgba(0, 0, 0, 0.2);
}
#user .el-form-item {
  float: left;
  position: relative;
  z-index: 999;
}
#user .el-form-item:nth-last-child(3),
#user .el-form-item:nth-last-child(2) {
  float: none;
  width: 100%;
}
#user .el-form-item {
  width: 50%;
  overflow: hidden;
}
#user .el-form-item:last-child {
  float: right;
}
#user .el-button {
  float: right;
  margin-left: 20px !important;
}
#user .el-input__inner:focus {
  border-color: #ff7f41;
}
</style>