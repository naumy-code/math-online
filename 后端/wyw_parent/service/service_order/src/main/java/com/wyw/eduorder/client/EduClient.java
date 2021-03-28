package com.wyw.eduorder.client;

import com.wyw.commonutils.R;
import com.wyw.commonutils.ordervo.CourseWebVoOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Administrator
 */
@Component
@FeignClient("service-edu")
public interface EduClient {

    /**
     * 根据课程id查询课程信息
     */
    @PostMapping("/eduservice/coursefront/getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable("id") String id);

    /**
     * 根据课程id查询课程信息,销售数量加一
     * @param id
     * @return
     */
    @PostMapping("/eduservice/coursefront/getCourse/{id}")
    public R getCourse(@PathVariable String id) ;

}
