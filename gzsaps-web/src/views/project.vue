<template>
  <div id="project">
    <h4 class="boxTitle"><i class="el-icon-s-flag"></i>志愿者项目</h4>
    <el-button
      type="warning"
      @click="toAudit"
      v-show="showList && $store.state.user.role == 2"
      class="topLeftButton"
      >审核未发布项目
    </el-button>
    <el-button
      type="warning"
      @click="openAddMessage"
      v-show="(showList || showAudit) && $store.state.user.role != 0"
      class="topButton"
      >添加项目</el-button
    >
    <el-button
      type="warning"
      @click="toAll"
      v-show="showAudit && $store.state.user.role == 2"
      class="topLeftButton"
      >返回查看全部
    </el-button>
    <el-button
      type="warning"
      @click="toList"
      v-show="!showList && !showAudit"
      class="topButton"
      >返回列表</el-button
    >
    <div class="search" v-show="showList || showAudit">
      <el-input
        placeholder="请输入志愿者项目编号"
        v-model="searchid"
      ></el-input>
      <el-button type="warning" icon="el-icon-search" @click="search"
        >搜索</el-button
      >
    </div>
    <el-timeline v-show="showList || showAudit">
      <el-timeline-item
        placement="top"
        color="#ff7f41"
        v-for="(item, index) in projects"
        :key="index"
        :timestamp="item.createtime"
        v-if="showList || (showAudit && item.status == 0)"
      >
        <el-card>
          <h5 @click="toDetails(index)">
            NO.{{ item.projectid }}：{{ item.title }}
          </h5>
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
              rows="8"
              resize="none"
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
          <span>确定删除该志愿者项目吗？</span>
        </div>
        <div class="deleteButton">
          <el-button type="warning" plain @click="cancelDelete">取消</el-button>
          <el-button type="warning" @click="deleteproject">确定</el-button>
        </div>
      </div>
    </div>

    <div class="details" v-show="!showList && !showAudit">
      <h4>{{ title }}</h4>
      <p class="detailsContent">{{ content }}</p>
      <p class="detailsAddress">活动地址：{{ address }}</p>
      <p class="detailsTime">
        活动时间：{{ projectForm.begintime }} - {{ projectForm.endtime }}
      </p>
      <p class="detailsPrincipal">负责人：{{ principal }}</p>
      <p class="detailsPrincipalTel">负责人联系方式：{{ principaltel }}</p>
      <p
        class="datails"
        v-if="projectForm.author == nowUser"
      >
        已报名用户：{{ projectForm.applicantlist }}
      </p>
      <div class="itemTimes">
        <p class="detailsCreateTime">发布时间：{{ projectForm.createtime }}</p>
        <p class="detailsLastTime">
          最后修改时间：{{ projectForm.lastmodifiedtime }}
        </p>
      </div>
      <div class="detailsButton">
        <el-button
          type="warning"
          @click="apply"
          :disabled="projectForm.status != 1"
          >报名参加</el-button
        >
        <el-button
          type="warning"
          plain
          @click="openUpdateMessage"
          v-if="
            projectForm.author == nowUser ||
            $store.state.user.role == 2
          "
          >修改</el-button
        >
        <el-button
          type="warning"
          plain
          @click="openDeleteMessage"
          v-if="
            projectForm.author == nowUser ||
            $store.state.user.role == 2
          "
          >删除</el-button
        >
        <el-button
          type="warning"
          plain
          @click="pass"
          v-if="$store.state.user.role == 2 && projectForm.status == 0"
        >
          审核通过
        </el-button>
        <el-button
          type="warning"
          plain
          @click="showCutoff = true"
          v-if="
            projectForm.author == nowUser &&
            projectForm.status == 1
          "
          >截止报名</el-button
        >
      </div>
    </div>

    <div class="coverBox" v-show="showCutoff">
      <div class="cutoffBox">
        <div class="cutoffContent">
          <i class="el-icon-warning-outline"></i>
          <span>确定截止报名吗？</span>
        </div>
        <div class="cutoffButton">
          <el-button type="warning" plain @click="showCutoff = false"
            >取消</el-button
          >
          <el-button type="warning" @click="cutoff">确定</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import getNowTime from "../utils/date";

export default {
  data() {
    return {
      nowUser:"",
      showList: true,
      showMessage: false,
      showDelete: false,
      showCutoff: false,
      showAudit: false,
      fromList: true,
      isAdd: false,
      currentIndex: 0,
      total: 0,
      searchid: null,
      time: [],
      projects: [],
      AuditProjects: [],
      projectForm: {
        projectid: null,
        title: "",
        content: "",
        address: "",
        begintime: "",
        endtime: "",
        principal: "",
        principaltel: "",
        createtime: "",
        lastmodifiedtime: "",
        applicantlist: "",
      },
      title: "",
      content: "",
      address: "",
      principal: "",
      principaltel: "",
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
    openUpdateMessage() {
      Object.assign(this.projectForm, this.projects[this.currentIndex]);
      this.time[0] = this.projectForm.begintime;
      this.time[1] = this.projectForm.endtime;
      this.isAdd = false;
      this.showMessage = true;
    },
    //打开删除项目提示
    openDeleteMessage() {
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
      Object.assign(obj, this.projectForm);
      obj.begintime = this.time[0];
      obj.endtime = this.time[1];
      obj.author = this.nowUser;
      if (this.isAdd) {
        //新增项目
        if (this.$store.state.user.role == 1) {
          //志愿者身份
          obj.status = 0;
        } else if (this.$store.state.user.role == 2) {
          //管理员身份
          obj.status = 1;
        }
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
        obj.lastmodifiedtime = getNowTime();
        obj.applicantlist = this.projects[this.currentIndex].applicantlist;
        obj.status = this.projects[this.currentIndex].status;
        this.$ajax
          .put("http://localhost:8081/project/update", obj)
          .then(function (res) {
            if (res.data == "success") {
              that.projects[that.currentIndex] = obj;
              that.title = obj.title;
              that.content = obj.content;
              that.address = obj.address;
              that.principal = obj.principal;
              that.principaltel = obj.principaltel;
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
          that.toList();
          that.$message({
            message: "删除成功",
            type: "success",
          });
        })
        .catch((err) => console.log(err));
    },
    //查看详情
    toDetails(index) {
      this.currentIndex = index;
      Object.assign(this.projectForm, this.projects[index]);
      this.title = this.projectForm.title;
      this.content = this.projectForm.content;
      this.address = this.projectForm.address;
      this.principal = this.projectForm.principal;
      this.principaltel = this.projectForm.principaltel;
      this.showList = false;
      this.showAudit = false;
    },
    //详情返回列表
    toList() {
      if (this.fromList) {
        this.showList = true;
        this.showAudit = false;
      } else {
        this.showList = false;
        this.showAudit = true;
      }
    },
    //搜索
    search() {
      let that = this;
      this.$ajax
        .get("http://localhost:8081/project/find/" + this.searchid)
        .then(function (res) {
          if (res.data) {
            Object.assign(that.projectForm, res.data);
            that.title = that.projectForm.title;
            that.content = that.projectForm.content;
            that.address = that.projectForm.address;
            that.principal = that.projectForm.principal;
            that.principaltel = that.projectForm.principaltel;
            that.showList = false;
          } else {
            that.$message("该志愿者项目不存在");
          }
        })
        .catch((err) => console.log(err));
    },
    //报名
    apply() {
      if (this.projectForm != 1) {
        this.$message("该项目无法报名");
      } else {
        let that = this;
        this.$ajax
          .put(
            "http://localhost:8081/project/apply/" +
              this.nowUser +
              "/" +
              this.projectForm.projectid
          )
          .then(function (res) {
            if (res.data == 0) {
              that.$message("您已经报名过该项目");
            } else {
              that.$message.success("报名成功！");
            }
          })
          .catch((err) => console.log(err));
      }
    },
    //截止报名
    cutoff() {
      let that = this;
      this.$ajax
        .put(
          "http://localhost:8081/project/cutoff/" + this.projectForm.projectid
        )
        .then(function (res) {
          if (res.data == "success") {
            that.projectForm.status = 2;
            that.projects[that.currentIndex].status = 2;
            that.$message.success("已截止报名");
          } else {
            that.$message.error("截止报名失败");
          }
          that.showCutoff = false;
        })
        .catch((err) => console.log(err));
    },
    //管理员查看未审核项目
    toAudit() {
      this.showList = false;
      this.showAudit = true;
      this.fromList = false;
    },
    //返回查看全部
    toAll() {
      this.showList = true;
      this.showAudit = false;
      this.fromList = true;
    },
    //管理员审核项目通过
    pass() {
      let that = this;
      this.$ajax
        .put("http://localhost:8081/project/pass/" + this.projectForm.projectid)
        .then((res) => {
          if (res.data == "success") {
            that.projectForm.status = 1;
            that.projects[that.currentIndex].status = 1;
            that.$message.success("审核通过");
          } else {
            that.$message.error("操作失败");
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
    this.nowUser = window.localStorage.getItem("username");
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
.topLeftButton {
  float: right;
  margin-top: -5%;
  margin-right: 12%;
}
.topButton {
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
  overflow: auto;
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
  width: 70%;
  height: 82%;
  background: #fff;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  padding: 3% 3% 0 3%;
}
.coverBox .el-textarea__inner,
.coverBox .el-input__inner {
  font-size: 13px !important;
}
.coverBox .el-form-item:last-child {
  margin-left: 78%;
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
.messageBox .el-button:last-of-type {
  position: absolute;
}
.deleteBox,
.cutoffBox {
  position: absolute;
  width: 28%;
  height: 15%;
  background: #fff;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  padding: 3% 3% 0 3%;
}
.deleteContent,
.cutoffContent {
  font-size: 18px;
  width: 100%;
}
.deleteContent i,
.cutoffContent i {
  font-size: 25px;
}
.deleteButton,
.cutoffButton {
  position: relative;
  top: 30%;
  left: 70%;
}
h5 {
  cursor: pointer;
}
.details {
  overflow: auto;
  height: 83%;
}
.detailsContent {
  white-space: pre-wrap;
  line-height: 20px;
}
.details h4 {
  text-align: center;
  font-size: 20px;
  margin: 15px 0;
}
.details p {
  margin: 20px 0;
  clear: both;
}
.detailsAddress {
  margin-top: 40px !important;
}
.itemTimes {
  clear: both;
  float: right;
  margin-bottom: 10px;
}
.itemTimes p {
  font-size: 12px;
  margin: 5px;
  color: rgba(0, 0, 0, 0.8);
}
.detailsButton {
  clear: both;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>