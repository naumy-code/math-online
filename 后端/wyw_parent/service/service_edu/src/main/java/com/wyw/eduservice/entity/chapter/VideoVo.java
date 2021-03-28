package com.wyw.eduservice.entity.chapter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Administrator
 */
@Data
public class VideoVo {

    private String id;

    private String title;

    /**
     * 视频id
     */
    private String videoSourceId;

    @ApiModelProperty(value = "是否可以试听：0收费 1免费")
    private Boolean isFree;
}
