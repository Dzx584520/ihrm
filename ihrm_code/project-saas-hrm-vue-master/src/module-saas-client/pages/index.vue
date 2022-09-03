<template>
  <div class="dashboard-container">
    <el-main class="main">
      <el-table :data="dataList" border style="width: 100%">
       <el-table-column fixed prop="index" type="index" label="序号" width="50"></el-table-column>
        <el-table-column fixed prop="name" label="企业名称"></el-table-column>
        <el-table-column fixed prop="version" label="当前版本" ></el-table-column>
        <el-table-column fixed prop="companyPhone" label="联系电话"></el-table-column>
        <el-table-column fixed prop="expirationDate" label="截止时间"></el-table-column>
        <el-table-column fixed prop="companyArea" label="所在地区" ></el-table-column>
        <el-table-column fixed prop="state" label="状态">
          <template slot-scope="scope">
            <!-- inactive-value:禁用的值 inactive-color:未激活的颜色   active-value:激活的值 active-color:激活的颜色 -->
            <el-switch
              disabled
              v-model="scope.row.status"
              :inactive-value="0"
              :active-value="1"
              active-color="#13ce66"
              inactive-color="#ff4949">
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="100">
          <template slot-scope="scope">
            <router-link :to="'/saas-clients/detail/'+scope.row.id">查看</router-link>
          </template>
        </el-table-column>
      </el-table>
    </el-main>
  </div>
</template>

<script>
import { list } from "@/api/base/saasClient";
export default {
  name: "saas-clients-table-index",
  data() {
    return {
      dataList: []
    };
  },
  methods: {
    handleClick(row) {
        console.log(row);
      },
    getList() {
      list().then((res) => {
        this.dataList = res.data.data;
        console.log("返回结果", res.data.data);
      });
    },
  },
  // 创建完毕状态
  created() {
    this.getList();
  },
};
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.alert {
  margin: 10px 0px 0px 0px;
}
.pagination {
  margin-top: 10px;
  text-align: right;
}
</style>
