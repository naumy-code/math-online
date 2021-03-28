package com.wyw.eduservice.mapper;

import com.wyw.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wyw.eduservice.entity.frontvo.CourseWebVo;
import com.wyw.eduservice.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author wyw
 * @since 2020-09-26
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    CourseWebVo getBaseCourseInfo(String courseId);

    CoursePublishVo getPublishCourseInfo(String id);
}

