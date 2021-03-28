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

  // 2 学生考试列表（条件查询分页）
  // current当前页 limit每页记录数 teacherQuery条件对象
  getExamStuListPage(current, size, studentId) {
    return request({
      // eslint-disable-next-line spaced-comment
      //url: '/eduservice/teacher/pageTeacherCondition/'+current+"/"+limit,
      url: `/examservice/Score/score/${current}/${size}/${studentId}`,
      method: 'get'
    })
  },

  // 搜索试卷
  getExamList() {
    return request({
      url: `/examservice/exam/exams`,
      method: 'get'
    })
  },

  //通过Id查询试卷信息的方法
  getExamInfo(examCode) {
    return request({
      url: `/examservice/exam/exam/${examCode}`,
      method: 'get'
    })
  },

  //通过paperId查询试卷信息的方法
  getExamPaperInfo(paperId) {
    return request({
      url: `/examservice/paper/paper/${paperId}`,
      method: 'get'
    })
  }

}