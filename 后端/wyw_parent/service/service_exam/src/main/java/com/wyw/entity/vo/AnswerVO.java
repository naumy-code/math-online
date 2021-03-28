package com.wyw.entity.vo;

import lombok.Data;

/**
 * @author Administrator
 */
@Data
public class AnswerVO {
    private Integer questionId;
    private String question;
    private String subject;
    private String score;
    private String section;
    private String level;
    private String questionType;
}
