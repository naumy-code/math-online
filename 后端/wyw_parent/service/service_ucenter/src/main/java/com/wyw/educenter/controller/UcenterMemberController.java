package com.wyw.educenter.controller;


import com.wyw.commonutils.JwtUtils;
import com.wyw.commonutils.R;
import com.wyw.commonutils.ordervo.UcenterMemberOrder;
import com.wyw.educenter.entity.UcenterMember;
import com.wyw.educenter.entity.vo.ChangeVo;
import com.wyw.educenter.entity.vo.RegisterVo;
import com.wyw.educenter.service.UcenterMemberService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author wyw
 * @since 2020-10-09
 * //@CrossOrigin 解决跨域问题
 */
@RestController
@RequestMapping("/educenter/member")

public class UcenterMemberController {

    @Autowired
    private UcenterMemberService memberService;

    /**
     * 登录
     * @param member
     * @return
     */
    @PostMapping("login")
    public R loginUser(@RequestBody UcenterMember member) {
        // member对象封装手机号和密码
        // 调用service方法实现登录
        // 返回token值，使用jwt生成
        String token = memberService.login(member);
        return R.ok().data("token",token);
    }

    /**
     * 注册
     * @param registerVo
     * @return
     */
    @PostMapping("register")
    public R registerUser(@RequestBody RegisterVo registerVo) {
        memberService.register(registerVo);
        return R.ok();
    }

    /**
     * 根据token获取用户信息
     */
    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request) {
        // 调用jwt工具类的方法。根据request对象获取头信息，返回用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        // 查询数据库根据用户id获取用户信息
        UcenterMember member = memberService.getById(memberId);
        return R.ok().data("userInfo",member);
    }


    /**
     * 根据用户id获取用户信息
     */
    @PostMapping("getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable String id) {
        UcenterMember member = memberService.getById(id);
        //把member对象里面值复制给UcenterMemberOrder对象
        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(member,ucenterMemberOrder);
        return ucenterMemberOrder;
    }

    /**
     * 查询某一天注册人数
     */
    @GetMapping("countRegister/{day}")
    public R countRegister(@PathVariable String day) {
        Integer count = memberService.countRegisterDay(day);
        return R.ok().data("countRegister",count);
    }

    /**
     * 查询某时段注册人数
     */
    @GetMapping("countRegister/{begin}/{end}")
    public R countRegisterStartEnd(@PathVariable String begin,@PathVariable String end) {
        Integer count = memberService.countRegisterStartEnd(begin,end);
        return R.ok().data("countRegister",count);
    }

    /**
     * 根据用户id获取用户信息
     */
    @PostMapping("getInfoUc/{memberId}")
    public com.wyw.commonutils.ordervo.UcenterMemberOrder getInfo(@PathVariable String memberId) {
        // 根据用户id获取用户信息
        UcenterMember ucenterMember = memberService.getById(memberId);
        com.wyw.commonutils.ordervo.UcenterMemberOrder member = new com.wyw.commonutils.ordervo.UcenterMemberOrder();
        BeanUtils.copyProperties(ucenterMember,member);
        return member;
    }

    /**
     * 用户信息修改功能
     */
    @ApiOperation(value = "用户信息修改")
    @PostMapping("updateMember")
    public R updateMember(@RequestBody UcenterMember ucenterMember) {
        boolean flag = memberService.updateById(ucenterMember);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    /**
     * 根据用户id获取用户信息 个人中心用
     */
    @ApiOperation(value = "根据用户id获取用户信息 个人中心用")
    @PostMapping("getUserInfo/{id}")
    public R getUserInfo(@PathVariable String id) {
        UcenterMember member = memberService.getById(id);
        // 把member对象里面值复制给UcenterMemberOrder对象
        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(member,ucenterMemberOrder);
        return R.ok().data("memberInfo",ucenterMemberOrder);
    }

    /**
     * 更改密码
     */
    @ApiOperation(value = "更改密码")
    @PostMapping("updatePassword/{mobile}/{password}")
    public R updatePassword(@PathVariable String mobile,@PathVariable String password) {
        memberService.updatePassword(mobile,password);
        return R.ok().message("修改密码成功");
    }

}
