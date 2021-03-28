package com.wyw.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wyw.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wyw.eduservice.entity.frontvo.CourseFrontVo;
import com.wyw.eduservice.entity.frontvo.CourseWebVo;
import com.wyw.eduservice.entity.vo.CourseInfoVo;
import com.wyw.eduservice.entity.vo.CoursePublishVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author wyw
 * @since 2020-09-26
 */
public interface EduCourseService extends IService<EduCourse> {

    /**
     * 添加课程基本信息的方法
     * @param courseInfoVo 课程基本信息实体
     */
    String saveCourseInfo(CourseInfoVo courseInfoVo);

    /**
     *根据课程id查询课程基本信息
     * @param courseId 课程ID
     * @return
     */
    CourseInfoVo getCourseInfo(String courseId);

    /**
     * 修改课程信息
     */
    void updateCourseInfo(CourseInfoVo courseInfoVo);

    /**
     * 根据课程id查询课程确认信息
     * @param id
     * @return
     */
    CoursePublishVo publishCourseInfo(String id);

    /**
     * 通过课程ID删除课程
     * @param courseId
     */
    void removeCourse(String courseId);

    /**
     * 条件查询带分页查询课程前台
      */
    Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo);

    /**
     * 根据课程id，编写sql语句查询课程信息
     * @param courseId
     * @return 课程信息
     */
    CourseWebVo getBaseCourseInfo(String courseId);

    /**
     * 统计一段时间内的注册人数
     * @param begin
     * @param end
     * @return
     */
    Integer countCourseViewStartEnd(String begin,String end);
}

