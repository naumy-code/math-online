package com.wyw.eduservice.service;

import com.wyw.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wyw.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author wyw
 * @since 2020-09-26
 */
public interface EduChapterService extends IService<EduChapter> {

    boolean deleteChapter(String chapterId);

    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    void removeChapterByCourseId(String courseId);
}
