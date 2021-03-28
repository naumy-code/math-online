import request from '@/utils/request'

export default {
    //生成订单
  createOrders(courseId) {
    return request({
      url: '/eduorder/order/createOrder/'+courseId,
      method: 'post'
    })
  },
   //调用支付宝
  Alipay(id) {
    return request({
      url: '/eduorder/order/Alipay/'+id,
      method: 'post'
    })
  },
  //根据订单id查询订单信息
  getOrdersInfo(id) {
    return request({
      url: '/eduorder/order/getOrderInfo/'+id,
      method: 'get'
    })
  },
  //生成二维码的方法
  createNatvie(orderNo) {
    return request({
      url: '/eduorder/paylog/createNative/'+orderNo,
      method: 'get'
    })
  },

  //查询订单状态的方法
  queryPayStatus(orderNo) {
    return request({
      url: '/eduorder/paylog/queryPayStatus/'+orderNo,
      method: 'get'
    })
  },

  //个认中心订单列表
  //个人中心
  getList(current, limit) {
    return request({
      url: `/eduorder/order/auth/list/${current}/${limit}`,
      method: 'post'
    })
  },
  
  removeById(payLogId) {
    return request({
      url: '/eduorder/order/auth/remove/'+payLogId,
      method: 'delete'
    })
  },



}