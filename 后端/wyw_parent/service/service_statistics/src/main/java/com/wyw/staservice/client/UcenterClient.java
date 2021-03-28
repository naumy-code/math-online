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
@FeignClient("service-ucenter")
public interface UcenterClient {

    /**
     * 查询某一天注册人数
     */
    @GetMapping("/educenter/member/countRegister/{day}")
    public R countRegister(@PathVariable("day") String day);

    /**
     * 查询某段时间注册人数
     */
    @GetMapping("/educenter/member/countRegister/{begin}/{end}")
    public R countRegisterStartEnd(@PathVariable String begin,@PathVariable String end);
}
