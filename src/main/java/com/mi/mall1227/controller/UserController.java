package com.mi.mall1227.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mi.mall1227.common.R;
import com.mi.mall1227.common.SystemConstant;
import com.mi.mall1227.common.util.HttpClientUtil;
import com.mi.mall1227.common.util.JwtUtils;
import com.mi.mall1227.entity.Wxuserinfo;
import com.mi.mall1227.properties.WeiXinProperties;
import com.mi.mall1227.service.WxuserinfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final WeiXinProperties weiXinProperties;

    private final WxuserinfoService wxuserinfoService;

    private final HttpClientUtil httpClientUtil;

    @PostMapping("/wxlogin")
    public R wxLogin(@RequestBody Wxuserinfo wxuserinfo) {
        System.out.println(wxuserinfo.getCode());
        String jscode2sessionUrl = weiXinProperties.getJscode2sessionUrl() +
                "?appid=" + weiXinProperties.getAppid() +
                "&secret=" + weiXinProperties.getSecret() +
                "&js_code=" + wxuserinfo.getCode();
        System.out.println(jscode2sessionUrl);
        String result = httpClientUtil.sendHttpGet(jscode2sessionUrl);
        System.out.println(result);
        JSONObject jsonObject = JSON.parseObject(result);
        String openid = jsonObject.getString("openid").toString();
        System.out.println(openid);
        Wxuserinfo userInfo = wxuserinfoService.getOne(new QueryWrapper<Wxuserinfo>().eq("openid", openid));
        if (userInfo == null) {
            System.out.println("不存在，插入");
            wxuserinfo.setRegisterdate(new Date());
            wxuserinfo.setLastlogindate(new Date());
            wxuserinfo.setOpenid(openid);
            wxuserinfoService.save(wxuserinfo);
        } else {
            System.out.println("存在，更新");
            wxuserinfo.setAvatarurl(wxuserinfo.getAvatarurl());
            wxuserinfo.setNickname(wxuserinfo.getNickname());
            wxuserinfo.setLastlogindate(new Date());
            wxuserinfoService.updateById(wxuserinfo);
        }

        String token = JwtUtils.createJWT(openid, wxuserinfo.getNickname(), SystemConstant.JWT_TTL);
        HashMap<String, Object> map = new HashMap<>();
        map.put("token", token);
        return R.ok(map);
    }
}
