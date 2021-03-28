package com.wyw.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wyw.entity.FillQuestion;
import com.wyw.entity.JudgeQuestion;
import com.wyw.entity.MultiQuestion;
import com.wyw.mapper.AnswerMapper;
import com.wyw.service.AnswerService;
import com.wyw.entity.vo.AnswerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 */
@Service
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private AnswerMapper answerMapper;

    @Override
    public IPage<AnswerVO> findAll(Page<AnswerVO> page) {
        return answerMapper.findAll(page);
    }


    @Override
    public int deleteMultiQuestion(Integer id) {
        return answerMapper.deleteMultiQuestion(id);
    }

    @Override
    public int deletefillquestion(Integer id) {
        return answerMapper.deletefillquestion(id);
    }

    @Override
    public int deleteJudgequestion(Integer id) {
        return answerMapper.deleteJudgequestion(id);
    }

    @Override
    public JudgeQuestion findById(Integer id) {
        return answerMapper.findJudgequestionById(id);
    }

    @Override
    public MultiQuestion findmultiQuestionById(Integer id) {
        return answerMapper.findmultiQuestionById(id);
    }

    @Override
    public FillQuestion findfillQuestionById(Integer id) {
        return answerMapper.findfillQuestionById(id);
    }
}
