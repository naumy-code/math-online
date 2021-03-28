package com.wyw.educenter.service.impl;

import com.wyw.commonutils.JwtUtils;
import com.wyw.educenter.entity.UcenterMember;
import com.wyw.educenter.entity.vo.ChangeVo;
import com.wyw.educenter.entity.vo.RegisterVo;
import com.wyw.educenter.mapper.UcenterMemberMapper;
import com.wyw.educenter.service.UcenterMemberService;
import com.wyw.educenter.utils.MD5;
import com.wyw.servicebase.exceptionhandler.wywException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-03-09
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    /**
     * 登录的方法
     */
    @Override
    public String login(UcenterMember member) {
        //获取登录手机号和密码
        String mobile = member.getMobile();
        String password = member.getPassword();

        //手机号和密码非空判断
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new wywException(20001,"登录失败");
        }

        //判断手机号是否正确
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        UcenterMember mobileMember = baseMapper.selectOne(wrapper);
        //判断查询对象是否为空
        //没有这个手机号
        if(mobileMember == null) {
            throw new wywException(20001,"登录失败");
        }

        //判断密码
        //因为存储到数据库密码肯定加密的
        //把输入的密码进行加密，再和数据库密码进行比较
        //加密方式 MD5
        if(!MD5.encrypt(password).equals(mobileMember.getPassword())) {
            throw new wywException(20001,"登录失败");
        }

        //判断用户是否禁用
        if(mobileMember.getIsDisabled()) {
            throw new wywException(20001,"登录失败");
        }

        //登录成功
        //生成token字符串，使用jwt工具类
        String jwtToken = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());
        return jwtToken;
    }

    /**
     * 注册的方法
     */
    @Override
    public void register(RegisterVo registerVo) {
        //获取注册的数据
        //验证码
        String code = registerVo.getCode();
        //手机号
        String mobile = registerVo.getMobile();
        //昵称
        String nickname = registerVo.getNickname();
        //密码
        String password = registerVo.getPassword();

        //非空判断
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)
                || StringUtils.isEmpty(code) || StringUtils.isEmpty(nickname)) {
            throw new wywException(20001,"注册失败");
        }
        //判断验证码
        //获取redis验证码
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if(!code.equals(redisCode)) {
            throw new wywException(20001,"注册失败");
        }

        //判断手机号是否重复，表里面存在相同手机号不进行添加
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if(count > 0) {
            throw new wywException(20001,"注册失败");
        }

        //数据添加数据库中
        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setNickname(nickname);
        //密码需要加密的
        member.setPassword(MD5.encrypt(password));
        //用户不禁用
        member.setIsDisabled(false);
        member.setAvatar("https://edu-wyw.oss-cn-beijing.aliyuncs.com/2020/09/26/0da500a58c284f6e83d51144bb605dd0file.png");
        baseMapper.insert(member);
    }

    /**
     * 根据openid判断
     */
    @Override
    public UcenterMember getOpenIdMember(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        UcenterMember member = baseMapper.selectOne(wrapper);
        return member;
    }

    /**
     * 查询某一天注册人数
     */
    @Override
    public Integer countRegisterDay(String day) {
        return baseMapper.countRegisterDay(day);
    }

    /**
     * 统计某段时间注册人数
     */
    @Override
    public Integer countRegisterStartEnd(String begin,String end) {
        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("gmt_create",begin,end);
        return baseMapper.selectCount(queryWrapper);
    }

    /**
     * 更改密码
     */
    @Override
    public void updatePassword(String mobile,String password) {
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new wywException(20001,"修改失败!");
        }
        //密码需要进行MD5加密
        String newpass = MD5.encrypt(password);
        baseMapper.password(newpass,mobile);
    }
    
}
