package com.wyw.entity;

import lombok.Data;

/**
 * 判断题实体类
 * @author Administrator
 */
@Data
public class JudgeQuestion {
    private Integer questionId;

    private String subject;

    private String question;

    private String answer;

    private String level;

    private String section;

    private Integer score;

    /**
     * 题目解析
     */
    private String analysis;

    private String questionType;
}