package com.wyw.eduservice.entity.subject;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * 一级分类
 */ 
@Data
public class OneSubject {
    private String id;
    private String title;

    @ApiModelProperty(value = "父ID")
    private String parentId;

    /**
     * 一个一级分类有多个二级分类
     */
    private List<TwoSubject> children = new ArrayList<>();
}
