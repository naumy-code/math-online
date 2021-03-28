<template>
  <div class="app-container">
    <!-- 输入表单 -->
    <el-form label-width="120px">
      <el-form-item label="轮播图名称">
        <el-input v-model="ad.title" />
      </el-form-item>
      <!-- <el-form-item label="排序">
        <el-input-number v-model="ad.sort" :min="0"/>
      </el-form-item> -->
      <el-form-item label="轮播图片">
        <el-upload
          :on-success="handleAvatarSuccess"
          :on-error="handleAvatarError"
          :on-exceed="handleUploadExceed"
          :before-upload="beforeAvatarUpload"
          :limit="1"
          :file-list="fileList"
          :action="BASE_API+'/eduoss/fileoss'"
          list-type="picture">
          <el-button size="small" type="primary" plain="true">点击上传</el-button>
        </el-upload>
      </el-form-item>
      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" plain="true" @click="saveOrUpdate()">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import adApi from '@/api/cms/bander'
export default {
  data() {
    return {
      ad: {
        sort: 0,
        imageUrl: ''
      },
      fileList: [], // 上传文件列表
      saveBtnDisabled: false ,// 保存按钮是否禁用，防止表单重复提交
      BASE_API: process.env.BASE_API, //获取dev.env.js里面地址
    }
  },

  // 页面渲染成功
  created() {
    // 页面渲染之前执行
    this.init();
  },

  watch: {
    // 监听
    $route(to, from) {
      this.init();
    },
  },

  methods: {

    init() {
      // 判断路径中是否有id值，做修改
      if (this.$route.params && this.$route.params.id) {
        // 从路径获取ID值
        const id = this.$route.params.id;
        // 调用根据id查询的方法
        this.fetchDataById(id);
      } else {
        // 清空表单
        this.ad = {};
        fileList = '';
      }
    },


    saveOrUpdate() {
      if (!this.ad.id) {
        this.saveData()
      } else {
        this.updateData()
      }
    },

    // 新增
    saveData() {
      // debugger
      adApi.save(this.ad).then(response => {
        this.$message.success(response.message)
        this.$router.push({ path: '/cms/table' })
      })
    },

    // 根据id更新记录
    updateData() {
      // teacher数据的获取
      adApi.updateById(this.ad).then(response => {
        this.$message.success(response.message)
        this.$router.push({ path: '/cms/table' })
      })
    },

    // 根据id查询记录
    fetchDataById(id) {
      adApi.getById(id).then(response => {
        this.ad = response.data.item
        this.fileList = [{ 'url': this.ad.imageUrl }]
      })
    },

    // 上传多于一个文件
    handleUploadExceed(files, fileList) {
      this.$message.warning('想要重新上传图片，请先删除已上传的图片')
    },

    // 上传校验
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg'
      const isLt3M = file.size / 1024 / 1024 < 3

       if (!isJPG) {
         this.$message.error('上传头像图片只能是 JPG 格式!')
       }
      if (!isLt3M) {
        this.$message.error('上传头像图片大小不能超过 3MB!')
      }
      return  isLt3M && isJPG
    },

    // 上传成功回调
    handleAvatarSuccess(response, file) {
      console.log(response)
      if (response.success) {
        // console.log(res)
        this.ad.imageUrl = response.data.url
        // 强制重新渲染
        // this.$forceUpdate()
      } else {
        this.$message.error('上传失败')
      }
    },

    // 错误处理
    handleAvatarError() {
      console.log('error')
      this.$message.error('上传失败! （http失败）')
    }
  }
}
</script>
