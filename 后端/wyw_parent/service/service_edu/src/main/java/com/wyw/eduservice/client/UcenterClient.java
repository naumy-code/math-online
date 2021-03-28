package com.wyw.eduservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Administrator
 */
@Component
@FeignClient(name="service-ucenter",fallback = UcenterClientImpl.class)
public interface UcenterClient {

    /**
     * 根据token字符串获取用户信息
     * @param memberId
     * @return
     */
    @PostMapping("/educenter/member/getInfoUc/{memberId}")
    public com.wyw.commonutils.ordervo.UcenterMemberOrder getInfo(@PathVariable("memberId") String memberId);
}