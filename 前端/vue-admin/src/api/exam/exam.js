import request from '@/utils/request'
export default {
  // 1 考试列表（条件查询分页）
  // current当前页 limit每页记录数 teacherQuery条件对象
  getExamListPage(current, size) {
    return request({
      // eslint-disable-next-line spaced-comment
      //url: '/eduservice/teacher/pageTeacherCondition/'+current+"/"+limit,
      url: `/examservice/exam/exams/${current}/${size}`,
      method: 'get'
    })
  },
  //删除考试
  deleteExamId(examCode) {
    return request({
      url: `/examservice/exam/exam/${examCode}`,
      method: 'delete'
    })
  },
  // 获得paperId
  getPaperId() {
    return request({
      url: `/examservice/exam/examManagePaperId`,
      method: 'get'
    })
  },
  //添加考试信息
  addPaper(form) {
    return request({
        url: `/examservice/exam/exam`,
        method: 'post',
        data: form
    })
  },
  //根据id查询考试信息
  getExamInfo(examCode) {
    return request({
        url: `/examservice/exam/exam/${examCode}`,
        method: 'get'
      })
  },
  //修改考试信息
  updateExamInfo(form) {
    return request({
        url: `/examservice/exam/updateExam`,
        method: 'post',
        data: form
      })
  }
}
