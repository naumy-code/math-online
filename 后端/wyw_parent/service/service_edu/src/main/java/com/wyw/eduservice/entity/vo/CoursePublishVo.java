package com.wyw.eduservice.entity.vo;

import lombok.Data;

/**
 * @author Administrator
 * 获取首台最终发布界面时候的课程信息
 */
@Data
public class CoursePublishVo {
    private String id;
    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    /**
     * 只用于显示
     */
    private String price;
}
