package com.wyw.msmservice.service;

import java.util.Map;

/**
 * @author Administrator
 */
public interface MsmService {

    /**
     * 发送短信的方法
     * @param param
     * @param phone
     * @return
     */
    boolean send(Map<String, Object> param, String phone);
}
