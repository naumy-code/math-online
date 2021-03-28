package com.wyw.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wyw.eduservice.entity.EduCourseCollect;
import com.wyw.eduservice.entity.frontvo.CourseCollectVo;
import com.wyw.eduservice.mapper.EduCourseCollectMapper;
import com.wyw.eduservice.service.EduCourseCollectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 课程收藏 服务实现类
 * </p>
 *
 * @author wyw
 * @since 2020-10-28
 */
@Service
public class EduCourseCollectServiceImpl extends ServiceImpl<EduCourseCollectMapper, EduCourseCollect> implements EduCourseCollectService {

    @Override
    public boolean isCollect(String courseId, String memberId) {
        QueryWrapper<EduCourseCollect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId).eq("member_id", memberId);
        Integer count = baseMapper.selectCount(queryWrapper);
        return count > 0;
    }

    @Override
    public void saveCourseCollect(String courseId, String memberId) {
        //未收藏则收藏
        if(!this.isCollect(courseId, memberId)) {
            EduCourseCollect courseCollect = new EduCourseCollect();
            courseCollect.setCourseId(courseId);
            courseCollect.setMemberId(memberId);
            this.save(courseCollect);
        }
    }

    @Override
    public List<CourseCollectVo> selectListByMemberId(String memberId) {
        return baseMapper.selectPageByMemberId(memberId);
    }

    @Override
    public boolean removeCourseCollect(String courseId, String memberId) {
        //已收藏则删除
        if(this.isCollect(courseId, memberId)) {
            QueryWrapper<EduCourseCollect> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("course_id", courseId).eq("member_id", memberId);
            return this.remove(queryWrapper);
        }
        return false;
    }
}
