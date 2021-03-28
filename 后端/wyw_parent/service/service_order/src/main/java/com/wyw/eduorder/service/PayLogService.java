package com.wyw.eduorder.service;

import com.wyw.commonutils.R;
import com.wyw.eduorder.entity.Order;
import com.wyw.eduorder.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-03-13
 */
public interface PayLogService extends IService<PayLog> {


    R insertPayLog(Order order);

    boolean removeById(String orderId,String memberId);

    R updataPayLog(PayLog payLog);

    //生成微信支付二维码接口
//    Map createNatvie(String orderNo);
//
//    //根据订单号查询订单支付状态
//    Map<String, String> queryPayStatus(String orderNo);
//
//    //向支付表添加记录，更新订单状态
//    void updateOrdersStatus(Map<String, String> map);

}
