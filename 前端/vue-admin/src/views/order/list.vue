<template>
  <div class="app-container">
    订单表单
    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="orderQuery.courseTitle" placeholder="课程名称" />
      </el-form-item>

      <el-form-item label="添加时间">
        <el-date-picker
          v-model="orderQuery.begin"
          type="datetime"
          placeholder="选择开始时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="orderQuery.end"
          type="datetime"
          placeholder="选择截止时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>

      <el-button type="primary" icon="el-icon-search" @click="getList()"
        >查询</el-button
      >
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>

    <!-- 表格 -->
    <el-table
      :data="list"
      border
      fit
      highlight-current-row
    >
      <el-table-column label="序号" width="70" align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column prop="courseTitle" label="课程名称" width="250" />

      <el-table-column label="订单状态" width="200">
        <template slot-scope="scope">
          {{ scope.row.status === 1 ? "已支付" : "未支付" }}
        </template>
      </el-table-column>

      <el-table-column prop="nickname" label="用户昵称" width="200"/>

      <el-table-column prop="gmtCreate" label="创建时间" width="350" />

      <el-table-column prop="mobile" label="用户手机" width="200" />
    </el-table>

    <!-- 分页 -->
    <el-pagination
      :current-page="page"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: center"
      layout="total, prev, pager, next, jumper"
      @current-change="getList"
    />
  </div>
</template>
<script>
// 引入调用teacher.js文件
// eslint-disable-next-line no-unused-vars
import order from "@/api/order/order";

export default {
  // 写核心代码位置
  data() {
    // 定义数据变量与初始值
    return {
      list: null,
      page: 1,
      limit: 10,
      total: 0,
      orderQuery: {}, // 条件封装对象
    };
  },
  created() {
    // 页面渲染之前执行
    this.getList();
  },
  methods: {
    // 创建具体的方法，调用teacher.js
    // 讲师列表的方法
    getList(page = 1) {
      this.page = page;
      order
        .getOrderListPage(this.page, this.limit, this.orderQuery)
        .then((response) => {
          // 请求成功
          // response接口返回的数据
          // console.log(response)
          this.list = response.data.rows;
          this.total = response.data.total;
          console.log(this.list);
          console.log(this.total);
        })
    },
    resetData() {
      // 清空的方法
      // 表单输入项数据清空
      this.orderQuery = {};
      // 查询所有讲师数据
      this.getList();
    },
  },
};
</script>