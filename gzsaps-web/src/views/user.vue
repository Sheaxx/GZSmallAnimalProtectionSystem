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
          <el-button type="warning" size="small" icon="el-icon-edit" @click="openEdit">编辑个人信息</el-button>
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
          <el-button type="warning" @click="onSubmit">修改</el-button>
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
        username: "kooriookami",
        realname: "李白",
        password: "",
        address: "江苏省苏州市吴中区吴中大道 1188 号",
        tel: "18100000000",
        role: "志愿者",
      },
      adoptions: [
        {
          content: "活动按期开始",
          timestamp: "2018-04-15",
        },
        {
          content: "通过审核",
          timestamp: "2018-04-13",
        },
      ],
      projects: [
        {
          content: "活动按期开始",
          timestamp: "2018-04-15",
        },
        {
          content: "通过审核",
          timestamp: "2018-04-13",
        },
      ],
      form: {
        name: "",
        region: "",
        date1: "",
        date2: "",
        delivery: false,
        type: [],
        resource: "",
        desc: "",
      },
    };
  },
  methods: {
    editUser() {},
    //打开修改
    openEdit() {
      this.readOnly = false;
    },
    //取消修改
    cancelEdit() {
      this.readOnly = true;
    }
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
}
.title {
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
  margin-left: 20px!important;
}
</style>