<template>
  <div class="dashboard-container">
    <el-main class="main">
        <el-tabs v-model="activeName" @tab-click="handleClick">
          <el-tab-pane label="企业信息" name="first">
            <!-- 表单内容 -->
            <div class="formInfo">
              <div>
                <div class="boxMain">
                  <el-form ref="form" :model="formData" label-width="215px" label-position="right">
                      <el-form-item class="formInfo" label="公司名称：">
                        <el-input v-model="formData.name" class="inputW" disabled></el-input>
                      </el-form-item>
                      <el-form-item class="formInfo" label="公司地区：">
                        <el-input v-model="formData.companyArea" class="inputW" disabled></el-input>
                      </el-form-item>
                      <el-form-item class="formInfo" label="公司地址：">
                        <el-input v-model="formData.companyAddress" class="inputW" disabled></el-input>
                      </el-form-item>
                      <el-form-item class="formInfo" label="审核状态：">
                        <el-input v-model="formData.auditState" class="inputW" disabled></el-input>
                      </el-form-item>
                      <el-form-item class="formInfo" label="营业执照：">
                        <!-- <span v-for="item in fileList" :key='item.id' class="fileImg">
                          <img :src="item.url">
                        </span> -->
                      </el-form-item>
                      <el-form-item class="formInfo" label="法人代表：">
                        <el-input v-model="formData.legalRepresentative" class="inputW" disabled></el-input>
                      </el-form-item>
                      <el-form-item class="formInfo" label="公司电话：">
                        <el-input v-model="formData.companyPhone" class="inputW" disabled></el-input>
                      </el-form-item>
                      <el-form-item class="formInfo" label="邮箱：">
                        <el-input v-model="formData.mailbox" class="inputW" disabled></el-input>
                      </el-form-item>
                      <el-form-item class="formInfo" label="公司规模：">
                        <el-input v-model="formData.companySize" class="inputW" disabled></el-input>
                      </el-form-item>
                      <el-form-item class="formInfo" label="所属行业：">
                        <el-input v-model="formData.industry" class="inputW" disabled></el-input>
                      </el-form-item>
                      <el-form-item class="formInfo" label="备注：">
                        <el-input type="textarea" v-model="formData.remarks" class="inputW">
                      </el-input>
                      </el-form-item>
                  </el-form>
                  <div slot="footer" class="dialog-footer">
                    <el-button type="primary" @click="handleSub('1')">审核</el-button>
                    <el-button @click="handleSub('2')">拒绝</el-button>
                  </div>
                </div>
              </div>
            </div>
          </el-tab-pane>
          <el-tab-pane label="账户信息" name="second">账户信息</el-tab-pane>
          <el-tab-pane label="交易记录" name="third">交易记录</el-tab-pane>
        </el-tabs>
    </el-main>
         
  </div>
</template>

<script>
import { findById } from "@/api/base/saasClient";
export default {
  name: "saas-clients-table-index",
  data() {
    return {
      activeName: 'first',
      formData: {}
    };
  },
  methods: {
    getDetail(id){
        findById({id:id}).then(res => {
            this.formData = res.data.data
            console.log(id)
        })
    },
    handleClick(tab, event) {
      console.log(tab, event);
    },
    handleSub(num){

    }
  },
  created() {
    var id = this.$route.params.id
    this.getDetail(id)
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
