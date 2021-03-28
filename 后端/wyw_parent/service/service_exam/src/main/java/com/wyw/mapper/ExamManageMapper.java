package com.wyw.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wyw.entity.ExamManage;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ExamManageMapper {
    @Select("select * from exam_manage")
    List<ExamManage> findAll();

    /**
     * 分页查询
     * @param page
     * @return
     */
    @Select("select * from exam_manage")
    IPage<ExamManage> findAll(Page page);

    /**
     * 通过ExamCode查询考试
     * @param examCode
     * @return
     */
    @Select("select * from exam_manage where examCode = #{examCode}")
    ExamManage findById(Integer examCode);

    /**
     * 通过ExamCode删除考试信息
     * @param examCode
     * @return
     */
    @Delete("delete from exam_manage where examCode = #{examCode}")
    int delete(Integer examCode);

    /**
     * 修改考试信息
     * @param exammanage
     * @return
     */
    @Update("update exam_manage set description = #{description},source = #{source},paperId = #{paperId}," +
            "examDate = #{examDate},totalTime = #{totalTime},grade = #{grade},term = #{term}," +
            "major = #{major},institute = #{institute},totalScore = #{totalScore}," +
            "type = #{type},tips = #{tips} where examCode = #{examCode}")
    int update(ExamManage exammanage);


    /**
     * 添加考试信息
     * @param exammanage
     * @return
     */
    @Options(useGeneratedKeys = true,keyProperty = "examCode")
    @Insert("insert into exam_manage(description,source,paperId,examDate,totalTime,grade,term,major,institute,totalScore,type,tips)" +
            " values(#{description},#{source},#{paperId},#{examDate},#{totalTime},#{grade},#{term},#{major},#{institute},#{totalScore},#{type},#{tips})")
    int add(ExamManage exammanage);

    /**
     * 查询最后一条记录的paperId,返回给前端达到自增效果
     * @return paperId
     */
    @Select("select paperId from exam_manage order by paperId desc limit 1")
    ExamManage findOnlyPaperId();
}
