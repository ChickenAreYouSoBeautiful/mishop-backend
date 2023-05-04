package com.mi.mall1227.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mi.mall1227.common.R;
import com.mi.mall1227.common.util.*;
import com.mi.mall1227.entity.Order;
import com.mi.mall1227.entity.OrderDetail;
import com.mi.mall1227.properties.WeixinpayProperties;
import com.mi.mall1227.service.OrderDetailService;
import com.mi.mall1227.service.OrderService;
import com.sun.org.apache.xpath.internal.operations.Or;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpResponse;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/my/order")
public class OrderController {

    private final OrderService orderService;

    private final OrderDetailService orderDetailService;

    private final WeixinpayProperties weixinpayProperties;

    @PostMapping("/create")
    public R createOrder(@RequestBody Order order, @RequestHeader(value = "token") String token) {
        System.out.println("token: " + token);
        //解析token
        Claims claims = JwtUtils.validateJWT(token).getClaims();
        //获取用户id
        if (claims != null) {
            System.out.println("openid :" + claims.getId());
            order.setUserId(claims.getId());
        }
        //生成订单信息
        order.setOrderNo("JAVA" + DateUtil.getCurrentDateStr());
        order.setCreateDate(new Date());
        OrderDetail[] orderDetails = order.getGoods();
        orderService.save(order);
        //生成订单详情信息
        for (int i = 0; i < orderDetails.length; i++) {
            OrderDetail orderDetail = orderDetails[i];
            orderDetail.setMId(order.getId());
            orderDetailService.save(orderDetail);
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("orderNo", order.getOrderNo());
        return R.ok(map);
    }

    @PostMapping("/preparePay")
    public R preparePay(@RequestBody String orderNo) throws Exception {
        System.out.println("订单编号" + orderNo);
        Order order = orderService.getOne(new QueryWrapper<Order>().eq("orderNo", orderNo));
         /* System.out.println("total_fee="+order.getTotalPrice().movePointRight(2));
        System.out.println("========");
        System.out.println("appid="+weixinpayProperties.getAppid());
        System.out.println("mch_id="+weixinpayProperties.getMch_id());
        System.out.println("openid="+order.getUserId());
        System.out.println("nonce_str="+StringUtil.getRandomString(32));
        System.out.println("body="+"mall商品购买测试");
        System.out.println("out_trade_no="+orderNo);
        System.out.println("spbill_create_ip="+"127.0.0.1");
        System.out.println("notify_url="+weixinpayProperties.getNotify_url());
        System.out.println("trade_type="+"JSAPI");
        System.out.println("sign=");*/

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("appid", weixinpayProperties.getAppid());
        map.put("mch_id", weixinpayProperties.getMch_id());
        map.put("openid", order.getUserId());
        map.put("nonce_str", StringUtil.getRandomString(32));
        map.put("body", "mall商品购买测试");
        map.put("total_fee", order.getTotalPrice().movePointRight(2));
        map.put("out_trade_no", orderNo);
        map.put("spbill_create_ip", "127.0.0.1");
        map.put("notify_url", weixinpayProperties.getNotify_url());
        map.put("trade_type", "JSAPI");
        map.put("sign", getSign(map));


        String xml = XmlUtil.genXml(map);
        System.out.println("xml=" + xml);

        HttpResponse httpResponse = HttpClientUtil.sendXMLDataByPost(weixinpayProperties.getUrl().toString(), xml);
        String httpEntityContent = HttpClientUtil.getHttpEntityContent(httpResponse);
        System.out.println("httpEntityContent=" + httpEntityContent);

        Map resultMap = XmlUtil.doXMLParse(httpEntityContent);
        System.out.println("resultMap=" + resultMap);

        return R.ok();
    }

    @GetMapping("/list")
    public R list(Integer type, Integer page, Integer pageSize) {
        System.out.println("type=" + type);

        List<Order> orders;
        Page<Order> orderPage = new Page<>(page, pageSize);

        HashMap<String, Object> map = new HashMap<>();
        if (type == 0) {
            Page<Order> orderResult = orderService.page(orderPage, new QueryWrapper<Order>().orderByDesc("id"));
            System.out.println("总记录数：" + orderResult.getTotal());
            System.out.println("总页数：" + orderResult.getPages());
            System.out.println("当前页数据：" + orderResult.getRecords());
            orders = orderResult.getRecords();
            map.put("total", orderResult.getTotal());
            map.put("totalPage", orderResult.getPages());
        } else {
            Page<Order> orderResult = orderService.page(orderPage, new QueryWrapper<Order>().eq("status", type)
                    .orderByDesc("id"));
            System.out.println("总记录数：" + orderResult.getTotal());
            System.out.println("总页数：" + orderResult.getPages());
            System.out.println("当前页数据：" + orderResult.getRecords());
            orders = orderResult.getRecords();
            map.put("total", orderResult.getTotal());
            map.put("totalPage", orderResult.getPages());
        }
        map.put("orderList", orders);
        return R.ok(map);
    }

    /**
     * 微信支付签名算法sign
     */
    private String getSign(Map<String, Object> map) {
        StringBuffer sb = new StringBuffer();
        String[] keyArr = (String[]) map.keySet().toArray(new String[map.keySet().size()]);//获取map中的key转为array
        Arrays.sort(keyArr);//对array排序
        for (int i = 0, size = keyArr.length; i < size; ++i) {
            if ("sign".equals(keyArr[i])) {
                continue;
            }
            sb.append(keyArr[i] + "=" + map.get(keyArr[i]) + "&");
        }
        sb.append("key=" + weixinpayProperties.getKey());
        String sign = string2MD5(sb.toString());
        return sign;
    }

    /***
     * MD5加码 生成32位md5码
     */
    private String string2MD5(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};

        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(str.getBytes(StandardCharsets.UTF_8));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] buf = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf).toUpperCase();
        } catch (Exception e) {
            return null;
        }
    }
}
