package com.wyw.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * Course
 * @author Administrator
 */
@Data
public class CourseQuery {

    @ApiModelProperty(value = "课程名称,模糊查询")
    private String title;

    @ApiModelProperty(value = "课程状态")
    private String status;
}