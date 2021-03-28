import request from '@/utils/request'
export default {
  // 1 考试试卷列表（条件查询分页） Question
  // current当前页 limit每页记录数 teacherQuery条件对象
  getExamQusetionListPage(current, size) {
    return request({
      // eslint-disable-next-line spaced-comment
      //url: '/eduservice/teacher/pageTeacherCondition/'+current+"/"+limit,
      url: `/examservice/exam/exams/${current}/${size}`,
      method: 'get'
    })
  },

  //添加选择题到选择题表
  addPaperMultiQuestion(form) {
    return request({
        url: `/examservice/multiQuestion/addMultiQuestion`,
        method: 'post',
        data: form
    })
  },
  //根据id查询选择题信息
  getExamInfo() {
    return request({
        url: `/examservice/multiQuestion/multiQuestionId`,
        method: 'get'
      })
  },
  //添加选择题
  addPaperMultiQuestionPaper(postPaper) {
    return request({
        url: `/examservice/paper/paperManage`,
        method: 'post',
        data: postPaper
    })
  },

  //添加填空题到填空题表
  addPaperfillQuestion(form) {
    return request({
        url: `/examservice/fillQuestion/addfillQuestion`,
        method: 'post',
        data: form
    })
  },
  //根据id查询填空题信息
  getExamfillQuestion() {
    return request({
        url: `/examservice/fillQuestion/fillQuestionId`,
        method: 'get'
      })
  },
  //添加填空题
  addPaperMultiQuestionPaper(postPaper) {
    return request({
        url: `/examservice/paper/paperManage`,
        method: 'post',
        data: postPaper
    })
  },

  //添加判断题到判断题表
  addPaperjudgeQuestion(form) {
    return request({
        url: `/examservice/judgeQuestion/addJudgeQuestion`,
        method: 'post',
        data: form
    })
  },
  //根据id查询填空题信息
  getExamjudgeQuestion() {
    return request({
        url: `/examservice/judgeQuestion/judgeQuestionId`,
        method: 'get'
      })
  },
  //添加填空题
  addPapejudgeQuestionPaper(postPaper) {
    return request({
        url: `/examservice/paper/paperManage`,
        method: 'post',
        data: postPaper
    })
  },

}
