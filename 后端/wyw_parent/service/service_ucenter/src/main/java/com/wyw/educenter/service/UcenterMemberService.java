package com.wyw.educenter.service;

import com.wyw.educenter.entity.UcenterMember;
import com.wyw.educenter.entity.vo.ChangeVo;
import com.wyw.educenter.entity.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author wyw
 * @since 2020-10-05
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    /**
     * 登录的方法
     * @param member
     * @return Result
     */
    String login(UcenterMember member);

    /**
     * 注册的方法
     * @param registerVo 封装注册的信息
     */
    void register(RegisterVo registerVo);

    /**
     *根据openid判断
     * @param openid
     * @return Result
     */
    UcenterMember getOpenIdMember(String openid);

    /**
     *查询某一天注册人数
     * @param day 天
     * @return
     */
    Integer countRegisterDay(String day);

    /**
     * 查询某段时间的注册人数
     * @param begin  开始时间
     * @param end    结束时间
     */
    Integer countRegisterStartEnd(String begin,String end);

    /**
     *修改密码
     * @param mobile 电话号码
     * @param password 密码
     */
    void updatePassword(String mobile,String password);
}
