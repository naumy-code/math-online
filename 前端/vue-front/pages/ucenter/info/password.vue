<template>
  <article class="col-7 fl">
    <div class="u-r-cont">
      <section>
        <div>
          <section class="c-infor-tabTitle c-tab-title">

            <a href="javascript: void(0)" title="密码设置" class="current">
              密码设置
            </a>
          </section>
        </div>

        <!-- 手机号 -->
        <div style="margin-right: 10%;">
          <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
            <el-form-item label="新密码" prop="newPwd">
            <el-input v-model="ruleForm.newPwd" clearable size="small" type="password"></el-input>
            </el-form-item>
            <el-form-item label="确认密码" prop="confirmPwd">
            <el-input v-model="ruleForm.confirmPwd" clearable size="small" type="password"></el-input>
            </el-form-item>
            <el-form-item>
            <el-button type="primary" @click="submitChange('ruleForm')" class="button">确定</el-button>
            <el-button @click="resetForm('ruleForm')" class="button">重置</el-button>
            </el-form-item>
        </el-form>
        </div>

      </section>
    </div>
  </article>
</template>
<script>
  //引入调用login.js文件
  import loginApi from '@/api/login'
  //引入调用register.js文件
  import registerApi from '@/api/register'
  //引入调用js-cookie
  import cookie from 'js-cookie'

  export default {
    data() {
      var validatePass = (rule, value, callback) => {
        if (value === '') {
                callback(new Error('请输入密码'));
        } else {
          if (this.ruleForm.confirmPwd !== '') {
              this.$refs.ruleForm.validateField('confirmPwd');
          }
          callback();
        }
      };
      var validatePass2 = (rule, value, callback) => {
          if (value === '') {
              callback(new Error('请再次输入密码'));
          } else if (value !== this.ruleForm.newPwd) {
              callback(new Error('两次输入密码不一致!'));
          } else {
              callback();
          }
      };

      return {
        token: '',
        loginInfo: {
          id: '',
          age: '',
          avatar: '',
          mobile: '',
          nickname: '',
          sex: ''
        },
        memberInfo: {
          id: '',
          age: '',
          avatar: '',
          mobile: '',
          nickname: '',
          sex: '',
          sign: '',
        },
        params: { //封装注册输入的数据
          mobile: '', //手机号
          code: '', //验证码
          nickname: '', //昵称
          password: ''
        },
        

        changePwdDialog:true,//修改密码弹框
        ruleForm: {
          newPwd: '',
          confirmPwd:''
        },
        rules: {
          newPwd: [
            { required: true, message: '请输入密码', trigger: 'blur' },
            { min: 6, max: 16, message: '长度在 6 到 16 个字符', trigger: 'blur' },
            { validator: validatePass, trigger: 'blur' }
          ],
          confirmPwd:[
            { required: true, message: '请确认密码', trigger: 'blur' },
            { min: 6, max: 16, message: '长度在 6 到 16 个字符', trigger: 'blur' },
            { validator: validatePass2, trigger: 'blur', required: true }
          ],
        }
      }
    },
    created() {
      //获取路径里面token值
      this.token = this.$route.query.token
      if (this.token) { //判断路径是否有token值
        this.wxLogin()
      }

      this.showInfoFromCookie()
    },

    methods: {
      //修改密码提交的方法
      submitChange(formName) {
        this.$refs[formName].validate(valid => {
            if (valid) {
              loginApi.ChangePassword(this.memberInfo.mobile,this.ruleForm.newPwd)
              .then(response => {
                //提示注册成功
                this.$message({
                  type: 'success',
                  message: "修改密码成功!"
                })
                //跳转到登陆页面
                this.$router.push({
                  path: '/login'
                })
              })
            } else {
                this.$message.error('请正确填写表单')
                return false
            }
        })
      },

      //从cookie中获取用户信息
      showInfoFromCookie() {
        //从cookie中获取用户信息
        var userStr = cookie.get("guli_ucenter")
        //userStr是字符串   需要转换为json对象
        if (userStr) {
          this.loginInfo = JSON.parse(userStr)
        }
        console.log(this.loginInfo.id)

        loginApi.getMemberInfoSelf(this.loginInfo.id).then(response => {
          this.memberInfo = response.data.data.memberInfo
        })
        console.log(this.memberInfo.id)
      },

      resetForm() {
        // 清空的方法
        // 表单输入项数据清空
        this.ruleForm = {};
      },

    }
  }
</script>
