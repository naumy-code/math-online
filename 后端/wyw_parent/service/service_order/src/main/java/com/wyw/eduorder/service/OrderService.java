package com.wyw.eduorder.service;

import com.alipay.api.AlipayApiException;
import com.wyw.commonutils.R;
import com.wyw.eduorder.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-03-13
 */
public interface OrderService extends IService<Order> {

    /**
     * 1 生成订单的方法
     * @param courseId
     * @param memberIdByJwtToken
     * @return
     * @throws AlipayApiException
     */
    String createOrders(String courseId, String memberIdByJwtToken) throws AlipayApiException;

    /**
     * 查询会员id
     * @param memberId
     * @return
     */
    List<Order> selectByMemberId(String memberId);

    /**
     *  移除
      * @param orderId
     * @param memberId
     * @return
     */
    boolean removeById(String orderId,String memberId);


    Order getOrderByOrderNo(String outTradeNo);

    R updateOrder(Order orderByOrder);
}
