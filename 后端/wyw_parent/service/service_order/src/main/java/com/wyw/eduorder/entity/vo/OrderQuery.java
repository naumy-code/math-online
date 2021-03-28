package com.wyw.eduorder.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Administrator
 */
@Data
public class OrderQuery {

    @ApiModelProperty(value = "课程名称,模糊查询")
    private String courseTitle;

    @ApiModelProperty(value = "查询开始时间", example = "2019-01-01 10:10:10")

    /**
     * 注意，这里使用的是String类型，前端传过来的数据无需进行类型转换
     */
    private String begin;

    @ApiModelProperty(value = "查询结束时间", example = "2019-12-01 10:10:10")
    private String end;
}