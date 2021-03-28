package com.wyw.entity;

import lombok.Data;

/**
 * 填空题实体类
 */
@Data
public class FillQuestion {
    private Integer questionId;

    private String subject;

    private String question;

    private String answer;

    private Integer score;

    private String level;

    private String section;

    /**
     * //题目解析
     */
    private String analysis;

    private String questionType;
}
