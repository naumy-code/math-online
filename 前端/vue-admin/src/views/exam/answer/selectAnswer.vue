//查询所有题库
<template>
  <div class="exam">
    <el-table :data="list" border :row-class-name="tableRowClassName">
      <el-table-column fixed="left" prop="subject" label="试卷名称" width="180"></el-table-column>
      <el-table-column prop="question" label="题目信息" width="400  "></el-table-column>
      <el-table-column prop="questionType" label="题目类型" width="200"></el-table-column>
      <el-table-column prop="score" label="试题分数" width="110"></el-table-column>
      <el-table-column label="难度等级" width="150" align="center">
        
        <template slot-scope="scope">
           <el-rate
              v-model="scope.row.level"
              disabled
              show-score
              text-color="#ff9900"
              score-template="{value}">
            </el-rate>
        </template>
      </el-table-column>
      
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
            <el-button type="info" size="mini" icon="el-icon-view"
              @click="SelectById(scope.row.questionId,scope.row.questionType)"
              >预览</el-button
            >
          <el-button
            type="danger"
            size="mini"
            icon="el-icon-delete"
            @click="removeById(scope.row.questionId,scope.row.questionType)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pagination.current"
      :page-sizes="[6, 10]"
      :page-size="pagination.size"
      layout="total, sizes, prev, pager, next, jumper"
      :total="pagination.total"
      class="page"
    ></el-pagination>

    <el-dialog title="题目详情" :visible.sync="centerAnswerDialogVisible"  width="30%" center>
      <span v-if="questionList.questionType === '选择题'">
        题目：{{questionList.question}} <br>
              A：{{questionList.answerA}} <br>
              B：{{questionList.answerB}} <br>
              C：{{questionList.answerC}} <br>
              D：{{questionList.answerD}} <br>
        解析：{{questionList.analysis}} <br>
      </span>
      <span v-if="questionList.questionType === '填空题'">
        题目：{{questionList.question}} <br>
        答案：{{questionList.answer }} <br>
        解析：{{questionList.analysis}} <br>
      </span>
      <span v-if="questionList.questionType === '判断题'">
        题目：{{questionList.question}} <br>
        答案：{{questionList.answer === T ? '正确' :'错误'}} <br>
        解析：{{questionList.analysis}} <br>
      </span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="centerAnswerDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="centerAnswerDialogVisible = false">确 定</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
import answer from "@/api/exam/answer";
export default {
  data() {
    return {
      centerAnswerDialogVisible: false,
      list: null,
      questionList: {
        questionType:null,
      },
      pagination: {
        //分页后的考试信息
        current: 1, //当前页
        total: null, //记录条数
        size: 10 //每页条数
      }
    };
  },
  created() {
    this.getAnswerInfo();
  },
  methods: {
    getAnswerInfo() {
      //分页查询所有试卷信息
      // this.$axios(
      //   `http://localhost:8101/answers/${this.pagination.current}/${this.pagination.size}`
      // )
      answer.getAnswerListPage(this.pagination.current,this.pagination.size).then(res => {
        this.list = res.data.records;
        console.log(list);
      })
      .catch(error => {});
    },
    //改变当前记录条数
    handleSizeChange(val) {
      this.pagination.size = val;
      this.getAnswerInfo();
    },
    //改变当前页码，重新发送请求
    handleCurrentChange(val) {
      this.pagination.current = val;
      this.getAnswerInfo();
    },
    tableRowClassName({ row, rowIndex }) {
      if (rowIndex % 2 == 0) {
        return "warning-row";
      } else {
        return "success-row";
      }
    },
    removeById(questionId,questionType) {
      this.$confirm("确定删除该记录吗","删除提示",{
        confirmButtonText: '确定删除',
        cancelButtonText: '取消',
        type: 'danger'
      }).then(()=> { //确认删除
        // this.$axios({
        //   url: `http://localhost:8101/exam/${questionId}/${questionType}`,
        //   method: 'delete',
        // })
        answer.deleteAnswerId(questionId,questionType).then(res => {
          this.getAnswerInfo()
        })
      }).catch(() => {

      })
    },
    SelectById(questionId,questionType) {
      answer.findAnswerById(questionId,questionType).then(res => {
        this.centerAnswerDialogVisible = true
        this.questionList = res.data
        console.log(this.questionList)
      })
    },
  }
};
</script>
<style lang="scss" scoped>
.exam {
  padding: 0px 40px;
  .page {
    margin-top: 20px;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .edit {
    margin-left: 20px;
  }
  .el-table tr {
    background-color: #DD5862 !important;
  }
}
  .el-table .warning-row {
    background: #000 !important;
    
  }

  .el-table .success-row {
    background: #DD5862;
  }

</style>
