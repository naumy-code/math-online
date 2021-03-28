package com.wyw.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wyw.entity.FillQuestion;
import com.wyw.entity.JudgeQuestion;
import com.wyw.entity.MultiQuestion;
import com.wyw.entity.vo.AnswerVO;

/**
 * @author Administrator
 */
public interface AnswerService {

    IPage<AnswerVO> findAll(Page<AnswerVO> page);

    int deleteMultiQuestion(Integer id);

    int deletefillquestion(Integer id);

    int deleteJudgequestion(Integer id);

    JudgeQuestion findById(Integer id);

    MultiQuestion findmultiQuestionById(Integer id);

    FillQuestion findfillQuestionById(Integer id);
}
