import request from '@/utils/request'
export default {
  // 1 讲师列表（条件查询分页）
  // current当前页 limit每页记录数 teacherQuery条件对象
  getOrderListPage(current, limit, orderQuery) {
    return request({
      // eslint-disable-next-line spaced-comment
      //url: '/eduservice/teacher/pageTeacherCondition/'+current+"/"+limit,
      url: `/eduorder/order/pageOrderCondition/${current}/${limit}`,
      method: 'post',
      // teacherQuery条件对象，后端使用RequestBody获取数据
      // data表示把对象转换json进行传递到接口里面
      data: orderQuery
    })
  },
}
