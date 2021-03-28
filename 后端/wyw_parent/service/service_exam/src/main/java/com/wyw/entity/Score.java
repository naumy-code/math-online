package com.wyw.entity;

import lombok.Data;

/**
 * @author Administrator
 */
@Data
public class Score {
    private Integer examCode;

    private String studentId;

    private String subject;

    private Integer ptScore;

    private Integer etScore;

    private Integer score;

    private Integer scoreId;

    private String answerDate;
}