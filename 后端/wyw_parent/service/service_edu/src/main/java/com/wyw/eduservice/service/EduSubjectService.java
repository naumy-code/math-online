package com.wyw.eduservice.service;

import com.wyw.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wyw.eduservice.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author wyw
 * @since 2020-09-26
 */
public interface EduSubjectService extends IService<EduSubject> {

    /**
     *  添加课程分类
     * @param file
     * @param subjectService
     */
    void saveSubject(MultipartFile file,EduSubjectService subjectService);

    /**
     *  课程分类列表（树形）
     * @return
     */
    List<OneSubject> getAllOneTwoSubject();

    /**
     * 删除课程分类
     */
    void removeSubject();
}
