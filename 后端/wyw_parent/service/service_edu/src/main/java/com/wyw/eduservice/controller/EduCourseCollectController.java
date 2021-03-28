package com.wyw.eduservice.controller;


import com.wyw.commonutils.JwtUtils;
import com.wyw.commonutils.R;
import com.wyw.eduservice.entity.frontvo.CourseCollectVo;
import com.wyw.eduservice.service.EduCourseCollectService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 课程收藏 前端控制器
 * </p>
 *
 * @author wyw
 * @since 2020-10-28
 */
@RestController
@RequestMapping("/eduservice/edu-course-collect")
public class EduCourseCollectController {

    @Autowired
    private EduCourseCollectService courseCollectService;

    @ApiOperation(value = "判断是否收藏")
    @GetMapping("auth/is-collect/{courseId}")
    public R isCollect(
            @ApiParam(name = "courseId", value = "课程id", required = true)
            @PathVariable String courseId,
            HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        boolean isCollect = courseCollectService.isCollect(courseId, memberId);
        return R.ok().data("isCollect", isCollect);
    }

    @ApiOperation(value = "收藏课程")
    @PostMapping("auth/save/{courseId}")
    public R save(
            @ApiParam(name = "courseId", value = "课程id", required = true)
            @PathVariable String courseId,
            HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        courseCollectService.saveCourseCollect(courseId, memberId);
        return R.ok();
    }

    @ApiOperation(value = "获取课程收藏列表")
    @GetMapping("auth/list")
    public R collectList(HttpServletRequest request) {

        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        List<CourseCollectVo> list = courseCollectService.selectListByMemberId(memberId);
        return R.ok().data("items", list);
    }

    @ApiOperation(value = "取消收藏课程")
    @DeleteMapping("auth/remove/{courseId}")
    public R remove(
            @ApiParam(name = "courseId", value = "课程id", required = true)
            @PathVariable String courseId,
            HttpServletRequest request) {

        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        boolean result = courseCollectService.removeCourseCollect(courseId, memberId);
        if (result) {
            return R.ok().message("已取消");
        } else {
            return R.error().message("取消失败");
        }
    }

}

