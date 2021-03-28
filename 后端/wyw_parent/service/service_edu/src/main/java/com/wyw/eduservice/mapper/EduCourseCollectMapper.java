package com.wyw.eduservice.mapper;

import com.wyw.eduservice.entity.EduCourseCollect;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wyw.eduservice.entity.frontvo.CourseCollectVo;

import java.util.List;

/**
 * <p>
 * 课程收藏 Mapper 接口
 * </p>
 *
 * @author wyw
 * @since 2020-10-28
 */
public interface EduCourseCollectMapper extends BaseMapper<EduCourseCollect> {

    List<CourseCollectVo> selectPageByMemberId(String memberId);
}
