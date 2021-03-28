package com.wyw.staservice.client;

import com.wyw.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Administrator
 */
@Component
@FeignClient("service-edu")
public interface EduClient {

    /**
     * 查询某段时间浏览视频数量
     */
    @GetMapping("/eduservice/course/countCourseView/{begin}/{end}")
    public R countCourseViewStartEnd(@PathVariable String begin,@PathVariable String end);
}
