<template>
  <div class="app-container">
    课程列表

    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="courseQuery.title" placeholder="课程名称"/>
      </el-form-item>

      <el-form-item>
        <el-select v-model="courseQuery.status" clearable placeholder="课程状态">
          <el-option value="Normal" label="已发布"/>
          <el-option value="Draft" label="未发布"/>
        </el-select>
      </el-form-item>

      <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>


    <!-- 表格 -->
    <el-table
      :data="list"
      border
      fit
      highlight-current-row>

      <el-table-column
        label="序号"
        width="70"
        align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column prop="title" label="课程名称" width="200" />

      <el-table-column label="封面" width="150" align="center">
        <template slot-scope="scope">
          <el-image style="width: 100px; height: 100px" :src="scope.row.cover" alt="avatar"></el-image>
        </template>
      </el-table-column>


      <el-table-column prop="lessonNum" label="课时数" width="100"/>


      <el-table-column label="课程状态" width="100">
        <template slot-scope="scope">
          <!-- {{ scope.row.status==='Normal'?'已发布':'未发布' }} -->
          <el-tag type="danger" v-if="scope.row.status === 'Draft'">未发布</el-tag>
          <el-tag type="success" v-if="scope.row.status === 'Normal'">已发布</el-tag>
        </template>
      </el-table-column>

      <el-table-column label="课程状态" width="100">
        <template slot-scope="scope">
          <!-- {{ scope.row.status==='Normal'?'已发布':'未发布' }} -->
          <el-tag  v-if="scope.row.price != 0">{{scope.row.price}}元</el-tag>
          <el-tag type="success" v-if="scope.row.price === 0">免费</el-tag>
        </template>
      </el-table-column>

      <el-table-column prop="price" label="课程价格" width="80" />
      <el-table-column prop="gmtCreate" label="添加时间" width="180"/>

      <el-table-column label="操作" width="310" align="center">
        <template slot-scope="scope">
          <router-link :to="'/course/info/'+scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit">编辑课程</el-button>
          </router-link>
          <router-link :to="'/course/chapter/'+scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit">编辑大纲 </el-button>
          </router-link>
          <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)"> 删除 </el-button>
        </template>
      </el-table-column>
    </el-table>

  <!-- 分页 -->
    <el-pagination
      :current-page="page"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper"
      @current-change="getList"
    />

  </div>
</template>
<script>
//引入调用teacher.js文件
import course from '@/api/edu/course'

export default {
    //写核心代码位置
    // data:{
    // },
    data() { //定义变量和初始值
        return {
          list:null,//查询之后接口返回集合
          page:1,//当前页
          limit:10,//每页记录数
          total:0,//总记录数
          courseQuery:{} //条件封装对象
        }
    },
    created() { //页面渲染之前执行，一般调用methods定义的方法
        //调用
        this.getList() 
    },
    methods:{  //创建具体的方法，调用teacher.js定义的方法
        //课程列表的方法
        // getList() {
        //     course.getListCourse()
        //         .then(response =>{//请求成功
        //             //response接口返回的数据
        //             this.list = response.data.list
        //         }) 
        // },
        resetData() {//清空的方法
            //表单输入项数据清空
            this.courseQuery = {}
            //查询所有讲师数据
            this.getList()
        },
        //课程列表的方法
        getList(page = 1) {
            this.page = page;
            course
              .getCourseListPage(this.page, this.limit, this.courseQuery)
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

    // 删除课程的方法
    removeDataById(id) {
      this.$confirm("此操作将永久删除课程记录, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        // 点击确定，执行then方法
        // 调用删除的方法
        course.deleteCourseId(id).then((response) => {
          // 删除成功
          // 提示信息
          this.$message({
            type: "success",
            message: "删除成功!",
          });
          // 回到列表页面
          this.getList();
        });
      }); // 点击取消，执行catch方法
    },
 
    }
}
</script>
