package com.wyw.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wyw.commonutils.R;
import com.wyw.eduservice.entity.EduCourse;
import com.wyw.eduservice.entity.EduTeacher;
import com.wyw.eduservice.entity.vo.CourseInfoVo;
import com.wyw.eduservice.entity.vo.CoursePublishVo;
import com.wyw.eduservice.entity.vo.CourseQuery;
import com.wyw.eduservice.entity.vo.TeacherQuery;
import com.wyw.eduservice.service.EduCourseService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 * @CrossOrigin
 * @author wyw
 * @since 2020-09-26
 */
@RestController
@RequestMapping("/eduservice/course")
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    //TODO  完善条件查询带分页
    /**
     * 课程列表 基本实现
     */
    @GetMapping
    public R getCourseList() {
        List<EduCourse> list = courseService.list(null);
        return R.ok().data("list",list);
    }

    /**
     * 添加课程基本信息的方法
     */
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        //返回添加之后课程id，为了后面添加大纲使用
        String id = courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId",id);
    }

    /**
     * 根据课程id查询课程基本信息
     */
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId) {
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo",courseInfoVo);
    }

    /**
     * 修改课程信息
     */
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    /**
     * 根据课程id查询课程确认信息
     */
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id) {
        CoursePublishVo coursePublishVo = courseService.publishCourseInfo(id);
        return R.ok().data("publishCourse",coursePublishVo);
    }

    /**
     * 课程最终发布
     * 修改课程状态
     * @param id
     * @return
     */
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        //设置课程发布状态
        eduCourse.setStatus("Normal");
        courseService.updateById(eduCourse);
        return R.ok();
    }

    /**
     * 删除课程
     */
    @DeleteMapping("{courseId}")
    public R deleteCourse(@PathVariable String courseId) {
        courseService.removeCourse(courseId);
        return R.ok();
    }

    /**
     * 查询某时段视频浏览人数
     */
    @GetMapping("countCourseView/{begin}/{end}")
    public R countRegisterStartEnd(@PathVariable String begin,@PathVariable String end) {
        Integer count = courseService.countCourseViewStartEnd(begin,end);
        return R.ok().data("countCourseView",count);
    }


    /**
     * 4 条件查询带分页的方法
     */
    @PostMapping("pageCourseCondition/{current}/{limit}")
    public R pageCourseCondition(@PathVariable long current,@PathVariable long limit,
                                  @RequestBody(required = false) CourseQuery courseQuery) {
        //创建page对象
        //Course
        Page<EduCourse> pageCourse = new Page<>(current,limit);
        //构建条件
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        // 多条件组合查询
        // mybatis学过 动态sql
        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();
        //判断条件值是否为空，如果不为空拼接条件
        if(!StringUtils.isEmpty(title)) {
            //构建条件
            wrapper.like("title",	title);
        }
        if(!StringUtils.isEmpty(status)) {
            wrapper.eq("status",status);
        }
        //排序
        wrapper.orderByDesc("gmt_create");
        //调用方法实现条件查询分页
        courseService.page(pageCourse,wrapper);
        //总记录数
        long total = pageCourse.getTotal();
        //数据list集合
        List<EduCourse> records = pageCourse.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }
}

