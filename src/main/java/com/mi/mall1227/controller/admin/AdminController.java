package com.mi.mall1227.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mi.mall1227.common.R;
import com.mi.mall1227.common.SystemConstant;
import com.mi.mall1227.common.util.JwtUtils;
import com.mi.mall1227.common.util.StringUtil;
import com.mi.mall1227.entity.Admin;
import com.mi.mall1227.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/adminLogin")
    public R adminLogin(@RequestBody Admin admin) {
        if (StringUtil.isEmpty(admin.getUsername())) {
            return R.error("用户名不能为空");
        }
        if (StringUtil.isEmpty(admin.getPassword())) {
            return R.ok("密码不能为空");
        }

        Admin user = adminService.getOne(new QueryWrapper<Admin>().eq("username", admin.getUsername()));

        if (user == null) {
            return R.error("用户名不存在");
        }
        if (!user.getPassword().trim().equals(admin.getPassword())) {
            return R.error("用户名或者密码错误！");
        }

        String token = JwtUtils.createJWT("-1", user.getUsername(), SystemConstant.JWT_TTL);
        HashMap<String, Object> map = new HashMap<>();
        map.put("token", token);
        return R.ok(map);
    }

}
