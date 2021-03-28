package com.wyw.eduservice.service.impl;

import com.wyw.eduservice.entity.EduCourse;
import com.wyw.eduservice.entity.EduCourseDescription;
import com.wyw.eduservice.entity.EduTeacher;
import com.wyw.eduservice.entity.frontvo.CourseFrontVo;
import com.wyw.eduservice.entity.frontvo.CourseWebVo;
import com.wyw.eduservice.entity.vo.CourseInfoVo;
import com.wyw.eduservice.entity.vo.CoursePublishVo;
import com.wyw.eduservice.mapper.EduCourseMapper;
import com.wyw.eduservice.service.EduChapterService;
import com.wyw.eduservice.service.EduCourseDescriptionService;
import com.wyw.eduservice.service.EduCourseService;
import com.wyw.eduservice.service.EduVideoService;
import com.wyw.servicebase.exceptionhandler.wywException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-03-02
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    /**
     * 课程描述注入
     */
    @Autowired
    private EduCourseDescriptionService courseDescriptionService;

    /**
     * 注入小节和章节service
     */
    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private EduChapterService chapterService;

    /**
     * 添加课程基本信息的方法
     * @param courseInfoVo
     * @return
     */
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //1 向课程表添加课程基本信息
        //CourseInfoVo对象转换eduCourse对象
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if(insert == 0) {
            //添加失败
            throw new wywException(20001,"添加课程信息失败");
        }

        //获取添加之后课程id
        String cid = eduCourse.getId();

        //2 向课程简介表添加课程简介
        //edu_course_description
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        //设置描述id就是课程id
        courseDescription.setId(cid);
        courseDescriptionService.save(courseDescription);

        return cid;
    }

    /**
     * 根据课程id查询课程基本信息
     */
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        //1 查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse,courseInfoVo);

        //2 查询描述表
        EduCourseDescription courseDescription = courseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());

        return courseInfoVo;
    }

    /**
     * 修改课程信息
     * @param courseInfoVo
     */
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //1 修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if(update == 0) {
            throw new wywException(20001,"修改课程信息失败");
        }

        //2 修改描述表
        EduCourseDescription description = new EduCourseDescription();
        description.setId(courseInfoVo.getId());
        description.setDescription(courseInfoVo.getDescription());
        courseDescriptionService.updateById(description);
    }

    /**
     * 根据课程id查询课程确认信息
     */
    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        //调用mapper
        CoursePublishVo publishCourseInfo = baseMapper.getPublishCourseInfo(id);
        return publishCourseInfo;
    }

    /**
     * 删除课程
     */
    @Override
    public void removeCourse(String courseId) {
        //1 根据课程id删除小节
        eduVideoService.removeVideoByCourseId(courseId);

        //2 根据课程id删除章节
        chapterService.removeChapterByCourseId(courseId);

        //3 根据课程id删除描述
        courseDescriptionService.removeById(courseId);

        //4 根据课程id删除课程本身
        int result = baseMapper.deleteById(courseId);
        //失败返回
        if(result == 0) {
            throw new wywException(20001,"请先删除未上传视频");
        }
    }

    /**
     * 1 条件查询带分页查询课程
     * @param pageParam
     * @param courseFrontVo
     * @return
     */
    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> pageParam, CourseFrontVo courseFrontVo) {
        //2 根据讲师id查询所讲课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        //判断条件值是否为空，不为空拼接
        //一级分类
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())) {
            wrapper.eq("subject_parent_id",courseFrontVo.getSubjectParentId());
        }
        //二级分类
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectId())) {
            wrapper.eq("subject_id",courseFrontVo.getSubjectId());
        }
        //关注度
        if(!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())) {
            wrapper.orderByDesc("buy_count");
        }
        //最新
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) {
            wrapper.orderByDesc("gmt_create");
        }
        //价格
        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {
            wrapper.orderByDesc("price");
        }

        baseMapper.selectPage(pageParam,wrapper);

        List<EduCourse> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        //下一页
        boolean hasNext = pageParam.hasNext();
        //上一页
        boolean hasPrevious = pageParam.hasPrevious();

        //把分页数据获取出来，放到map集合
        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        //map返回
        return map;
    }

    /**
     * 根据课程id，编写sql语句查询课程信息
     */
    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        EduCourse course = baseMapper.selectById(courseId);
        course.setViewCount(course.getViewCount() + 1);
        baseMapper.updateById(course);
        return baseMapper.getBaseCourseInfo(courseId);
    }

    /**
     * 统计一段时间内的课程浏览数量
     * @param begin
     * @param end
     * @return
     */
    @Override
    public Integer countCourseViewStartEnd(String begin,String end) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.between("view_count",begin,end);
        return baseMapper.selectCount(wrapper);
    }


}
