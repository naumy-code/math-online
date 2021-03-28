package com.wyw.eduservice.controller;

import com.wyw.commonutils.R;
import org.springframework.web.bind.annotation.*;

/**
 * @author Administrator
 * @CrossOrigin  //解决跨域
 */
@RestController
@RequestMapping("/eduservice/user")
public class EduLoginController {
    /**
     * login
     */
    @PostMapping("login")
    public R login() {
        return R.ok().data("token","admin");
    }

    /**
     * info
     * @return
     */
    @GetMapping("info")
    public R info() {
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","\n" +
                "https://edu-wyw.oss-cn-beijing.aliyuncs.com/%E5%B0%8F%E5%AD%A6%E6%95%B0%E5%AD%A6%E5%9C%A8%E7%BA%BF%E6%95%99%E8%82%B2/%E5%A4%B4%E5%83%8F/default.jpeg");
    }
}
