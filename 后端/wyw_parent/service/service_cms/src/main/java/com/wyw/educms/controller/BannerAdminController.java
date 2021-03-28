package com.wyw.educms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wyw.commonutils.R;
import com.wyw.educms.entity.CrmBanner;
import com.wyw.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 后台banner管理接口
 * </p>
 * @CrossOrigin
 * @author wyw
 */
@RestController
@RequestMapping("/educms/banneradmin")
public class BannerAdminController {

    @Autowired
    private CrmBannerService bannerService;

    /**
     * 1 分页查询banner
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable long page,@PathVariable long limit) {
        Page<CrmBanner> pageBanner = new Page<>(page,limit);
        //排序
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("gmt_create");
        bannerService.page(pageBanner,wrapper);
        //总记录数
        long total = pageBanner.getTotal();
        //数据list集合
        List<CrmBanner> records = pageBanner.getRecords();
        return R.ok().data("total",total).data("items",records);
    }

    /**
     * 2 添加banner
     * @param crmBanner
     * @return
     */
    @PostMapping("addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner) {
        bannerService.save(crmBanner);
        return R.ok();
    }

    @ApiOperation(value = "获取Banner")
    @GetMapping("get/{id}")
    public R get(@PathVariable String id) {
        CrmBanner banner = bannerService.getById(id);
        return R.ok().data("item", banner);
    }

    @ApiOperation(value = "修改Banner")
    @PutMapping("update")
    public R updateById(@RequestBody CrmBanner banner) {
        bannerService.updateById(banner);
        return R.ok();
    }

    @ApiOperation(value = "删除Banner")
    @DeleteMapping("remove/{id}")
    public R remove(@PathVariable String id) {
        bannerService.removeById(id);
        return R.ok();
    }
}

