package com.wyw.eduservice.mapper;

import com.wyw.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程科目 Mapper 接口
 * </p>
 *
 * @author wyw
 * @since 2020-09-26
 */
public interface EduSubjectMapper extends BaseMapper<EduSubject> {
    void truncate();
}
