package com.mi.mall1227.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mi.mall1227.common.PageBean;
import com.mi.mall1227.common.R;
import com.mi.mall1227.common.util.StringUtil;
import com.mi.mall1227.entity.Bigtype;
import com.mi.mall1227.entity.Smalltype;
import com.mi.mall1227.service.BigtypeService;
import com.mi.mall1227.service.SmalltypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 商品大类
 *
 * @author mi11
 */
@RestController
@RequestMapping("/admin/bigType")
@RequiredArgsConstructor
public class AdminBigTypeController {

    private final BigtypeService bigtypeService;

    private final SmalltypeService smalltypeService;

    @PostMapping("list")
    public R list(@RequestBody PageBean pageBean) {
        System.out.println(pageBean);
        String query = pageBean.getQuery().trim();
        Page<Bigtype> pageInfo = new Page<>(pageBean.getStart(), pageBean.getPageSize(), pageBean.getPageNum());
        Page<Bigtype> pageResult = bigtypeService.page(pageInfo,
                new QueryWrapper<Bigtype>().like(StringUtil.isNotEmpty(query),
                        "name", query));
        HashMap<String, Object> map = new HashMap<>();
        map.put("bigTypeList", pageResult.getRecords());
        map.put("total", pageResult.getTotal());
        return R.ok(map);
    }

    /**
     * 添加或者修改
     *
     * @param bigType
     * @return
     */
    @RequestMapping("/save")
    public R save(@RequestBody Bigtype bigType) {
        if (bigType.getId() == null || bigType.getId() == -1) {
            bigtypeService.save(bigType);
        } else {
            bigtypeService.saveOrUpdate(bigType);
        }
        return R.ok();
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R findById(@PathVariable(value = "id") Integer id) {
        Bigtype bigType = bigtypeService.getById(id);
        Map<String, Object> map = new HashMap<>();
        map.put("bigType", bigType);
        return R.ok(map);
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @GetMapping("/delete/{id}")
    public R delete(@PathVariable(value = "id") Integer id) {
        // 加个判断 大类下面如果有小类，返回报错提示
        if (smalltypeService.count(new QueryWrapper<Smalltype>().eq("bigTypeId", id)) > 0) {
            return R.error(500, "大类下面有小类信息，不能删除");
        } else {
            bigtypeService.removeById(id);
            return R.ok();
        }
    }
}
