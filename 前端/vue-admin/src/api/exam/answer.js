import request from '@/utils/request'
export default {
  // 1 习题列表（条件查询分页）
  // current当前页 limit每页记录数 teacherQuery条件对象
  getAnswerListPage(current, size) {
    return request({
      url: `/examservice/answer/answers/${current}/${size}`,
      method: 'get'
    })
  },
  // 删除习题
  deleteAnswerId(questionId,questionType) {
    return request({
      url: `/examservice/answer/exam/${questionId}/${questionType}`,
      method: 'delete'
    })
  },
  // 根据习题ID和类型进行查询
  findAnswerById(questionId,questionType) {
    return request({
      url: `/examservice/answer/examInfo/${questionId}/${questionType}`,
      method: 'get'
    })
  },
}