import request from '@/utils/request'

export default {
    //登录的方法
  submitLoginUser(userInfo) {
    return request({
      url: `/educenter/member/login`,
      method: 'post',
      data: userInfo
    })
  },

  //根据token获取用户信息
  getLoginUserInfo() {
    return request({
      url: `/educenter/member/getMemberInfo`,
      method: 'get'
    })
  },
  
  //根据id获取用户信息
  getMemberInfo(id) {
    return request({
      url: `/educenter/member/getUserInfoOrder/${id}`,
      method: 'post'
    })
  },

  //根据id获取用户信息  （个人中心用）
  getMemberInfoSelf(id) {
    return request({
      url: `/educenter/member/getUserInfo/${id}`,
      method: 'post'
    })
  },

  //用户信息修改功能
  updataMemberInfo(ucenterMember) {
    return request({
      url: `/educenter/member/updateMember`,
      method: 'post',
      data: ucenterMember
    })
  },

  //修改密码
  ChangePassword(mobile,password) {
    return request({
      url: `/educenter/member/updatePassword/${mobile}/${password}`,
      method: 'post'
    })
  },
}
