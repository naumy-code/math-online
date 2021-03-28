package com.wyw.eduservice.controller;


import com.wyw.commonutils.R;
import com.wyw.eduservice.entity.EduTeacher;
import com.wyw.eduservice.entity.vo.TeacherQuery;
import com.wyw.eduservice.service.EduTeacherService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 讲师 前端控制器
 * </p>
 * @author wyw
 * @since 2020-09-22
 * //@CrossOrigin
 */
@Api(description="讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    /**
     *访问地址： http://localhost:8001/eduservice/teacher/findAll
     *访问地址： http://localhost:8001/swagger-ui.html#/
     *1 查询讲师表所有数据
     *rest风格
     * @return
     */
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R findAllTeacher() {
        //调用service的方法实现查询所有的操作
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("items",list);
    }

    /**
     * 2 逻辑删除讲师的方法
     * @param id
     * @return
     */
    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("{id}")
    public R removeTeacher(@ApiParam(name = "id", value = "讲师ID", required = true)
                           @PathVariable String id) {
        boolean flag = teacherService.removeById(id);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    /**
     *3 分页查询讲师的方法
     *current 当前页
     *limit 每页记录数
     * @param current
     * @param limit
     * @return
     */
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable long current,
                             @PathVariable long limit) {
        //创建page对象
        //current代表当前页 limit代表每页的记录数
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);

        //调用方法实现分页
        //调用方法时候，底层封装，把分页所有数据封装到pageTeacher对象里面
        teacherService.page(pageTeacher,null);
        //总记录数
        long total = pageTeacher.getTotal();
        //数据list集合
        List<EduTeacher> records = pageTeacher.getRecords();

        return R.ok().data("total",total).data("rows",records);
    }

    /**
     * 4 条件查询带分页的方法
     * @param current
     * @param limit
     * @param teacherQuery
     * @return
     */
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current,@PathVariable long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery) {
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);

        //构建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        // 多条件组合查询
        // mybatis学过 动态sql
        String name = teacherQuery.getName();
        String begin = teacherQuery.getBegin();
        Integer level = teacherQuery.getLevel();
        String end = teacherQuery.getEnd();
        //判断条件值是否为空，如果不为空拼接条件
        if(!StringUtils.isEmpty(name)) {
            //构建条件
            wrapper.like("name",name);
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create",end);
        }


        //排序
        wrapper.orderByDesc("gmt_create");

        //调用方法实现条件查询分页
        teacherService.page(pageTeacher,wrapper);
        //总记录数
        long total = pageTeacher.getTotal();
        //数据list集合
        List<EduTeacher> records = pageTeacher.getRecords();

        //        Map map = new HashMap();
        //        map.put("total",total);
        //        map.put("rows",records);
        //        return R.ok().data(map);

        return R.ok().data("total",total).data("rows",records);
    }

    /**
     * 添加讲师接口的方法
     */
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = teacherService.save(eduTeacher);
        if(save) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    /**
     * 根据讲师id进行查询
     */
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id) {
        EduTeacher eduTeacher = teacherService.getById(id);
        return R.ok().data("teacher",eduTeacher);
    }

    /**
     * 讲师修改功能
     */
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean flag = teacherService.updateById(eduTeacher);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }
}



