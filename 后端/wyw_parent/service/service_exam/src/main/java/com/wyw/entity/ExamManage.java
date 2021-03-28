package com.wyw.entity;

import lombok.Data;

@Data
public class ExamManage {
    private Integer examCode;

    private String description;

    private String source;

    private Integer paperId;

    private String examDate;

    private Integer totalTime;

    private String grade;

    private String term;

    private String major;

    private String institute;

    private Integer totalScore;

    private String type;

    private String tips;
}