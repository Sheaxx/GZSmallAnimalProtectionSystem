<template>
  <div id="project">
    <h4 class="boxTitle"><i class="el-icon-s-flag"></i>志愿者项目</h4>
    <el-button type="warning" @click="openAddMessage" v-if="showList"
      >添加项目</el-button
    >
    <el-button type="warning" @click="toList" v-else>返回列表</el-button>
    <div class="search" v-show="showList">
      <el-input
        placeholder="请输入志愿者项目编号"
        v-model="searchid"
      ></el-input>
      <el-button type="warning" icon="el-icon-search" @click="search"
        >搜索</el-button
      >
    </div>
    <el-timeline v-show="showList">
      <el-timeline-item
        placement="top"
        color="#ff7f41"
        v-for="(item, index) in projects"
        :key="index"
        :timestamp="item.createtime"
      >
        <el-card>
          <h5 @click="toDetails(index)">
            NO.{{ item.projectid }}：{{ item.title }}
          </h5>
          <div class="option">
            <a href="" @click.prevent="openUpdateMessage(index)">修改</a>
            <a href="" @click.prevent="openDeleteMessage(index)">删除</a>
          </div>
        </el-card>
      </el-timeline-item>
    </el-timeline>
    <el-pagination
      layout="prev, pager, next"
      :total="total"
      v-show="showList"
      :page-size="6"
      @current-change="page"
    >
    </el-pagination>

    <div class="coverBox" v-show="showMessage">
      <div class="messageBox">
        <el-form ref="projectForm" :model="projectForm" label-width="80px">
          <el-form-item label="主题">
            <el-input v-model="projectForm.title"></el-input>
          </el-form-item>
          <el-form-item label="内容">
            <el-input
              type="textarea"
              v-model="projectForm.content"
              rows="10"
              resize="none"
              maxlength="500"
              show-word-limit
            ></el-input>
          </el-form-item>
          <el-form-item label="活动时间">
            <el-date-picker
              v-model="time"
              type="datetimerange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              value-format="yyyy-MM-dd HH:mm:ss"
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item label="活动地址">
            <el-input v-model="projectForm.address"></el-input>
          </el-form-item>
          <el-form-item label="负责人">
            <el-input v-model="projectForm.principal"></el-input>
          </el-form-item>
          <el-form-item label="联系方式">
            <el-input v-model="projectForm.principaltel"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="warning" plain @click="cancelMessage"
              >取消</el-button
            >
            <el-button type="warning" @click="onSubmit">发布</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>

    <div class="coverBox" v-show="showDelete">
      <div class="deleteBox">
        <div class="deleteContent">
          <i class="el-icon-warning-outline"></i>
          <span>确定删除该条公告吗？</span>
        </div>
        <div class="deleteBuntton">
          <el-button type="warning" plain @click="cancelDelete">取消</el-button>
          <el-button type="warning" @click="deleteproject">确定</el-button>
        </div>
      </div>
    </div>

    <div class="details" v-show="!showList">
      <h4>{{ projectForm.title }}</h4>
      <p>{{ projectForm.content }}</p>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      showList: true,
      showMessage: false,
      showDelete: false,
      isAdd: false,
      currentIndex: 0,
      total: 0,
      searchid: null,
      time: [],
      projects: [],
      projectForm: {
        projectid: null,
        title: "",
        content: "",
        address: "",
        begintime: "",
        endtime: "",
        principal: "",
        principaltel: "",
        applicantlist: "",
      },
    };
  },
  methods: {
    //打开添加项目界面
    openAddMessage() {
      for (let item in this.projectForm) {
        this.projectForm[item] = null;
      }
      this.isAdd = true;
      this.showMessage = true;
    },
    //打开修改项目界面
    openUpdateMessage(index) {
      Object.assign(this.projectForm, this.projects[index]);
      this.isAdd = false;
      this.currentIndex = index;
      this.showMessage = true;
    },
    //打开删除项目提示
    openDeleteMessage(index) {
      this.currentIndex = index;
      this.showDelete = true;
    },
    //取消添加或修改
    cancelMessage() {
      this.showMessage = false;
      this.$message("取消发布");
    },
    //发布项目
    onSubmit() {
      let that = this;
      let obj = {};
      obj.title = this.projectForm.title;
      obj.content = this.projectForm.content;
      obj.address = this.projectForm.address;
      obj.begintime = this.time[0];
      obj.endtime = this.time[1];
      obj.principal = this.projectForm.principal;
      obj.principaltel = this.projectForm.principaltel;
      obj.author = this.$store.state.user.username;
      if (this.isAdd) {
        //新增项目
        if (this.$store.state.role == 1) {
          //志愿者身份
          obj.status = 0;
        } else if (this.$store.state.role == 2) {
          //管理员身份
          obj.status = 1;
        }
        (obj.applicantlist = ""), console.log(obj);
        console.log(this.time);
        this.$ajax
          .post("http://localhost:8081/project/save", obj)
          .then(function (res) {
            if (res.data == "success") {
              that.projects.unshift(obj);
              that.showMessage = false;
              if ($store.state.role == 1) {
                //志愿者身份
                that.$message({
                  message: "提交成功，待管理员审核",
                  type: "success",
                });
              } else if ($store.state.role == 2) {
                //管理员身份
                that.$message({
                  message: "发布成功",
                  type: "success",
                });
              }
            }
          })
          .catch((err) => console.log(err));
      } else {
        //修改项目
        obj.projectid = this.projects[this.currentIndex].projectid;
        obj.createtime = this.projects[this.currentIndex].createtime;
        obj.applicantlist = this.projects[this.currentIndex].applicantlist;
        this.$ajax
          .put("http://localhost:8081/project/update", obj)
          .then(function (res) {
            if (res.data == "success") {
              that.projects[that.currentIndex] = obj;
              that.showMessage = false;
              that.$message({
                message: "修改成功",
                type: "success",
              });
            }
          });
        that.projects[this.currentIndex] = obj;
      }
    },
    //取消删除
    cancelDelete() {
      this.showDelete = false;
      this.$message("取消删除");
    },
    //删除项目
    deleteproject() {
      let that = this;
      this.$ajax
        .delete(
          "http://localhost:8081/project/delete/" +
            this.projects[this.currentIndex].projectid
        )
        .then(function () {
          that.projects.splice(that.currentIndex, 1);
          that.showDelete = false;
          that.$message({
            message: "删除成功",
            type: "success",
          });
        })
        .catch((err) => console.log(err));
    },
    //查看详情
    toDetails(index) {
      this.showList = false;
      Object.assign(this.projectForm, this.projects[index]);
    },
    //详情返回列表
    toList() {
      this.showList = true;
    },
    //搜索
    search() {
      let that = this;
      this.$ajax
        .get("http://localhost:8081/project/find/" + this.searchid)
        .then(function (res) {
          if (res.data) {
            Object.assign(that.projectForm, res.data);
            that.showList = false;
          } else {
            that.$message("该志愿者项目不存在");
          }
        })
        .catch((err) => console.log(err));
    },
    //分页改变
    page(currentPage) {
      let that = this;
      this.$ajax
        .get("http://localhost:8081/project/findAll/" + currentPage + "/6")
        .then((res) => {
          that.projects = res.data.content;
          that.total = res.data.totalElements;
        })
        .catch((error) => console.log(error));
    },
  },
  //开始时获取全部项目
  created() {
    let that = this;
    this.$ajax
      .get("http://localhost:8081/project/findAll/1/6")
      .then((res) => {
        that.projects = res.data.content;
        that.total = res.data.totalElements;
      })
      .catch((error) => console.log(error));
  },
};
</script>

<style>
#project {
  background: #fcfaf1;
  width: 94%;
  height: 94%;
  padding: 3%;
  border-radius: 25px;
  box-shadow: 0px 0px 10px 5px rgba(0, 0, 0, 0.2);
}
.boxTitle {
  font-size: 2rem;
  font-weight: 700;
  margin-bottom: 2%;
  overflow: hidden;
  width: 90%;
}
#project .el-button {
  float: right;
  margin-top: -5%;
  margin-right: 2%;
}
#project h5 {
  font-size: 16px;
  margin: 10px;
}
.el-icon-s-flag {
  margin-right: 20px;
}
#project a {
  margin: 10px 5px 10px 10px;
  overflow: hidden;
}
.search {
  display: flex;
  padding: 0 5%;
  margin-bottom: 2%;
}
#project .search .el-button {
  margin: 0;
}
#project .el-pagination {
  text-align: center;
  margin-top: 1%;
}
#project .btn-prev,
#project .el-pager .number,
#project .btn-next {
  background: transparent !important;
}
#project .btn-prev:hover,
#project .el-pager .number:hover,
#project .btn-next:hover,
#project .el-pager .active {
  color: #ff7f41 !important;
}
#project .el-timeline {
  width: 100%;
  height: 75%;
  overflow-y: scroll;
}
#project .el-timeline-item {
  width: 95%;
  margin: 0 auto;
}
.coverBox {
  position: absolute;
  z-index: 999;
  width: 101vw;
  height: 99.5vh;
  top: -15vh;
  left: -9vw;
  background: rgba(0, 0, 0, 0.3);
}
.messageBox {
  position: absolute;
  width: 40%;
  min-height: 40%;
  max-height: 80%;
  background: #fff;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  padding: 3% 3% 0 3%;
}
.coverBox .el-textarea__inner {
  font-size: 16px !important;
}
.coverBox .el-form-item:last-child {
  margin-top: 8%;
}
.coverBox .el-textarea__inner {
  font-size: 14px !important;
}
#project textarea {
  font-family: Arial, Helvetica, sans-serif;
  font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB",
    "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
}
.el-input__inner:focus,
.el-textarea__inner:focus {
  border-color: #ff7f41;
}
.deleteBox {
  position: absolute;
  width: 28%;
  height: 15%;
  background: #fff;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  padding: 3% 3% 0 3%;
}
.deleteContent {
  font-size: 18px;
  width: 100%;
}
.deleteContent i {
  font-size: 25px;
}
.deleteBuntton {
  position: relative;
  top: 45%;
  left: 5%;
}
h5 {
  cursor: pointer;
}
.details {
  white-space: pre-wrap;
  line-height: 20px;
}
.details h4 {
  text-align: center;
  font-size: 20px;
  margin: 10px 0;
}
</style>