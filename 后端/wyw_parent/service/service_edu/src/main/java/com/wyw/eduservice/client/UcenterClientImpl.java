package com.wyw.eduservice.client;

import com.wyw.commonutils.ordervo.UcenterMemberOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



/**
 * @author Administrator
 */
@Component
public class UcenterClientImpl implements UcenterClient {
    @Override
    public UcenterMemberOrder getInfo(String memberId) {
        return null;
    }
}