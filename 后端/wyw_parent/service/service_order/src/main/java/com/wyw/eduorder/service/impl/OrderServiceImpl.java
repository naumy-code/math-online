package com.wyw.eduorder.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wyw.commonutils.R;
import com.wyw.commonutils.ordervo.CourseWebVoOrder;
import com.wyw.commonutils.ordervo.UcenterMemberOrder;
import com.wyw.eduorder.client.EduClient;
import com.wyw.eduorder.client.UcenterClient;
import com.wyw.eduorder.dto.AlipayBean;
import com.wyw.eduorder.entity.Order;
import com.wyw.eduorder.entity.PayLog;
import com.wyw.eduorder.mapper.OrderMapper;
import com.wyw.eduorder.service.OrderService;
import com.wyw.eduorder.utils.AliPayUtil;
import com.wyw.eduorder.utils.OrderNoUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-03-13
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private EduClient eduClient;

    @Autowired
    private UcenterClient ucenterClient;

    /**
     * 1 生成订单的方法
     */
    @Override
    public String createOrders(String courseId, String memberId) throws AlipayApiException {
        //通过远程调用根据用户id获取用户信息
        UcenterMemberOrder userInfoOrder = ucenterClient.getUserInfoOrder(memberId);

        //通过远程调用根据课程id获取课信息
        CourseWebVoOrder courseInfoOrder = eduClient.getCourseInfoOrder(courseId);

        //创建Order对象，向order对象里面设置需要数据
        Order order = new Order();

        //订单号
        order.setOrderNo(OrderNoUtil.getOrderNo());
        //课程id
        order.setCourseId(courseId);
        order.setCourseTitle(courseInfoOrder.getTitle());
        order.setCourseCover(courseInfoOrder.getCover());
        order.setTeacherName(courseInfoOrder.getTeacherName());
        order.setTotalFee(courseInfoOrder.getPrice());
        order.setMemberId(memberId);
        order.setMobile(userInfoOrder.getMobile());
        order.setNickname(userInfoOrder.getNickname());
        //订单状态（0：未支付 1：已支付）
        order.setStatus(0);
        //支付类型 ，支付宝支付1
        order.setPayType(1);
        baseMapper.insert(order);
         //返回订单号
        return order.getOrderNo();
    }

    /**
     * 根据用户id查询订单信息
     * @param memberId
     * @return
     */
    @Override
    public List<Order> selectByMemberId(String memberId) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create");
        queryWrapper.eq("member_id", memberId);
        return baseMapper.selectList(queryWrapper);
    }

    /**
     * 根据用户id和订单id做删除订单实现
     * @param orderId
     * @param memberId
     * @return
     */
    @Override
    public boolean removeById(String orderId, String memberId) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("id", orderId)
                .eq("member_id", memberId);
        return this.remove(queryWrapper);
    }

    /**
     * 根据订单号查询订单
     *
     * @param orderNo
     * @return 返回订单信息
     */
    @Override
    public Order getOrderByOrderNo(String orderNo) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("orderNo", orderNo);
        return (Order) baseMapper.selectList(queryWrapper);
    }

    /**
     * 更新订单
     *
     * @param order 订单对象
     * @return
     */
    @Override
    public R updateOrder(Order order) {
        // 更新订单表订单状态
        if(order.getStatus().intValue() == 1) {
            return null;
        }
        // 1 代表已经支付
        order.setStatus(1);
        System.out.println("===获取修改后的订单状态，检验是否修改===="+order.getStatus());
        int flag = baseMapper.updateById(order);
        if(flag == 1) {
            return R.ok();
        } else {
            return R.error();
        }
    }
}
