<template>
  <div id="information">
    <h4 class="boxTitle"><i class="el-icon-s-management"></i>小动物科普</h4>
    <el-button
      type="warning"
      @click="openAddMessage"
      v-show="showList && $store.state.user.role == 2"
      class="topButton"
      >添加科普信息</el-button
    >
    <el-button
      type="warning"
      @click="toList"
      v-show="!showList"
      class="topButton"
      >返回列表</el-button
    >
    <div class="search" v-show="showList">
      <el-input placeholder="请输入科普信息编号" v-model="searchid"></el-input>
      <el-button type="warning" icon="el-icon-search" @click="search"
        >搜索</el-button
      >
    </div>
    <ul id="informationList" v-show="showList">
      <li
        v-for="(item, index) in informations"
        :key="index"
        @click="toDetails(index)"
      >
        <img :src="informationImages[index].data" />
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
        <el-form
          ref="informationForm"
          :model="informationForm"
          label-width="3em"
        >
          <el-form-item label="标题">
            <el-input v-model="informationForm.title"></el-input>
          </el-form-item>
          <el-form-item label="内容">
            <el-input
              type="textarea"
              v-model="informationForm.content"
              rows="8"
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
          <span>确定删除该科普信息吗？</span>
        </div>
        <div class="deleteButton">
          <el-button type="warning" plain @click="cancelDelete">取消</el-button>
          <el-button type="warning" @click="deleteinformation">确定</el-button>
        </div>
      </div>
    </div>

    <div class="details" v-show="!showList">
      <h4>{{ title }}</h4>
      <div class="image">
        <img v-if="informationImage.data" :src="imgUrl" alt="科普信息照片" />
      </div>
      <p>{{ content }}</p>
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
export default {
  data() {
    return {
      showList: true,
      showMessage: false,
      showDelete: false,
      notShowUpload: false,
      isAdd: false,
      currentIndex: 0,
      total: 0,
      searchid: null,
      informations: [],
      informationForm: {
        informationid: 0,
        title: "",
        content: "",
      },
      informationImages: [],
      informationImage: {
        imageid: null,
        data: "",
        informationid: null,
      },
      title: "",
      content: "",
      imgUrl: "",
    };
  },
  methods: {
    //删除照片
    handleRemove(file, fileList) {
      if (fileList.length < 8) {
        this.notShowUpload = false;
      }
      this.informationImage.data = "";
    },
    //照片上传按钮的动态显示
    handleChange(file, fileList) {
      if (fileList.length >= 8) {
        this.notShowUpload = true;
      } else {
        this.notShowUpload = false;
      }
      let reader = new FileReader();
      reader.readAsDataURL(file.raw);
      let that = this;
      reader.onload = function () {
        that.informationImage.data = reader.result;
      };
    },
    //打开添加科普信息界面
    openAddMessage() {
      for (let item in this.informationForm) {
        this.informationForm[item] = null;
      }
      for (let item in this.informationImage) {
        this.informationImage[item] = null;
      }
      this.imgUrl = null;
      this.isAdd = true;
      this.showMessage = true;
    },
    //打开修改科普信息界面
    openUpdateMessage(index) {
      Object.assign(this.informationForm, this.informations[index]);
      this.isAdd = false;
      this.currentIndex = index;
      this.showMessage = true;
    },
    //打开删除科普信息提示
    openDeleteMessage(index) {
      this.currentIndex = index;
      this.showDelete = true;
    },
    //取消添加或修改
    cancelMessage() {
      this.showMessage = false;
      this.$message("取消发布");
    },
    //发布科普信息
    onSubmit() {
      let that = this;
      let obj = {};
      Object.assign(obj, this.informationForm);
      let img = {};
      Object.assign(img, this.informationImage);
      if (that.isAdd) {
        //新增科普信息
        this.$ajax
          .post("http://localhost:8081/information/save", obj)
          .then(function (res) {
            if (res.data) {
              obj.informationid = res.data;
              that.informations.push(obj);
              img.informationid = obj.informationid;
              that.$ajax
                .post("http://localhost:8081/informationImage/save", img)
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
        //修改科普信息
        obj.informationid = this.informations[this.currentIndex].informationid;
        this.$ajax
          .put("http://localhost:8081/information/update", obj)
          .then(function (res) {
            if (res.data == "success") {
              that.informations[that.currentIndex] = obj;
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
    },
    //取消删除
    cancelDelete() {
      this.showDelete = false;
      this.$message("取消删除");
    },
    //删除科普信息
    deleteinformation() {
      let that = this;
      this.$ajax
        .delete(
          "http://localhost:8081/information/delete/" +
            this.informationForm.informationid
        )
        .then(function () {
          that.informations.splice(that.currentIndex, 1);
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
      Object.assign(this.informationForm, this.informations[index]);
      Object.assign(this.informationImage, this.informationImages[index]);
      this.title = this.informationForm.title;
      this.content = this.informationForm.content;
      this.imgUrl = this.informationImage.data;
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
        .get("http://localhost:8081/information/find/" + this.searchid)
        .then(function (res) {
          if (res.data) {
            Object.assign(that.informationForm, res.data);
            that.$ajax
              .get(
                "http://localhost:8081/informationImage/find/" +
                  that.informationForm.informationid
              )
              .then((res) => {
                if (res.data) {
                  Object.assign(that.informationImage, res.data);
                  that.title = that.informationForm.title;
                  that.content = that.informationForm.content;
                  that.imgUrl = that.informationImage.data;
                  that.showList = false;
                }
              })
              .catch((err) => console.log(err));
          } else {
            that.$message("该科普信息不存在");
          }
        })
        .catch((err) => console.log(err));
    },
    //分页改变
    page(currentPage) {
      let that = this;
      this.$ajax
        .get("http://localhost:8081/information/findAll/" + currentPage + "/8")
        .then((res) => {
          that.informations = res.data.content;
          that.total = res.data.totalElements;
        })
        .catch((error) => console.log(error));
      this.$ajax
        .get(
          "http://localhost:8081/informationImage/findAll/" + currentPage + "/8"
        )
        .then((res) => {
          that.informationImages = res.data.content;
        })
        .catch((error) => console.log(error));
    },
  },
  //开始时获取全部科普信息
  created() {
    let that = this;
    this.$ajax
      .get("http://localhost:8081/information/findAll/1/8")
      .then((res) => {
        that.informations = res.data.content;
        that.total = res.data.totalElements;
      })
      .catch((error) => console.log(error));
    this.$ajax
      .get("http://localhost:8081/informationImage/findAll/1/8")
      .then((res) => {
        that.informationImages = res.data.content;
      })
      .catch((error) => console.log(error));
  },
};
</script>

<style>
#information {
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
.el-icon-s-management {
  margin-right: 20px;
}
.search {
  display: flex;
  padding: 0 5%;
  margin-bottom: 2%;
}
#information .search .el-button {
  margin: 0;
}
#informationList {
  display: flex;
  flex-wrap: wrap;
  width: 90%;
  height: 73%;
  margin: 0 auto;
  overflow-y: auto;
}
#informationList li {
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
#informationList li img {
  width: 100%;
  margin-bottom: 5px;
}
#information .el-pagination {
  text-align: center;
  margin-top: 1%;
}
#information .btn-prev,
#information .el-pager .number,
#information .btn-next {
  background: transparent !important;
}
#information .btn-prev:hover,
#information .el-pager .number:hover,
#information .btn-next:hover,
#information .el-pager .active {
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
#information textarea {
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
  white-space: pre-wrap;
  line-height: 20px;
  height: 83%;
}
.details .image {
  display: flex;
  justify-content: center;
  align-items: center;
}
.details img {
  margin: 20px 0;
}
.details h4 {
  text-align: center;
  font-size: 20px;
  margin: 10px 0;
}
.detailsButton {
  clear: both;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>