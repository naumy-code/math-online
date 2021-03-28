<template>
  <div class="app-container">
    <el-input v-model="filterText" placeholder="Filter keyword" style="margin-bottom:30px;" />

    <!-- <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeData()">清空</el-button> -->

    <el-tree
      ref="tree2"
      :data="data2"
      :props="defaultProps"
      :filter-node-method="filterNode"
      class="filter-tree"
      node-key="SubjectId"
      default-expand-all
      @node-click="selectCategory"
    >


     <span class="custom-tree-node" slot-scope="{ node, data }">
          <span>{{ node.label }}</span>
          <span class="fr">
              <el-button
                  v-if="data.parentId == 0"
                  type="text"
                  size="mini"
                  @click="() => openSubjectDialog(node, data)">
                  添加
              </el-button>
              <el-button
                  type="text"
                  size="mini"
                  @click="() => openEditSubject(node, data)">
                  修改
              </el-button>
              <el-button
                  class="fontRed" 
                  type="text"
                  size="mini"
                  @click="() => removeDataById(node, data)">
                  删除
              </el-button>
          </span>
      </span>
    </el-tree>

    <!-- 添加和修改章节表单 -->
    <el-dialog :visible.sync="dialogSubjectFormVisible" title="课程科目">
        <el-form :model="Subject" label-width="120px">
            <el-form-item label="章节标题">
                <el-input v-model="Subject.title"/>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="dialogSubjectFormVisible = false">取 消</el-button>
            <!-- <el-button type="primary" @click="addSubject()">确 定</el-button> -->
            <el-button type="primary" @click="saveOrUpdate()">确 定</el-button>
        </div>
    </el-dialog>
<!-- @click="addTeacher" -->

  </div>
</template>

<script>
import subject from '@/api/edu/subject'
export default {

  data() {
    return {
      Subject:{ //封装章节数据
        title: '',
      },

      filterText: '',
      data2: [],  //返回所有分类数据
      defaultProps: {
        children: 'children',
        label: 'title'
      },
      dialogSubjectFormVisible:false,//科目弹框
    }
  },
  created() {
      this.getAllSubjectList()
  },
  watch: {
    filterText(val) {
      this.$refs.tree2.filter(val)
    }
  },

  methods: {
    getAllSubjectList() {
        subject.getSubjectList()
            .then(response => {
                this.data2 = response.data.list
            })
    },
    filterNode(value, data) {
      if (!value) return true
      return data.title.toLowerCase().indexOf(value.toLowerCase()) !== -1
    },

    // 清空科目的方法
    removeData() {
      this.$confirm("此操作将永久清空课程分类记录, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        // 点击确定，执行then方法
        // 调用删除的方法
        subject.deleteSubject().then((response) => {
          // 删除成功
          // 提示信息
          this.$message({
            type: "success",
            message: "删除成功!",
          });
          // 回到列表页面
          this.getAllSubjectList();
        });
      }); // 点击取消，执行catch方法
    },

    removeDataById(node,data) {
      this.$confirm("此操作将永久删除科目记录, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        // 点击确定，执行then方法
        // 调用删除的方法
        subject.deleteSubjectById(data.id).then((response) => {
          // 删除成功
          // 提示信息
          this.$message({
            type: "success",
            message: "删除成功!",
          });
          // 回到列表页面
          this.getAllSubjectList();
        });
      }); // 点击取消，执行catch方法
    },

    //修改章节弹框数据回显
    openEditSubject(node,data) {
        //弹框
        this.dialogSubjectFormVisible = true
        subject.getSubjectById(data.id)   
                .then(response => {
                    this.Subject = response.data.subject
                })
    },
    //弹出添加章节页面
    openSubjectDialog(node,data) {
        //设置父级idSubject对象里面
        this.Subject.parentId = data.id
        //弹框
        this.dialogSubjectFormVisible = true
        //表单数据清空
        this.Subject.title = ''
    },

    //添加课程科目
    addSubject() {

      subject.addSubject(this.Subject).then((response) => {
        //添加成功
        //提示信息
        this.$message({
          type: "success",
          message: "添加成功!",
        });
        // 回到列表页面
        this.getAllSubjectList();
      });
    },

    //修改科目的方法
    updateSubject() {
      subject.updateSubjectInfo(this.Subject).then((response) => {
        //关闭弹框
        this.dialogSubjectFormVisible = false
        //提示信息
        this.$message({
          type: "success",
          message: "修改成功!",
        });
        // 回到列表页面
        this.getAllSubjectList();
      });
    },

    saveOrUpdate() {
      if(!this.Subject.id) {
          this.addSubject()
      } else {
          this.updateSubject()
      }
    },
  }
}
</script>