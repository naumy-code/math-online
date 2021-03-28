package com.wyw.eduorder.controller;


import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wyw.commonutils.JwtInfo;
import com.wyw.commonutils.JwtUtils;
import com.wyw.commonutils.R;
import com.wyw.eduorder.client.EduClient;
import com.wyw.eduorder.dto.AlipayBean;
import com.wyw.eduorder.entity.Order;
import com.wyw.eduorder.entity.PayLog;
import com.wyw.eduorder.entity.vo.OrderQuery;
import com.wyw.eduorder.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wyw.eduorder.service.PayLogService;
import com.wyw.eduorder.utils.AliPayUtil;
import com.wyw.eduorder.utils.AlipayProperties;
import com.wyw.eduorder.utils.OrderEnum;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-10-13
 * @CrossOrigin
 */
@RestController
@RequestMapping("/eduorder/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Resource
    private AlipayProperties alipayProperties;
    @Resource
    private AliPayUtil aliPayUtil;
    @Autowired
    private PayLogService payLogService;
    @Autowired
    private EduClient eduClient;

    /**
     * 1 生成订单的方法
     * @param courseId
     * @param request
     * @return
     * @throws AlipayApiException
     */
    @PostMapping("createOrder/{courseId}")
    public R saveOrder(@PathVariable String courseId, HttpServletRequest request) throws AlipayApiException {
        //创建订单，返回订单号
        String orderNo =
                orderService.createOrders(courseId,JwtUtils.getMemberIdByJwtToken(request));
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        Order order = orderService.getOne(wrapper);
        payLogService.insertPayLog(order);
        return R.ok().data("orderId",orderNo);
    }


    /**
     * 1 调用支付宝接口的方法
     * @param id
     * @param request
     * @return
     * @throws AlipayApiException
     */
    @PostMapping("Alipay/{id}")
    public R Alipay(@PathVariable String id, HttpServletRequest request) throws AlipayApiException {
        // 1.根据订单id查询订单信息
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",id);
        Order order = orderService.getOne(wrapper);
        // 2.根据订单id查询订单日志表信息
        QueryWrapper<PayLog> wrapperPayLog = new QueryWrapper<>();
        wrapperPayLog.eq("order_no",id);
        PayLog payLog = payLogService.getOne(wrapperPayLog);
        // 使课程表的购买数量加一
        eduClient.getCourse(order.getCourseId());
        // 3. 调用支付宝
        AlipayBean alipayBean = new AlipayBean();
        alipayBean.setOut_trade_no(order.getOrderNo());
        alipayBean.setSubject("充值:" + order.getTotalFee());
        alipayBean.setTotal_amount(order.getTotalFee().toString());
        String pay = aliPayUtil.pay(alipayBean);
        // 4. 修改订单表和支付日志表的订单状态
        orderService.updateOrder(order);
        payLogService.updataPayLog(payLog);
        System.out.println("pay:" + pay);
        return R.ok().data("pay",pay);
    }

    /**
     * 2 根据订单id查询订单信息
     * @param orderId
     * @return
     */
    @GetMapping("getOrderInfo/{orderId}")
    public R getOrderInfo(@PathVariable String orderId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        Order order = orderService.getOne(wrapper);
        return R.ok().data("item",order);
    }

    /**
     * 3.根据课程id和用户id查询订单表中订单状态
     * @param courseId
     * @param memberId
     * @return
     */
    @GetMapping("isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable String courseId,@PathVariable String memberId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.eq("member_id",memberId);
        //支付状态 1代表已经支付
        wrapper.eq("status",1);
        int count = orderService.count(wrapper);
        //已经支付
        if(count>0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 4 条件查询带分页的方法
     * @param current
     * @param limit
     * @param orderQuery
     * @return
     */
    @PostMapping("pageOrderCondition/{current}/{limit}")
    public R pageOrderCondition(@PathVariable long current,@PathVariable long limit,
                                  @RequestBody(required = false) OrderQuery orderQuery) {
        //创建page对象
        Page<Order> pageOrder = new Page<>(current,limit);

        //构建条件
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        // 多条件组合查询
        // mybatis学过 动态sql
        String title = orderQuery.getCourseTitle();
        String begin = orderQuery.getBegin();
        String end = orderQuery.getEnd();
        //判断条件值是否为空，如果不为空拼接条件
        if(!StringUtils.isEmpty(title)) {
            //构建条件
            wrapper.like("course_title",title);
        }
        if(!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create",end);
        }

        //排序
        wrapper.orderByDesc("gmt_create");

        //调用方法实现条件查询分页
        orderService.page(pageOrder,wrapper);

        //总记录数
        long total = pageOrder.getTotal();
        //数据list集合
        List<Order> records = pageOrder.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }

    @ApiOperation(value = "获取当前用户订单列表")
    @PostMapping("auth/list/{current}/{limit}")
    public R list(@PathVariable long current,@PathVariable long limit,HttpServletRequest request) {
        //创建page对象
        Page<PayLog> payLog = new Page<>(current,limit);
        // 获取用户的id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        QueryWrapper<PayLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create");
        queryWrapper.eq("member_id", memberId);

        //调用方法实现条件查询分页
        payLogService.page(payLog,queryWrapper);

        //总记录数
        long total = payLog.getTotal();
        //数据list集合
        List<PayLog> list = payLog.getRecords();
//        List<PayLog> records = payLog.getRecords(); //数据list集合
//        return R.ok().data("total",total).data("rows",records);

//        List<Order> list = orderService.selectByMemberId(memberId);
        return R.ok().data("items", list).data("total",total);
    }

    @ApiOperation(value = "删除订单")
    @DeleteMapping("auth/remove/{payLogId}")
    public R remove(@PathVariable String payLogId, HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        boolean result = payLogService.removeById(payLogId, memberId);
        if (result) {
            return R.ok().message("删除成功");
        } else {
            return R.error().message("数据不存在");
        }
    }


    /**
     * 支付成功的回调接口
     * @return
     */
    @ResponseBody
    @RequestMapping("/notifyPayResult")
    public String notifyPayResult(HttpServletRequest request) {
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<进入支付宝回调->>>>>>>>>>>>>>>>>>>>>>>>>");
        // 1.从支付宝回调的request域中取值放到map中
        Map<String, String[]> requestParams = request.getParameterMap();

        Map<String, String> params = new HashMap();
        for (String name : requestParams.keySet()) {
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        //2.封装必须参数
        // 商户订单号
        String outTradeNo = params.get("out_trade_no");
        //交易状态
        String tradeStatus = params.get("trade_status");

        System.out.println("outTradeNo:" + outTradeNo + " tradeStatus:" + tradeStatus);

        //3.签名验证(对支付宝返回的数据验证，确定是支付宝返回的)
        boolean signVerified = false;
        try {
            //3.1调用SDK验证签名
            signVerified = AlipaySignature.rsaCheckV1(params, alipayProperties.getPublicKey(), alipayProperties.getCharset(), alipayProperties.getSignType());

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("--------------->验签结果:" + signVerified);

        //4.对验签进行处理

        if (signVerified) {
            //验签通过
            //只处理支付成功的订单: 修改交易表状态,支付成功
            if ("TRADE_FINISHED".equals(tradeStatus) || "TRADE_SUCCESS".equals(tradeStatus)) {
                //根据订单号查找订单,防止多次回调的问题
                Order orderByOrder = orderService.getOrderByOrderNo(outTradeNo);
                if (orderByOrder != null && orderByOrder.getStatus() == OrderEnum.ORDER_STATUS_NOT_PAY.getStatus()) {
                    //修改订单状态
                    orderByOrder.setStatus(OrderEnum.ORDER_STATUS_PAID.getStatus());
                    orderByOrder.setGmtModified(new Date());
                    orderService.updateOrder(orderByOrder);
                }
                return "success";
            } else {
                return "failure";
            }
        } else {
            //验签不通过
            System.err.println("-------------------->验签失败");
            return "failure";
        }
    }

}

