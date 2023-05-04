package com.mi.mall1227.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "weixin")
@Data
public class WeiXinProperties {

    private String jscode2sessionUrl;   //weixin开放接口

    private String appid;   //小程序id

    private String secret;  //密钥
}
