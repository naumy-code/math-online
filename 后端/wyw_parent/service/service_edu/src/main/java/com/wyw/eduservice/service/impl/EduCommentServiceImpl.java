package com.wyw.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wyw.eduservice.entity.EduComment;
import com.wyw.eduservice.mapper.EduCommentMapper;
import com.wyw.eduservice.service.EduCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.xml.stream.events.Comment;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author wyw
 * @since 2020-10-17
 */
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {

    @Override
    public void page(Page<Comment> pageParam,QueryWrapper<Comment> wrapper) {

    }
}
