package com.wyw.eduservice.service;

import com.wyw.eduservice.entity.EduCourseCollect;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wyw.eduservice.entity.frontvo.CourseCollectVo;

import java.util.List;

/**
 * <p>
 * 课程收藏 服务类
 * </p>
 *
 * @author wyw
 * @since 2020-10-28
 */
public interface EduCourseCollectService extends IService<EduCourseCollect> {

    boolean isCollect(String courseId,String memberId);

    void saveCourseCollect(String courseId,String memberId);

    List<CourseCollectVo> selectListByMemberId(String memberId);

    boolean removeCourseCollect(String courseId,String memberId);
}
