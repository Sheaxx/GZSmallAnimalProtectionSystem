<template>
  <div id="adoption">
    <h4 class="boxTitle"><i class="el-icon-s-claim"></i>领养</h4>
    <el-button
      type="warning"
      @click="openAddMessage"
      v-if="showList"
      class="topButton"
      >添加领养信息</el-button
    >
    <el-button type="warning" @click="toList" v-else class="topButton"
      >返回列表</el-button
    >
    <div class="search" v-show="showList">
      <el-input placeholder="请输入领养信息编号" v-model="searchid"></el-input>
      <el-button type="warning" icon="el-icon-search" @click="search"
        >搜索</el-button
      >
    </div>
    <ul id="adoptionList" v-show="showList">
      <li
        v-for="(item, index) in adoptions"
        :key="index"
        @click="toDetails(index)"
      >
        <img :src="adoptionImages[index].data" />
        <h5>{{ item.title }}</h5>
      </li>
    </ul>
    <el-pagination
      layout="prev, pager, next"
      :total="total"
      :page-size="8"
      @current-change="page"
      v-show="showList"
    >
    </el-pagination>

    <div class="coverBox" v-show="showMessage">
      <div class="messageBox">
        <el-form ref="adoptionForm" :model="adoptionForm" label-width="3em">
          <el-form-item label="标题">
            <el-input v-model="adoptionForm.title"></el-input>
          </el-form-item>
          <el-form-item label="内容">
            <el-input
              type="textarea"
              v-model="adoptionForm.content"
              rows="10"
              resize="none"
              show-word-limit
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-upload
              action="#"
              list-type="picture-card"
              :on-remove="handleRemove"
              :on-change="handleChange"
              :show-file-list="true"
              :auto-upload="false"
              :limit="1"
              :class="{ hide: notShowUpload }"
            >
              <img v-if="imgUrl" :src="imgUrl" />
              <i v-else class="el-icon-plus"></i>
            </el-upload>
            <!-- <el-dialog :visible.sync="dialogVisible">
              <img width="100%" :src="dialogImageUrl" alt="" />
            </el-dialog> -->
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
          <span>确定删除该条领养信息吗？</span>
        </div>
        <div class="deleteButton">
          <el-button type="warning" plain @click="cancelDelete">取消</el-button>
          <el-button type="warning" @click="deleteadoption">确定</el-button>
        </div>
      </div>
    </div>

    <div class="details" v-show="!showList">
      <h4>NO.{{adoptionForm.adoptionid}}：{{ title }}</h4>
      <div class="image">
        <img v-if="adoptionImage.data" :src="imgUrl" alt="小动物照片" />
      </div>
      <p>{{ content }}</p>
      <div
        class="detailsAdopter"
        v-if="
          adoptionForm.adopter &&
          ((adoptionForm.status == 1 &&
            adoptionForm.author == nowUser) ||
            $store.state.user.role == 2)
        "
      >
        <p>领养人用户名：{{ adoptionForm.adopter }}</p>
        <p>领养人真实姓名：{{ adopter.realname }}</p>
        <p>领养人联系电话：{{ adopter.tel }}</p>
        <p>领养人地址：{{ adopter.address }}</p>
        <p>领养时间：{{ adoptionForm.adopttime }}</p>
      </div>
      <div class="detailsButton">
        <el-button
          type="warning"
          @click="showAdopt = true"
          v-if="adoptionForm.author != nowUser"
          :disabled="adoptionForm.status == 1"
          >领养小动物</el-button
        >
        <el-button
          type="warning"
          plain
          @click="openUpdateMessage"
          v-if="
            adoptionForm.author == nowUser ||
            $store.state.user.role == 2
          "
          :disabled="adoptionForm.status == 1"
          >修改</el-button
        >
        <el-button
          type="warning"
          plain
          @click="openDeleteMessage"
          v-if="
            adoptionForm.author == nowUser ||
            $store.state.user.role == 2
          "
          :disabled="adoptionForm.status == 1"
          >删除</el-button
        >
      </div>
      <el-divider><i class="el-icon-chat-line-round"></i></el-divider>
      <el-timeline v-if="comments.length" class="comment">
        <el-timeline-item
          placement="top"
          v-for="(item, index) in comments"
          :key="index"
          :timestamp="item.createtime"
        >
          <el-card>
            <h6 class="commentAuthor">{{ item.author }}：</h6>
            <p class="commentContent">{{ item.content }}</p>
          </el-card>
        </el-timeline-item>
      </el-timeline>
      <el-input
        type="textarea"
        v-model="commentForm.content"
        rows="5"
        resize="none"
        class="commentEdit"
      ></el-input>
      <div>
        <el-button type="warning" plain class="addComment" @click="addComment"
          >发布评论</el-button
        >
      </div>
    </div>

    <div class="coverBox" v-show="showAdopt">
      <div class="adoptBox">
        <div class="adoptContent">
          <i class="el-icon-warning-outline"></i>
          <span>请对小动物负责，确定领养小动物吗？</span>
        </div>
        <div class="adoptButton">
          <el-button type="warning" plain @click="showAdopt = false"
            >取消</el-button
          >
          <el-button type="warning" @click="adopt">确定</el-button>
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
      notShowUpload: false,
      showAdopt: false,
      isAdd: false,
      currentIndex: 0,
      total: 0,
      searchid: null,
      adoptions: [],
      adoptionForm: {
        adoptionid: "",
        title: "",
        content: "",
        status: "",
        author: "",
        createtime: "",
        lastmodefiedtime: "",
        adopter: "",
        adopttime: "",
      },
      adopter: {
        username: "",
        realname: "",
        tel: "",
        address: "",
      },
      adoptionImages: [],
      adoptionImage: {
        imageid: null,
        data: "",
        adoptionid: null,
      },
      comments: [],
      commentForm: {
        commentid: null,
        content: "",
        author: "",
        createtime: "",
        adoptionid: null,
      },
      title: "",
      content: "",
      imgUrl: "",
    };
  },
  methods: {
    //删除照片
    handleRemove(file, fileList) {
      if (fileList.length < 1) {
        this.notShowUpload = false;
      }
      this.adoptionImage.data = "";
    },
    //照片上传按钮的动态显示
    handleChange(file, fileList) {
      if (fileList.length >= 1) {
        this.notShowUpload = true;
      } else {
        this.notShowUpload = false;
      }
      let reader = new FileReader();
      reader.readAsDataURL(file.raw);
      let that = this;
      reader.onload = function () {
        that.adoptionImage.data = reader.result;
      };
    },
    //打开添加领养信息界面
    openAddMessage() {
      for (let item in this.adoptionForm) {
        this.adoptionForm[item] = null;
      }
      for (let item in this.adoptionImage) {
        this.adoptionImage[item] = null;
      }
      this.imgUrl = null;
      this.isAdd = true;
      this.showMessage = true;
    },
    //打开修改领养信息界面
    openUpdateMessage() {
      this.isAdd = false;
      this.showMessage = true;
    },
    //打开删除领养信息提示
    openDeleteMessage() {
      this.showDelete = true;
    },
    //取消添加或修改
    cancelMessage() {
      this.showMessage = false;
      this.$message("取消发布");
    },
    //发布领养信息
    onSubmit() {
      let that = this;
      let obj = {};
      Object.assign(obj, this.adoptionForm);
      obj.author = this.nowUser;
      let img = {};
      Object.assign(img, this.adoptionImage);
      if (this.isAdd) {
        //新增领养信息
        obj.status = 0;
        this.$ajax
          .post("http://localhost:8081/adoption/save", obj)
          .then(function (res) {
            if (res.data) {
              obj.adoptionid = res.data;
              that.adoptions.push(obj);
              img.adoptionid = obj.adoptionid;
              that.$ajax
                .post("http://localhost:8081/adoptionImage/save", img)
                .then(function (res) {
                  if (res.data == "success") {
                    that.$router.go(0);
                    that.$message({
                      message: "发布成功",
                      type: "success",
                    });
                  }
                })
                .catch((err) => console.log(err));
            }
          })
          .catch((err) => console.log(err));
      } else {
        //修改领养信息
        obj.lastmodifiedtime = getNowTime();
        this.$ajax
          .put("http://localhost:8081/adoption/update", obj)
          .then(function (res) {
            if (res.data == "success") {
              that.$ajax
                .put("http://localhost:8081/adoptionImage/update", img)
                .then((res) => {
                  if (res.data == "success") {
                    that.adoptions[that.currentIndex] = obj;
                    that.title = obj.title;
                    that.content = obj.content;
                    that.imgUrl = img.data;
                    that.showMessage = false;
                    that.$message({
                      message: "修改成功",
                      type: "success",
                    });
                  }
                })
                .catch((err) => console.log(err));
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
    //删除领养信息
    deleteadoption() {
      let that = this;
      this.$ajax
        .delete(
          "http://localhost:8081/adoption/delete/" +
            this.adoptions[this.currentIndex].adoptionid
        )
        .then(function () {
          that.adoptions.splice(that.currentIndex, 1);
          that.showDelete = false;
          that.showList = true;
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
      Object.assign(this.adoptionForm, this.adoptions[index]);
      Object.assign(this.adoptionImage, this.adoptionImages[index]);
      this.title = this.adoptionForm.title;
      this.content = this.adoptionForm.content;
      this.imgUrl = this.adoptionImage.data;
      let that = this;
      if (this.adoptionForm.adopter) {
        this.$ajax
          .get("http://localhost:8081/user/find/" + this.adoptionForm.adopter)
          .then((res) => {
            //获取领养人信息
            that.adopter.username = res.data.username;
            that.adopter.realname = res.data.realname;
            that.adopter.tel = res.data.tel;
            that.adopter.address = res.data.address;
          })
          .catch((err) => console.log(err));
      }
      this.$ajax
        .get(
          "http://localhost:8081/comment/findAll/" +
            this.adoptionForm.adoptionid
        )
        .then(function (res) {
          //获取评论列表
          that.comments = res.data;
        })
        .catch((err) => console.log(err));
      for (let item in this.commentForm) {
        this.commentForm[item] = null;
      }
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
        .get("http://localhost:8081/adoption/find/" + this.searchid)
        .then(function (res) {
          if (res.data) {
            Object.assign(that.adoptionForm, res.data);
            that.$ajax
              .get(
                "http://localhost:8081/adoptionImage/find/" +
                  that.adoptionForm.adoptionid
              )
              .then((res) => {
                if (res.data) {
                  Object.assign(that.adoptionImage, res.data);
                  that.title = that.adoptionForm.title;
                  that.content = that.adoptionForm.content;
                  that.imgUrl = that.adoptionImage.data;
                  that.$ajax
                    .get(
                      "http://localhost:8081/comment/findAll/" +
                        that.adoptionForm.adoptionid
                    )
                    .then(function (res) {
                      //获取评论列表
                      that.comments = res.data;
                    })
                    .catch((err) => console.log(err));
                  for (let item in that.commentForm) {
                    that.commentForm[item] = null;
                  }
                  that.showList = false;
                }
              })
              .catch((err) => console.log(err));
          } else {
            that.$message("该领养信息不存在");
          }
        })
        .catch((err) => console.log(err));
    },
    //确认领养
    adopt() {
      let that = this;
      for (let item in this.$store.state.user) {
        if (
          this.$store.state.user[item] == null ||
          this.$store.state.user[item] == ""
        ) {
          this.$message("请先完善个人信息");
          return;
        }
      }
      this.$ajax
        .put(
          "http://localhost:8081/adoption/adopted/" +
            this.adoptionForm.adoptionid
        )
        .then(function (res) {
          if (res.data == "success") {
            that.$message.success("领养成功");
          } else {
            that.$message.error("领养失败");
          }
        })
        .catch((err) => console.log(err));
    },
    //发布评论
    addComment() {
      let that = this;
      let obj = {};
      Object.assign(obj, this.commentForm);
      obj.adoptionid = this.adoptionForm.adoptionid;
      obj.author = this.nowUser;
      this.$ajax
        .post("http://localhost:8081/comment/save", obj)
        .then(function (res) {
          if (res.data == "success") {
            that.comments.push(obj);
            that.$message.success("发布评论成功");
          }
        })
        .catch((err) => console.log(err));
    },
    //分页改变
    page(currentPage) {
      let that = this;
      this.$ajax
        .get("http://localhost:8081/adoption/findAll/" + currentPage + "/8")
        .then((res) => {
          that.adoptions = res.data.content;
          that.total = res.data.totalElements;
        })
        .catch((error) => console.log(error));
      this.$ajax
        .get(
          "http://localhost:8081/adoptionImage/findAll/" + currentPage + "/8"
        )
        .then((res) => {
          that.adoptionImages = res.data.content;
        })
        .catch((error) => console.log(error));
    },
  },
  //开始时获取全部领养信息
  created() {
    this.nowUser = window.localStorage.getItem("username");
    let that = this;
    this.$ajax
      .get("http://localhost:8081/adoption/findAll/1/8")
      .then((res) => {
        that.adoptions = res.data.content;
        that.total = res.data.totalElements;
      })
      .catch((error) => console.log(error));
    this.$ajax
      .get("http://localhost:8081/adoptionImage/findAll/1/8")
      .then((res) => {
        that.adoptionImages = res.data.content;
      })
      .catch((error) => console.log(error));
  },
};
</script>

<style>
#adoption {
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
#adoption .el-icon-s-claim {
  margin-right: 20px;
}
.search {
  display: flex;
  padding: 0 5%;
  margin-bottom: 2%;
}
#adoption .search .el-button {
  margin: 0;
}
#adoptionList {
  display: flex;
  flex-wrap: wrap;
  width: 90%;
  height: 73%;
  margin: 0 auto;
  overflow-y: auto;
}
#adoptionList li {
  width: 21%;
  margin: 0.5%;
  margin-top: 0;
  flex: 0 1 auto;
  background: #fff;
  padding: 1%;
  text-align: center;
  border-radius: 5px;
  border: 1px solid rgba(0, 0, 0, 0.1);
}
#adoptionList li img {
  width: 100%;
  margin-bottom: 5px;
}
#adoption .el-pagination {
  text-align: center;
  margin-top: 1%;
}
#adoption .btn-prev,
#adoption .el-pager .number,
#adoption .btn-next {
  background: transparent !important;
}
#adoption .btn-prev:hover,
#adoption .el-pager .number:hover,
#adoption .btn-next:hover,
#adoption .el-pager .active {
  color: #ff7f41 !important;
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
  width: 45%;
  min-height: 40%;
  max-height: 100%;
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
  margin-left: 70%;
}
#adoption textarea {
  font-family: Arial, Helvetica, sans-serif;
  font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB",
    "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
}
.el-input__inner:focus,
.el-textarea__inner:focus {
  border-color: #ff7f41;
}
.hide .el-upload--picture-card {
  display: none;
}
.el-upload--picture-card:hover,
.el-upload--picture-card:focus {
  border-color: #ff7f41;
}
.el-upload--picture-card {
  background: #fffffd !important;
}
.messageBox img {
  width: 148px;
  height: 148px;
  overflow: hidden;
}
.messageBox .el-button:last-of-type {
  position: absolute;
}
.deleteBox,
.adoptBox {
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
.adoptContent {
  font-size: 18px;
  width: 100%;
}
.deleteContent i,
.adoptContent i {
  font-size: 25px;
}
.deleteButton,
.adoptButton {
  position: relative;
  top: 30%;
  left: 70%;
}
h5 {
  cursor: pointer;
}
.details {
  white-space: pre-wrap;
  line-height: 20px;
  padding: 2%;
  overflow: auto;
  height: 83%;
}
.details .image {
  display: flex;
  justify-content: center;
  align-items: center;
}
.details img {
  margin: 20px 0;
  width: 400px;
}
.details h4 {
  text-align: center;
  font-size: 20px;
  margin: 10px 0;
}
.details h5 {
  margin: 10px 5%;
}
.el-divider__text {
  background: #fcfaf1;
  font-size: 18px;
}
.el-divider {
  margin: 50px 0;
}
.details .el-timeline {
  padding: 0 5%;
}
.comment h6 {
  font-size: 13px;
  color: grey;
}
.comment p {
  font-size: 14px;
}
.commentEdit {
  width: 80%;
  margin-top: 20px;
  margin-left: 10%;
}
.detailsButton {
  clear: both;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 20px;
}
.addComment {
  float: right;
  margin-top: 10px;
  margin-right: 10%;
}
.detailsAdopter {
  margin-top: 20px;
}
</style>