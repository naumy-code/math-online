import request from '@/utils/request'
export default {
    //1 课程分类列表
    getSubjectList() {
        return request({
            url: '/eduservice/subject/getAllSubject',
            method: 'get'
          })
    },
    //清空课程
    deleteSubject() {
        return request({
            url: `/eduservice/subject/deleteSubject`,
            method: 'delete'
          })
    },
    //通过ID删除课程
    deleteSubjectById(id) {
        return request({
            url: `/eduservice/subject/deleteCourseById/${id}`,
            method: 'delete'
            })
    },
    //通过ID查看课程
    getSubjectById(id) {
        return request({
            url: `/eduservice/subject/getSubjectById/${id}`,
            method: 'get'
            })
    },
    //添加课程科目
    addSubject(Subject) {
        return request({
            url: `/eduservice/subject/addsubjectByParentId`,
            method: 'post',
            data: Subject
        })
    },
    //修改课程科目
    updateSubjectInfo(Subject) {
        return request({
            url: `/eduservice/subject/updateSubject`,
            method: 'post',
            data: Subject
        })
    },
}
