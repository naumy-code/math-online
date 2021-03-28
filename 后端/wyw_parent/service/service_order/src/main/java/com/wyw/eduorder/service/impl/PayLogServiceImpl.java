package com.wyw.eduorder.service.impl;

import com.github.wxpay.sdk.WXPayUtil;
import com.wyw.commonutils.R;
import com.wyw.eduorder.entity.Order;
import com.wyw.eduorder.entity.PayLog;
import com.wyw.eduorder.mapper.PayLogMapper;
import com.wyw.eduorder.service.OrderService;
import com.wyw.eduorder.service.PayLogService;
import com.wyw.eduorder.utils.HttpClient;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyw.servicebase.exceptionhandler.wywException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-03-13
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

//    @Autowired
//    private OrderService orderService;



    //生成微信支付二维码接口
//    @Override
//    public Map createNatvie(String orderNo) {
//        try {
//            //1 根据订单号查询订单信息
//            QueryWrapper<Order> wrapper = new QueryWrapper<>();
//            wrapper.eq("order_no",orderNo);
//            Order order = orderService.getOne(wrapper);
//
//            //2 使用map设置生成二维码需要参数
//            Map m = new HashMap();
//            m.put("appid","wx74862e0dfcf69954");
//            m.put("mch_id","1558950191");
//            m.put("nonce_str",WXPayUtil.generateNonceStr());
//            m.put("body",order.getCourseTitle()); //课程标题
//            m.put("out_trade_no",orderNo); //订单号
//            m.put("total_fee",order.getTotalFee().multiply(new BigDecimal("100")).longValue() + "");
//            m.put("spbill_create_ip","127.0.0.1");
//            m.put("notify_url","http://guli.shop/api/order/weixinPay/weixinNotify\n"); // 支付后回调地址
//            m.put("trade_type","NATIVE"); // 生成二维码支付类型
//
//            //3 发送httpclient请求，传递参数xml格式，微信支付提供的固定的地址
//            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
//            //设置xml格式的参数
//            client.setXmlParam(WXPayUtil.generateSignedXml(m,"T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
//            client.setHttps(true);
//            //执行post请求发送
//            client.post();
//
//            //4 得到发送请求返回结果
//            //返回内容，是使用xml格式返回
//            String xml = client.getContent();
//
//            //把xml格式转换map集合，把map集合返回
//            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
//
//            //最终返回数据 的封装
//            Map map = new HashMap();
//            map.put("out_trade_no",orderNo);
//            map.put("course_id",order.getCourseId());
//            map.put("total_fee",order.getTotalFee());
//            map.put("result_code",resultMap.get("result_code"));  //返回二维码操作状态码
//            map.put("code_url",resultMap.get("code_url"));        //二维码地址
//
//            return map;
//        } catch (Exception e) {
//            throw new wywException(20001,"生成二维码失败");
//        }
//
//    }

    //查询订单支付状态
//    @Override
//    public Map<String, String> queryPayStatus(String orderNo) {
//        try {
//            //1、封装参数
//            Map m = new HashMap<>();
//            m.put("appid","wx74862e0dfcf69954");
//            m.put("mch_id","1558950191");
//            m.put("out_trade_no",orderNo);
//            m.put("nonce_str",WXPayUtil.generateNonceStr());
//
//            //2 发送httpclient
//            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
//            client.setXmlParam(WXPayUtil.generateSignedXml(m,"T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
//            client.setHttps(true);
//            client.post();
//
//            //3 得到请求返回内容
//            String xml = client.getContent();
//            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
//            //6、转成Map再返回
//            return resultMap;
//        } catch (Exception e) {
//            return null;
//        }
//    }

    //添加支付记录和更新订单状态
//    @Override
//    public void updateOrdersStatus(Map<String, String> map) {
//        //从map获取订单号
//        String orderNo = map.get("out_trade_no");
//        //根据订单号查询订单信息
//        QueryWrapper<Order> wrapper = new QueryWrapper<>();
//        wrapper.eq("order_no",orderNo);
//        Order order = orderService.getOne(wrapper);
//
//        //更新订单表订单状态
//        if (order.getStatus().intValue() == 1) {
//            return;
//        }
//        order.setStatus(1);//1代表已经支付
//        orderService.updateById(order);
//
//        //向支付表添加支付记录
//        PayLog payLog = new PayLog();
//        payLog.setOrderNo(orderNo);  //订单号
//        payLog.setPayTime(new Date()); //订单完成时间
//        payLog.setPayType(1);//支付类型 1微信
//        payLog.setTotalFee(order.getTotalFee());//总金额(分)
//
//        //payLog.setTradeState(map.get("trade_state"));//支付状态
//        //payLog.setTransactionId(map.get("transaction_id")); //流水号
//        //payLog.setAttr(JSONObject.toJSONString(map));
//
//        baseMapper.insert(payLog);
//    }

    /**
     * 向PayLog表中插入数据
     * @param order
     * @return
     */
    @Override
    public R insertPayLog(Order order) {
        // 向支付表添加支付记录
        PayLog payLog = new PayLog();
        // 订单号
        payLog.setOrderNo(order.getOrderNo());
        payLog.setCourseId(order.getCourseId());
        payLog.setCourseTitle(order.getCourseTitle());
        payLog.setCourseCover(order.getCourseCover());
        payLog.setTeacherName(order.getTeacherName());
        payLog.setMemberId(order.getMemberId());
        // 订单完成时间
        payLog.setPayTime(new Date());
        // 支付类型 1支付宝
        payLog.setPayType(1);
        // 总金额(分)
        payLog.setTotalFee(order.getTotalFee());
        // 支付状态
        payLog.setTradeState(0);
        int flag = baseMapper.insert(payLog);
        if(flag == 1) {
            return R.ok();
        } else {
            return R.error();
        }
    }


    /**
     * 根据用户id和订单id做删除订单实现
     * @param payLogId
     * @param memberId
     * @return
     */
    @Override
    public boolean removeById(String payLogId, String memberId) {
        QueryWrapper<PayLog> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("id", payLogId)
                .eq("member_id", memberId);
        return this.remove(queryWrapper);
    }

    /**
     * 更新订单
     *
     * @param payLog 订单对象
     * @return
     */
    @Override
    public R updataPayLog(PayLog payLog) {

        // 更新订单表订单状态
        if(payLog.getTradeState() == 1) {
            return null;
        }
        // 1 代表已经支付
        payLog.setTradeState(1);
        System.out.println("===获取修改后的订单状态，检验是否修改===="+payLog.getTradeState());
        int flag = baseMapper.updateById(payLog);
        if(flag == 1) {
            return R.ok();
        } else {
            return R.error();
        }
    }
}

