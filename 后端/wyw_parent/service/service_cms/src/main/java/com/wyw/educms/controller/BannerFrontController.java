package com.wyw.educms.controller;


import com.wyw.commonutils.R;
import com.wyw.educms.entity.CrmBanner;
import com.wyw.educms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前台bannber显示
 * </p>
 * @CrossOrigin
 * @author wyw
 * @since 2020-10-04
 */
@RestController
@RequestMapping("/educms/bannerfront")
public class BannerFrontController {

    @Autowired
    private CrmBannerService bannerService;

    /**
     * 查询所有banner
     * @return
     */
    @GetMapping("getAllBanner")
    public R getAllBanner() {
        List<CrmBanner> list = bannerService.selectAllBanner();
        return R.ok().data("list",list);
    }
}

