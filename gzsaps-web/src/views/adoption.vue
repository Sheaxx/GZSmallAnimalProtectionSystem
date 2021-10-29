<template>
  <div id="adoption">
    <h4 class="boxTitle"><i class="el-icon-s-claim"></i>领养</h4>
    <el-button type="warning" @click="openAddMessage" v-if="showList"
      >添加领养信息</el-button
    >
    <el-button type="warning" @click="toList" v-else>返回列表</el-button>
    <div class="search" v-show="showList">
      <el-input placeholder="请输入领养信息编号" v-model="searchid"></el-input>
      <el-button type="warning" icon="el-icon-search" @click="search">搜索</el-button>
    </div>
    <ul id="adoptionList" v-show="showList">
      <li
        v-for="(item, index) in adoptions"
        :key="index"
        @click="toDetails(index)"
      >
        <img :src="item.image" />
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
              maxlength="500"
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
              :limit="8"
              :class="{ hide: notShowUpload }"
            >
              <i class="el-icon-plus"></i>
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

    <div class="details" v-show="!showList">
      <h4>{{ adoptionForm.title }}</h4>
      <p>{{ adoptionForm.content }}</p>
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
      imageList: [],
    };
  },
  methods: {
    //删除照片
    handleRemove(file, fileList) {
      if (fileList.length < 8) {
        this.notShowUpload = false;
      }
    },
    //照片上传按钮的动态显示
    handleChange(file, fileList) {
      if (fileList.length >= 8) {
        this.notShowUpload = true;
      } else {
        this.notShowUpload = false;
      }
    },
    //打开添加领养信息界面
    openAddMessage() {
      for (let item in this.adoptionForm) {
        this.adoptionForm[item] = null;
      }
      this.isAdd = true;
      this.showMessage = true;
    },
    //打开修改领养信息界面
    openUpdateMessage(index) {
      Object.assign(this.adoptionForm, this.adoptions[index]);
      this.isAdd = false;
      this.currentIndex = index;
      this.showMessage = true;
    },
    //打开删除领养信息提示
    openDeleteMessage(index) {
      this.currentIndex = index;
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
      obj.title = this.adoptionForm.title;
      obj.content = this.adoptionForm.content;
      obj.author = this.$store.state.user.username;
      if (this.isAdd) {
        //新增领养信息
        obj.status = 0;
        this.$ajax
          .post("http://localhost:8081/adoption/save", obj)
          .then(function (res) {
            if (res.data == "success") {
              that.adoptions.unshift(obj);
              that.showMessage = false;
              that.$message({
                message: "发布成功",
                type: "success",
              });
            }
          })
          .catch((err) => console.log(err));
      } else {
        //修改领养信息
        obj.adoptionid = this.adoptions[this.currentIndex].adoptionid;
        obj.createtime = this.adoptions[this.currentIndex].createtime;
        obj.status = this.adoptions[this.currentIndex].status;
        this.$ajax
          .put("http://localhost:8081/adoption/update", obj)
          .then(function (res) {
            if (res.data == "success") {
              that.adoptions[that.currentIndex] = obj;
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
      Object.assign(this.adoptionForm, this.adoptions[index]);
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
            that.showList = false;
          } else {
            that.$message("该领养信息不存在");
          }
        }).catch((err) => console.log(err));
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
    },
  },
  //开始时获取全部领养信息
  created() {
    let that = this;
    this.$ajax
      .get("http://localhost:8081/adoption/findAll/1/8")
      .then((res) => {
        that.adoptions = res.data.content;
        that.total = res.data.totalElements;
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
#adoption .el-button {
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
  overflow-y: scroll;
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
.coverBox .el-textarea__inner {
  font-size: 16px !important;
}
.coverBox .el-form-item:last-child {
  margin-top: 8%;
}
.coverBox .el-textarea__inner {
  font-size: 14px !important;
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