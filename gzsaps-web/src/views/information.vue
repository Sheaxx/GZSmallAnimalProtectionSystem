<template>
  <div id="information">
    <h4 class="boxTitle"><i class="el-icon-s-management"></i>小动物科普</h4>
    <el-button type="warning">添加科普信息</el-button>
    <ul id="informationList">
      <li v-for="(item, index) in informations" :key="index">
        <img :src="item.image" />
        <h5>{{ item.title }}</h5>
      </li>
    </ul>
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
      informations: [
        {
          title: "hhhhhhhhhhhhhhhhhhhhh",
          image:
            "https://img2.baidu.com/it/u=3937429125,1837844793&fm=26&fmt=auto",
        },
        {
          title: "hhhhhhhhhhhhhhhhhhhhh",
          image:
            "https://img2.baidu.com/it/u=3937429125,1837844793&fm=26&fmt=auto",
        },
        {
          title: "hhhhhhhhhhhhhhhhhhhhh",
          image:
            "https://img2.baidu.com/it/u=3937429125,1837844793&fm=26&fmt=auto",
        },
        {
          title: "hhhhhhhhhhhhhhhhhhhhh",
          image:
            "https://img2.baidu.com/it/u=3937429125,1837844793&fm=26&fmt=auto",
        },
        {
          title: "hhhhhhhhhhhhhhhhhhhhh",
          image:
            "https://img2.baidu.com/it/u=3937429125,1837844793&fm=26&fmt=auto",
        },
        {
          title: "hhhhhhhhhhhhhhhhhhhhh",
          image:
            "https://img2.baidu.com/it/u=3937429125,1837844793&fm=26&fmt=auto",
        },
        {
          title: "hhhhhhhhhhhhhhhhhhhhh",
          image:
            "https://img2.baidu.com/it/u=3937429125,1837844793&fm=26&fmt=auto",
        },
        {
          title: "hhhhhhhhhhhhhhhhhhhhh",
          image:
            "https://img2.baidu.com/it/u=3937429125,1837844793&fm=26&fmt=auto",
        },
        {
          title: "hhhhhhhhhhhhhhhhhhhhh",
          image:
            "https://img2.baidu.com/it/u=3937429125,1837844793&fm=26&fmt=auto",
        },
      ],
    };
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
  margin-bottom: 3%;
  overflow: hidden;
  width: 90%;
}
#information .el-button {
  float: right;
  margin-top: -6%;
  margin-right: 2%;
}
#information i {
  margin-right: 20px;
}
#informationList {
  display: flex;
  flex-wrap: wrap;
  width: 90%;
  height: 82%;
  margin: 0 auto;
  overflow-y: scroll;
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

</style>