import request from '@/utils/request'
export default {
    //1 添加课程信息
    addCourseInfo(courseInfo) {
        return request({
            url: '/eduservice/course/addCourseInfo',
            method: 'post',
            data:courseInfo
          })
    },
    //2 查询所有讲师
    getListTeacher() {
        return request({
            url: '/eduservice/teacher/findAll',
            method: 'get'
          })
    },
    //根据课程id查询课程基本信息
    getCourseInfoId(id) {
        return request({
            url: '/eduservice/course/getCourseInfo/'+id,
            method: 'get'
          })
    },
    //修改课程信息
    updateCourseInfo(courseInfo) {
        return request({
            url: '/eduservice/course/updateCourseInfo',
            method: 'post',
            data: courseInfo
          })
    },
    //课程确认信息显示
    getPublihCourseInfo(id) {
        return request({
            url: '/eduservice/course/getPublishCourseInfo/'+id,
            method: 'get'
          })
    },
    //课程最终发布
    publihCourse(id) {
        return request({
            url: '/eduservice/course/publishCourse/'+id,
            method: 'post'
          })
    },
    //TODO 课程列表
    //课程最终发布
    getListCourse() {
        return request({
            url: '/eduservice/course',
            method: 'get'
          })
    },

    getCourseListPage(current, limit, teacherQuery) {
        return request({
          // eslint-disable-next-line spaced-comment。
          // Course
          //url: '/eduservice/teacher/pageTeacherCondition/'+current+"/"+limit,
          url: `/eduservice/course/pageCourseCondition/${current}/${limit}`,
          method: 'post',
          // teacherQuery条件对象，后端使用RequestBody获取数据
          // data表示把对象转换json进行传递到接口里面
          data: teacherQuery
        })
    },

    //删除课程
    deleteCourseId(id) {
    return request({
        url: `/eduservice/course/${id}`,
        method: 'delete'
      })
    },

}
