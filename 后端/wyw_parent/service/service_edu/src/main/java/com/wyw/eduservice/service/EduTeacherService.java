package com.wyw.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wyw.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author wyw
 * @since 2020-09-22
 */
public interface EduTeacherService extends IService<EduTeacher> {

    Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageTeacher);
}
