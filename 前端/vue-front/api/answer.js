import request from '@/utils/request'

export default {
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
  },

  //添加考试信息
  addAnswerMessage(Score) {
      return request({
          url: `/examservice/Score/addScore`,
          method: 'post',
          data: Score
        })
  },

}