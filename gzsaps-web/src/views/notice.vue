<template>
  <div id="notice">
    <h4 class="boxTitle"><i class="el-icon-message-solid"></i>公告</h4>
    <el-button type="warning">添加公告</el-button>
    <el-timeline>
      <el-timeline-item
        placement="top"
        color="#ff7f41"
        v-for="(item, index) in notices"
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
      notices: [
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
</style>