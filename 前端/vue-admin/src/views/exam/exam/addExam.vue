<!-- 添加考试 -->
<template>
  <section class="add">
    <el-form ref="form" :model="form" label-width="80px">
      <el-form-item label="试卷名称">
        <el-input v-model="form.source"></el-input>
      </el-form-item>
      <el-form-item label="介绍">
        <el-input v-model="form.description"></el-input>
      </el-form-item>
      <el-form-item label="课程老师">
        <el-input v-model="form.institute"></el-input>
      </el-form-item>
      <el-form-item label="课程名称">
        <el-input v-model="form.major"></el-input>
      </el-form-item>
      <el-form-item label="年级">
        <el-input v-model="form.grade"></el-input>
      </el-form-item>
      <el-form-item label="考试日期">
        <el-col :span="11">
          <el-date-picker placeholder="选择日期" v-model="form.examDate" style="width: 150%;"></el-date-picker>
        </el-col>
      </el-form-item>
      <el-form-item label="持续时间">
        <el-input v-model="form.totalTime"></el-input>
      </el-form-item>
      <el-form-item label="总分">
        <el-input v-model="form.totalScore"></el-input>
      </el-form-item>
      <el-form-item label="考试类型">
        <el-input v-model="form.type"></el-input>
      </el-form-item>
      <el-form-item label="考生提示">
        <el-input type="textarea" v-model="form.tips"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit()">立即创建</el-button>
        <el-button type="primary" @click="cancel()">清空</el-button>
      </el-form-item>
    </el-form>
  </section>
</template>

<script>
import exam from "@/api/exam/exam";
export default {
  data() {
    return {
      form: { //表单数据初始化
        source: null,
        description: null,
        institute: null,
        major: null,
        grade: null,
        examDate: null,
        totalTime: null,
        totalScore: null,
        type: null,
        tips: null,
        paperId: null,
      }
    };
  },
  methods: {
    formatTime(date) { //日期格式化
      let year = date.getFullYear()
      let month= date.getMonth()+ 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
      let day=date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
      let hours=date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
      let minutes=date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
      let seconds=date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
      // 拼接
      return year+"-"+month+"-"+day+" "+hours+":"+minutes+":"+seconds;
    },
    onSubmit() {
      let examDate = this.formatTime(this.form.examDate)
      this.form.examDate = examDate.substr(0,10)
      exam.getPaperId().then((response) => {
        this.form.paperId = response.data.paperId + 1 //实现paperId自增1
        console.log(this.form.paperId)
        exam.addPaper(this.form).then((response) => {
            this.$message({
              message: '数据添加成功',
              type: 'success'
            })
            this.$router.push({path: '/exam/list'})
        })
      })
    },
    cancel() { //取消按钮
      this.form = {}
    },
    
  }
};
</script>
<style lang="scss" scoped>
.add {
  padding: 0px 40px;
  width: 400px;
}
</style>

