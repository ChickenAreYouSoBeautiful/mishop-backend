package com.mi.mall1227.controller;

import ch.qos.logback.classic.Logger;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mi.mall1227.common.R;
import com.mi.mall1227.common.util.MD5Util;
import com.mi.mall1227.common.util.XmlUtil;
import com.mi.mall1227.entity.Order;
import com.mi.mall1227.properties.WeixinpayProperties;
import com.mi.mall1227.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jdom.JDOMException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 有 三方支付  异步回调 这个接口  告诉 平台 用户的支付结果
 * <p>
 * 商家的平台 微信小程序 是如何知道用户支付结果的？
 * 方式1    第三方支付服务 异步回调 商家平台的接口   notify_url
 * 方式2    商家平台 发送延迟消息 查询 用户的支付结果
 *
 * @author mi11
 */
@Controller
@RequestMapping("/weixinpay")
@RequiredArgsConstructor
@Slf4j
public class WeiXinPayController {

    private final OrderService orderService;

    private final WeixinpayProperties weixinpayProperties;

    @PostMapping("notifyUrl")
    public synchronized void notifyUrl(HttpServletRequest request) throws IOException, JDOMException {
        log.info("notifyUrl");
        // 第一步 获取数据
        InputStream inputStream;
        StringBuffer sb = new StringBuffer();
        inputStream = request.getInputStream();
        String s;
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        while (((s = in.readLine()) != null)) {
            sb.append(s);
        }
        in.close();
        inputStream.close();
        log.info("sb:" + sb.toString());

        // 解析xml成map
        Map<String, String> m = new HashMap<>();
        m = XmlUtil.doXMLParse(sb.toString());

        // 过滤空 设置TreeMap
        SortedMap<Object, Object> packageParams = new TreeMap<>();
        Iterator<String> it = m.keySet().iterator();
        while (it.hasNext()) {
            String parameter = it.next();
            String parameterValue = m.get(parameter);

            String v = "";
            if (parameterValue != null) {
                v = parameterValue.trim();
            }

            packageParams.put(parameter, v);
        }

        log.info("packageParams:" + packageParams);

        // 微信支付的API秘钥
        String key = weixinpayProperties.getKey();
        String out_trade_no = (String) packageParams.get("out_trade_no");

        if (isTenpaySign("UTF-8", packageParams, key)) {
            log.info("验证签名通过");

            if ("SUCCESS".equals(packageParams.get("result_code"))) {
                Order order = orderService.getOne(new QueryWrapper<Order>().eq("orderNo", out_trade_no));
                if (order != null && order.getStatus() == 1) {
                    order.setPayDate(new Date());
                    order.setStatus(2); // 设置已支付状态
                    orderService.saveOrUpdate(order);
                    log.info(out_trade_no + "：微信服务器异步修改订单状态成功！");
                }
            }

        } else {
            log.info(out_trade_no + "：微信服务器异步验证失败！");
        }
    }

    /**
     * 是否签名正确,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
     *
     * @return boolean
     */
    public static boolean isTenpaySign(String characterEncoding, SortedMap<Object, Object> packageParams, String API_KEY) {
        StringBuffer sb = new StringBuffer();
        Set es = packageParams.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (!"sign".equals(k) && null != v && !"".equals(v)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + API_KEY);

        //算出摘要
        String mysign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toLowerCase();
        String tenpaySign = ((String) packageParams.get("sign")).toLowerCase();

        return tenpaySign.equals(mysign);
    }

}
