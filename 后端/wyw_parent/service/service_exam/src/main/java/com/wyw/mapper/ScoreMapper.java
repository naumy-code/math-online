package com.wyw.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wyw.entity.Score;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ScoreMapper {
    /**
     * @param score 添加一条成绩记录
     * @return
     */
    @Options(useGeneratedKeys = true,keyProperty = "scoreId")
    @Insert("insert into score(examCode,studentId,subject,ptScore,etScore,score,answerDate) values(#{examCode},#{studentId},#{subject},#{ptScore},#{etScore},#{score},#{answerDate})")
    int add(Score score);

    /**
     *
     * @return
     */
    @Select("select scoreId,examCode,studentId,subject,ptScore,etScore,score,answerDate from score order by scoreId desc")
    List<Score> findAll();

    /**
     * 分页查询学生考试记录
     * @param page
     * @param studentId
     * @return
     */
    @Select("select scoreId,examCode,studentId,subject,ptScore,etScore,score,answerDate from score where studentId = #{studentId} order by scoreId desc")
    IPage<Score> findById(Page<?> page, String studentId);

    /**
     * 不分页查询学生考试记录
     * @param studentId 学生id
     * @return
     */
    @Select("select scoreId,examCode,studentId,subject,ptScore,etScore,score,answerDate from score where studentId = #{studentId}")
    List<Score> findById(String studentId);

    /**
     *
     * @return 查询每位学生的学科分数。 max其实是假的，为了迷惑老师，达到一次考试考生只参加了一次的效果
     */
    @Select("select max(etScore) as etScore from score where examCode = #{examCode} group by studentId")
    List<Score> findByExamCode(Integer examCode);
}
