package com.wyw.eduservice.controller;


import com.wyw.eduservice.entity.EduSubject;
import com.wyw.eduservice.entity.EduTeacher;
import com.wyw.eduservice.entity.subject.OneSubject;
import com.wyw.eduservice.service.EduSubjectService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import com.wyw.commonutils.R;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 * @CrossOrigin
 * @author wyw
 * @since 2020-09-26
 */
@RestController
@RequestMapping("/eduservice/subject")
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    /**
     * 添加课程分类
     * 获取上传过来文件，把文件内容读取出来
     */
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file) {
        //上传过来excel文件
        subjectService.saveSubject(file,subjectService);
        return R.ok();
    }

    /**
     * 课程分类列表（树形）
     * @return
     */
    @GetMapping("getAllSubject")
    public R getAllSubject() {
        //list集合泛型是一级分类
        List<OneSubject> list = subjectService.getAllOneTwoSubject();
        return R.ok().data("list",list);
    }

    /**
     * 清空Subject
     */
    @DeleteMapping("deleteSubject")
    public R deleteCourse() {
        subjectService.removeSubject();
        return R.ok();
    }

    /**
     * 逻辑删除课程科目的方法
     * @param id
     * @return
     */
    @ApiOperation(value = "逻辑删除课科目")
    @DeleteMapping("deleteCourseById/{id}")
    public R deleteCourseById(@ApiParam(name = "id", value = "课程科目ID", required = true)
                           @PathVariable String id) {
        boolean flag = subjectService.removeById(id);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    /**
     * 添加课程科目接口的方法
     */
    @PostMapping("addsubjectByParentId")
    public R addsubjectByParentId(@RequestBody EduSubject eduSubject) {
        boolean save = subjectService.save(eduSubject);
        if(save) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    /**
     * 科目修改功能
     */
    @PostMapping("updateSubject")
    public R updateSubject(@RequestBody EduSubject eduSubject) {
        boolean flag = subjectService.updateById(eduSubject);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    /**
     * 根据科目id进行查询
     */
    @GetMapping("getSubjectById/{id}")
    public R getSubjectById(@PathVariable String id) {
        EduSubject eduSubject = subjectService.getById(id);
        return R.ok().data("subject",eduSubject);
    }

}
