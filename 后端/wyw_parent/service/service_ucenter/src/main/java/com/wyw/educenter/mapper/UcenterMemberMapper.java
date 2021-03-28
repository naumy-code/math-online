package com.wyw.educenter.mapper;

import com.wyw.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-10-05
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    /**
     * 统计注册
     * @param day
     * @return
     */
    Integer countRegisterDay(String day);

    /**
     * 修改密码
     * @param password
     * @param mobile
     */
    void password(String password,String mobile);

    /**
     * 统计某段时间的注册人数
     * @param begin
     * @param end
     * @return
     */
    Integer countRegisterStartEnd(String begin,String end);
}
