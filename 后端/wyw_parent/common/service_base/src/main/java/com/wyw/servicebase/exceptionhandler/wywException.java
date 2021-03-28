package com.wyw.servicebase.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Administrator
 * @AllArgsConstructor  //生成有参数构造方法
 * @NoArgsConstructor   //生成无参数构造
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class wywException extends RuntimeException {
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 异常信息
     */
    private String msg;
}
