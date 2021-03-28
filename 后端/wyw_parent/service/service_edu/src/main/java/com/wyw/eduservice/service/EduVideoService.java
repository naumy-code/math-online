package com.wyw.eduservice.service;

import com.wyw.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author wyw
 * @since 2020-09-26
 */
public interface EduVideoService extends IService<EduVideo> {

    /**
     * 根据课程id删除小节
     * @param courseId
     */
    void removeVideoByCourseId(String courseId);
}
