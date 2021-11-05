<template>
  <div id="notice">
    <h4 class="boxTitle"><i class="el-icon-message-solid"></i>公告</h4>
    <el-button
      type="warning"
      @click="openAddMessage"
      v-show="showList && $store.state.user.role == 2"
      class="topButton"
      >添加公告</el-button
    >
    <el-button type="warning" @click="toList" v-show="!showList" class="topButton"
      >返回列表</el-button
    >
    <div class="search" v-show="showList">
      <el-input placeholder="请输入公告编号" v-model="searchid"></el-input>
      <el-button type="warning" icon="el-icon-search" @click="search"
        >搜索</el-button
      >
    </div>
    <el-timeline v-show="showList">
      <el-timeline-item
        placement="top"
        color="#ff7f41"
        v-for="(item, index) in notices"
        :key="index"
        :timestamp="item.createtime"
      >
        <el-card>
          <h5 @click="toDetails(index)">
            NO.{{ item.noticeid }}：{{ item.title }}
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
        <el-form ref="noticeForm" :model="noticeForm" label-width="3em">
          <el-form-item label="标题">
            <el-input v-model="noticeForm.title"></el-input>
          </el-form-item>
          <el-form-item label="内容">
            <el-input
              type="textarea"
              v-model="noticeForm.content"
              rows="17"
              resize="none"
              show-word-limit
            ></el-input>
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
        <div class="deleteButton">
          <el-button type="warning" plain @click="cancelDelete">取消</el-button>
          <el-button type="warning" @click="deleteNotice">确定</el-button>
        </div>
      </div>
    </div>

    <div class="details" v-show="!showList">
      <h4>{{ title }}</h4>
      <p>{{ content }}</p>
      <div class="itemTimes">
        <p class="detailsCreateTime">发布时间：{{ noticeForm.createtime }}</p>
        <p class="detailsLastTime">
          最后修改时间：{{ noticeForm.lastmodifiedtime }}
        </p>
      </div>
      <div class="detailsButton">
        <el-button
          type="warning"
          plain
          @click="openUpdateMessage"
          v-if="$store.state.user.role == 2"
          >修改</el-button
        >
        <el-button
          type="warning"
          plain
          @click="openDeleteMessage"
          v-if="$store.state.user.role == 2"
          >删除</el-button
        >
      </div>
    </div>
  </div>
</template>

<script>
import getNowTime from "../utils/date";

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
      notices: [],
      noticeForm: {
        noticeid: 0,
        title: "",
        content: "",
        createtime: "",
        lastmodifiedtime: "",
      },
      title:"",
      content:""
    };
  },
  methods: {
    //打开添加公告界面
    openAddMessage() {
      for (let item in this.noticeForm) {
        this.noticeForm[item] = null;
      }
      this.isAdd = true;
      this.showMessage = true;
    },
    //打开修改公告界面
    openUpdateMessage() {
      Object.assign(this.noticeForm, this.notices[index]);
      this.isAdd = false;
      this.showMessage = true;
    },
    //打开删除公告提示
    openDeleteMessage() {
      this.showDelete = true;
    },
    //取消添加或修改
    cancelMessage() {
      this.showMessage = false;
      this.$message("取消发布");
    },
    //发布公告
    onSubmit() {
      let that = this;
      let obj = {};
      Object.assign(obj,this.noticeForm);
      obj.author = window.localStorage.getItem("username");
      if (this.isAdd) {
        //新增公告
        this.$ajax
          .post("http://localhost:8081/notice/save", obj)
          .then(function (res) {
            if (res.data == "success") {
              that.notices.unshift(obj);
              that.showMessage = false;
              that.$message({
                message: "发布成功",
                type: "success",
              });
            }
          })
          .catch((err) => console.log(err));
      } else {
        //修改公告
        obj.lastmodifiedtime = getNowTime();
        this.$ajax
          .put("http://localhost:8081/notice/update", obj)
          .then(function (res) {
            if (res.data == "success") {
              that.notices[that.currentIndex] = obj;
              that.title = obj.title;
              that.content = obj.content;
              that.showMessage = false;
              that.$message({
                message: "修改成功",
                type: "success",
              });
            }
          })
          .catch((err) => console.log(err));
      }
    },
    //取消删除
    cancelDelete() {
      this.showDelete = false;
      this.$message("取消删除");
    },
    //删除公告
    deleteNotice() {
      let that = this;
      this.$ajax
        .delete(
          "http://localhost:8081/notice/delete/" +
            this.notices[this.currentIndex].noticeid
        )
        .then(function () {
          that.notices.splice(that.currentIndex, 1);
          that.showDelete = false;
          that.$message({
            message: "删除成功",
            type: "success",
          });
          that.toList();
        })
        .catch((err) => console.log(err));
    },
    //查看详情
    toDetails(index) {
      this.currentIndex = index;
      Object.assign(this.noticeForm, this.notices[index]);
      this.title = this.noticeForm.title;
      this.content = this.noticeForm.content;
      this.showList = false;
    },
    //详情返回列表
    toList() {
      this.showList = true;
    },
    //搜索
    search() {
      let that = this;
      this.$ajax
        .get("http://localhost:8081/notice/find/" + this.searchid)
        .then(function (res) {
          if (res.data) {
            Object.assign(that.noticeForm, res.data);
            that.title = that.noticeForm.title;
            that.content = that.noticeForm.content;
            that.showList = false;
          } else {
            that.$message("该公告不存在");
          }
        })
        .catch((err) => console.log(err));
    },
    //分页改变
    page(currentPage) {
      let that = this;
      this.$ajax
        .get("http://localhost:8081/notice/findAll/" + currentPage + "/6")
        .then((res) => {
          that.notices = res.data.content;
          that.total = res.data.totalElements;
        })
        .catch((error) => console.log(error));
    },
  },
  //开始时获取全部公告
  created() {
    let that = this;
    this.$ajax
      .get("http://localhost:8081/notice/findAll/1/6")
      .then((res) => {
        that.notices = res.data.content;
        that.total = res.data.totalElements;
      })
      .catch((error) => console.log(error));
  },
};
</script>

<style>
#notice {
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
.topButton {
  float: right;
  margin-top: -5%;
  margin-right: 2%;
}
#notice h5 {
  font-size: 16px;
  margin: 10px;
}
.el-icon-message-solid {
  margin-right: 20px;
}
#notice a {
  margin: 10px 5px 10px 10px;
  overflow: hidden;
}
.search {
  display: flex;
  padding: 0 5%;
  margin-bottom: 2%;
}
#notice .search .el-button {
  margin: 0;
}
#notice .el-pagination {
  text-align: center;
  margin-top: 1%;
}
#notice .btn-prev,
#notice .el-pager .number,
#notice .btn-next {
  background: transparent !important;
}
#notice .btn-prev:hover,
#notice .el-pager .number:hover,
#notice .btn-next:hover,
#notice .el-pager .active {
  color: #ff7f41 !important;
}
#notice .el-timeline {
  width: 100%;
  height: 75%;
  overflow: auto;
}
#notice .el-timeline-item {
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
  height: 70%;
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
  margin-left: 80%;
}
.coverBox textarea {
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
.deleteButton {
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
.details h4 {
  text-align: center;
  font-size: 20px;
  margin: 15px 0;
}
.details p {
  margin: 20px 0;
  clear: both;
  white-space: pre-wrap;
  line-height: 20px;
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