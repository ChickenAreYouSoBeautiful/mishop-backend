package com.mi.mall1227.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mi.mall1227.common.PageBean;
import com.mi.mall1227.common.R;
import com.mi.mall1227.common.util.StringUtil;
import com.mi.mall1227.entity.Wxuserinfo;
import com.mi.mall1227.service.WxuserinfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * 用户管理
 *
 * @author mi11
 */
@RestController
@RequestMapping("/admin/user")
@RequiredArgsConstructor
public class AdminUserController {

    private final WxuserinfoService wxuserinfoService;

    @PostMapping("/list")
    public R list(@RequestBody PageBean pageBean) {
        System.out.println(pageBean);
        String query = pageBean.getQuery().trim();
        Page<Wxuserinfo> pageInfo = new Page<>(pageBean.getStart(), pageBean.getPageSize(), pageBean.getPageNum());
        Page<Wxuserinfo> user = wxuserinfoService.page(pageInfo, new QueryWrapper<Wxuserinfo>().like(StringUtil.isNotEmpty(query), "nickName", query));
        HashMap<String, Object> map = new HashMap<>();
        map.put("userList", user.getRecords());
        map.put("total", user.getTotal());
        return R.ok(map);
    }

}
