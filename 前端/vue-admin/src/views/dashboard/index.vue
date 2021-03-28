<template>
  <div class="dashboard-container">
    <div class="dashboard-text"></div>
    <!-- <div class="dashboard-text">欢迎来到小学数学线上教育系统后台管理页面</div> -->
    <el-row>
      <el-col :span="24">
        <div class="grid-content bg-purple-dark">
            <p style="font-size:20px; text-align : 10px 0px 15px 5px; text-indent: 3em;line-height:200%">
                欢迎您:{{ name }}
            </p>
            <p style="font-size:20px;  text-indent: 3em; letter-spacing:2px;line-height:200%">
              欢迎来到小学数学线上教育系统，本系统主要功能包含，权限管理、讲师管理、课程管理、课程分类管理、
              首页轮播图的管理、统计分析、考试管理、题库管理......
            </p>
            <p style="font-size:20px; text-align : 10px 0px 15px 5px; text-indent: 10em; letter-spacing:2px;line-height:200%">
                今天也是美好的一天呢！
            </p>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'Dashboard',
  computed: {
    ...mapGetters([
      'name',
      'roles'
    ])
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}

  .el-row {
    width: 450px;
    margin-bottom: 20px;
    &:last-child {
      margin-bottom: 0;
    }
  }
  .el-col {
    border-radius: 4px;
  }
  .bg-purple-dark {
      font-family: "Helvetica Neue",Helvetica,"PingFang SC","Hiragino Sans GB","Microsoft YaHei","微软雅黑",Arial,sans-serif;
      box-shadow: 0 2px 4px rgba(0, 0, 0, .12);
    height: 300px;
      background: #ffffff;
  }
  .bg-purple {
    background: #d3dce6;
  }
  .bg-purple-light {
    background: #e5e9f2;
  }
  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }
  .row-bg {
    padding: 10px 0;
    background-color: #f9fafc;
  }
</style>
