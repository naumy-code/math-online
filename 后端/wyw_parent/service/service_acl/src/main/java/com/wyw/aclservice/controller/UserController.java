package com.wyw.aclservice.controller;


import com.wyw.aclservice.entity.User;
import com.wyw.aclservice.service.RoleService;
import com.wyw.aclservice.service.UserService;
import com.wyw.commonutils.MD5;
import com.wyw.commonutils.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author naumy
 * @since 2020-11-12
 * @CrossOrigin
 */
@RestController
@RequestMapping("/admin/acl/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    /**
     * 分页查询管理员用户列表
     * @param page
     * @param limit
     * @param userQueryVo
     * @return
     */
    @ApiOperation(value = "获取管理用户分页列表")
    @GetMapping("{page}/{limit}")
    public R index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
             User userQueryVo) {
        Page<User> pageParam = new Page<>(page, limit);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(userQueryVo.getUsername())) {
            wrapper.like("username",userQueryVo.getUsername());
        }

        IPage<User> pageModel = userService.page(pageParam, wrapper);
        return R.ok().data("items", pageModel.getRecords()).data("total", pageModel.getTotal());
    }

    /**
     * 新增加管理员用户
     * @param user
     * @return
     */
    @ApiOperation(value = "新增管理用户")
    @PostMapping("save")
    public R save(@RequestBody User user) {
        user.setPassword(MD5.encrypt(user.getPassword()));
        userService.save(user);
        return R.ok();
    }

    /**
     * 修改管理员用户
     * @param user
     * @return
     */
    @ApiOperation(value = "修改管理用户")
    @PutMapping("update")
    public R updateById(@RequestBody User user) {
        userService.updateById(user);
        return R.ok();
    }

    /**
     * 删除管理员用户
     * @param id
     * @return
     */
    @ApiOperation(value = "删除管理用户")
    @DeleteMapping("remove/{id}")
    public R remove(@PathVariable String id) {
        userService.removeById(id);
        return R.ok();
    }

    /**
     * 批量删除管理员用户
     * @param idList
     * @return
     */
    @ApiOperation(value = "根据id列表删除管理用户")
    @DeleteMapping("batchRemove")
    public R batchRemove(@RequestBody List<String> idList) {
        userService.removeByIds(idList);
        return R.ok();
    }

    /**
     * 获取管理员的角色
     * @param userId
     * @return
     */
    @ApiOperation(value = "根据用户获取角色数据")
    @GetMapping("/toAssign/{userId}")
    public R toAssign(@PathVariable String userId) {
        Map<String, Object> roleMap = roleService.findRoleByUserId(userId);
        return R.ok().data(roleMap);
    }

    /**
     * 为管理员分配角色
     * @param userId
     * @param roleId
     * @return
     */
    @ApiOperation(value = "根据用户分配角色")
    @PostMapping("/doAssign")
    public R doAssign(@RequestParam String userId,@RequestParam String[] roleId) {
        roleService.saveUserRoleRealtionShip(userId,roleId);
        return R.ok();
    }

    /**
     * 获取管理员用户信息
     * @param id
     * @return
     */
    @ApiOperation(value = "获取用户")
    @GetMapping("get/{id}")
    public R get(@PathVariable String id) {
        User user = userService.getById(id);
        return R.ok().data("item", user);
    }

}
