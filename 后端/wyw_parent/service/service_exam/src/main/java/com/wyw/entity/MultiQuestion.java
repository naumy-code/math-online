package com.wyw.entity;

import lombok.Data;

/**
 * // 选择题实体
 * @author Administrator
 */
@Data
public class MultiQuestion {
    private Integer questionId;

    private String subject;

    private String section;

    private String answerA;

    private String answerB;

    private String answerC;

    private String answerD;

    private String question;

    private String level;

    private String rightAnswer;

    /**
     * 题目解析
     */
    private String analysis;

    private Integer score;

    private String questionType;
}