<template>
  <div id="notice">
    <h4 class="boxTitle"><i class="el-icon-message-solid"></i>公告</h4>
    <el-button type="warning" @click="openAddMessage" v-if="showList">添加公告</el-button>
    <el-button type="warning" @click="toList" v-else>返回列表</el-button>
    <el-timeline v-show="showList">
      <el-timeline-item
        placement="top"
        color="#ff7f41"
        v-for="(item, index) in notices"
        :key="index"
        :timestamp="item.createtime"
      >
        <el-card>
          <h5 @click="toDetails">{{ item.title }}</h5>
          <div class="option">
            <a href="" @click.prevent="openUpdateMessage(index)">修改</a>
            <a href="" @click.prevent="openDeleteMessage(index)">删除</a>
          </div>
        </el-card>
      </el-timeline-item>
    </el-timeline>
    <el-pagination layout="prev, pager, next" :total="50" v-show="showList"> </el-pagination>

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
              rows="10"
              resize="none"
              maxlength="500"
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
        <div class="deleteBuntton">
          <el-button type="warning" plain @click="cancelDelete">取消</el-button>
          <el-button type="warning" @click="deleteNotice">确定</el-button>
        </div>
      </div>
    </div>

    <div class="details" v-show="!showList">
      <h4>{{noticeForm.title}}</h4>
      <p>{{noticeForm.content}}</p>
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
      notices: [
        {
          title: "1kkkkkkkkkk",
          createtime: "2021-10-13 18:35:00",
        },
        {
          title: "2hhhhhhhhhhhhhhhhhhhhh",
          createtime: "2021-10-13 18:35:00",
        },
        {
          title: "3hhhhhhhhhhhhhhhhhhhhh",
          createtime: "2021-10-13 18:35:00",
        },
        {
          title: "4hhhhhhhhhhhhhhhhhhhhh",
          createtime: "2021-10-13 18:35:00",
        },
        {
          title: "5hhhhhhhhhhhhhhhhhhhhh",
          createtime: "2021-10-13 18:35:00",
        },
        {
          title: "6hhhhhhhhhhhhhhhhhhhhh",
          createtime: "2021-10-13 18:35:00",
        },
      ],
      noticeForm: {
        title: "6hhhhhhhhhhhhhhhhhhhhh",
        content:
          "hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh",
      },
    };
  },
  methods: {
    //打开添加公告界面
    openAddMessage() {
      this.noticeForm.title = "";
      this.noticeForm.content = "";
      this.isAdd = true;
      this.showMessage = true;
    },
    //打开修改公告界面
    openUpdateMessage(index) {
      this.noticeForm.title = this.notices[index].title;
      this.noticeForm.content = this.notices[index].content;
      this.isAdd = false;
      this.currentIndex = index;
      this.showMessage = true;
    },
    //打开删除公告提示
    openDeleteMessage(index) {
      this.currentIndex = index;
      this.showDelete = true;
    },
    //取消添加或修改
    cancelMessage() {
      this.showMessage = false;
      this.$message("取消发布");
    },
    //发布公告
    onSubmit() {
      let obj = {};
      obj.title = this.noticeForm.title;
      obj.content = this.noticeForm.content;
      if (this.isAdd) {
        this.notices.unshift(obj);
      } else {
        this.notices[this.currentIndex] = obj;
      }
      this.showMessage = false;
      this.$message({
        message: "发布成功",
        type: "success",
      });
    },
    //取消删除
    cancelDelete() {
      this.showDelete = false;
      this.$message("取消删除");
    },
    //删除公告
    deleteNotice() {
      this.notices.splice(this.currentIndex, 1);
      this.showDelete = false;
      this.$message({
        message: "删除成功",
        type: "success",
      });
    },
    //查看详情
    toDetails() {
      this.showList = false;
    },
    //详情返回列表
    toList() {
      this.showList = true;
    },
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
  margin-bottom: 3%;
  overflow: hidden;
  width: 90%;
}
#notice .el-button {
  float: right;
  margin-top: -6%;
  margin-right: 2%;
}
#notice h5 {
  font-size: 16px;
  margin: 10px;
}
#notice i {
  margin-right: 20px;
}
#notice a {
  margin: 10px 5px 10px 10px;
  overflow: hidden;
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
  height: 80%;
  overflow-y: scroll;
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
  top: -20vh;
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
.deleteBuntton {
  position: relative;
  top: 45%;
  left: 5%;
}
h5 {
  cursor: pointer;
}
.details h4{
  font-size: 20px;
  margin: 10px 0;
}
</style>