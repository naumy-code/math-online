package com.wyw.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wyw.entity.FillQuestion;
import com.wyw.entity.JudgeQuestion;
import com.wyw.entity.MultiQuestion;
import com.wyw.entity.vo.AnswerVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


/**
 * @author Administrator
 */
@Mapper
public interface AnswerMapper {
    /**
     * 并连接查询试题信息
     * @param page
     * @return
     */
    @Select("(select questionId, question, subject, score, section,level, questionType  from multi_question order by questionId ASC)" +
            "union (select questionId, question, subject, score, section,level, questionType  from judge_question order by questionId DESC) " +
            "union (select questionId, question, subject, score, section,level, questionType from fill_question order by questionId DESC)")
    IPage<AnswerVO> findAll(Page page);

    /**
     * 通过试题ID删除选择题信息
     * @param id
     * @return
     */
    @Delete("delete from multi_question where questionId = #{id}")
    int deleteMultiQuestion(Integer id);

    /**
     * 通过试题ID删除填空题
     * @param id
     * @return
     */
    @Delete("delete from fill_question where questionId = #{id}")
    int deletefillquestion(Integer id);

    /**
     * 通过试题ID删除选择试题信息
     * @param id
     * @return
     */
    @Delete("delete from judge_question where questionId = #{id}")
    int deleteJudgequestion(Integer id);

    /**
     * 通过试题ID查询判断题信息
     * @param id
     * @return
     */
    @Select("select * from judge_question where questionId = #{id}")
    JudgeQuestion findJudgequestionById(Integer id);

    /**
     * 通过试题ID查询选择题信息
     * @param id
     * @return
     */
    @Select("select * from multi_question where questionId = #{id}")
    MultiQuestion findmultiQuestionById(Integer id);

    /**
     * 通过试题ID查询填空题信息
     * @param id
     * @return
     */
    @Select("select * from fill_question where questionId = #{id}")
    FillQuestion findfillQuestionById(Integer id);

}
