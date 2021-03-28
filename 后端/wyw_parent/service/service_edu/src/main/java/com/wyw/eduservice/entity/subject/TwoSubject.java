package com.wyw.eduservice.entity.subject;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Administrator
 * 二级分类
 */
@Data
public class TwoSubject {
    private String id;
    private String title;

    @ApiModelProperty(value = "父ID")
    private String parentId;
}
