package com.wyw.eduservice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wyw.eduservice.entity.EduComment;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.xml.stream.events.Comment;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author wyw
 * @since 2020-10-17
 */
public interface EduCommentService extends IService<EduComment> {

    void page(Page<Comment> pageParam,QueryWrapper<Comment> wrapper);
}
