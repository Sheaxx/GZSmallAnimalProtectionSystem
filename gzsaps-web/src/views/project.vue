<template>
  <div id="project">
    <h4 class="boxTitle"><i class="el-icon-s-flag"></i>志愿者项目</h4>
    <el-button type="warning">添加项目</el-button>
    <el-timeline>
      <el-timeline-item
        placement="top"
        color="#ff7f41"
        v-for="(item, index) in porjects"
        :key="index"
        :timestamp="item.createtime"
      >
        <el-card>
          <h5>{{ item.title }}</h5>
          <div class="option">
            <a href="">修改</a>
            <a href="">删除</a>
          </div>
        </el-card>
      </el-timeline-item>
    </el-timeline>
    <el-pagination
      layout="prev, pager, next"
      :total="50"
      :hide-on-single-page="value"
    >
    </el-pagination>
  </div>
</template>

<script>
export default {
  data() {
    return {
      value: true,
      porjects: [
        {
          title: "hhhhhhhhhhhhhhhhhhhhh",
          createtime: "2021-10-13 18:35:00",
        },
        {
          title: "hhhhhhhhhhhhhhhhhhhhh",
          createtime: "2021-10-13 18:35:00",
        },
        {
          title: "hhhhhhhhhhhhhhhhhhhhh",
          createtime: "2021-10-13 18:35:00",
        },
        {
          title: "hhhhhhhhhhhhhhhhhhhhh",
          createtime: "2021-10-13 18:35:00",
        },
        {
          title: "hhhhhhhhhhhhhhhhhhhhh",
          createtime: "2021-10-13 18:35:00",
        },
      ],
    };
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
  margin-bottom: 3%;
  overflow: hidden;
  width: 90%;
}
#project .el-button {
  float: right;
  margin-top: -6%;
  margin-right: 2%;
}
#project h5 {
  font-size: 16px;
  margin: 10px;
}
#project i {
  margin-right: 20px;
}
#project a {
  margin: 10px 5px 10px 10px;
  overflow: hidden;
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
  height: 80%;
  overflow-y: scroll;
}
#project .el-timeline-item {
  width: 95%;
  margin: 0 auto;
}
</style>