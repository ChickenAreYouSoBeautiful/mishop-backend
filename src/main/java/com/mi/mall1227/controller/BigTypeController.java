package com.mi.mall1227.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mi.mall1227.common.R;
import com.mi.mall1227.entity.Bigtype;
import com.mi.mall1227.entity.Product;
import com.mi.mall1227.entity.Smalltype;
import com.mi.mall1227.service.BigtypeService;
import com.mi.mall1227.service.ProductService;
import com.mi.mall1227.service.SmalltypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/bigType")
@RequiredArgsConstructor
public class BigTypeController {

    private final BigtypeService bigtypeService;

    private final SmalltypeService smalltypeService;

    private final ProductService productService;

    @GetMapping("/findAll")
    public R findAll() {
        List<Bigtype> list = bigtypeService.list();
        HashMap<String, Object> map = new HashMap<>();
        map.put("message", list);
        return R.ok(map);
    }

    @GetMapping("/findCategories")
    public R findCategories() {
        //查询所有大的分类
        List<Bigtype> bigtypeList = bigtypeService.list();
        //查询大分类下的小分类
        for (Bigtype bigtype : bigtypeList) {
            List<Smalltype> smalltypeList = smalltypeService.list(new QueryWrapper<Smalltype>()
                    .eq("bigTypeId", bigtype.getId()));
            bigtype.setSmalltypeList(smalltypeList);
            for (Smalltype smalltype : smalltypeList) {
                List<Product> productList = productService.list(new QueryWrapper<Product>()
                        .eq("typeId", smalltype.getId()));
                smalltype.setProductList(productList);
            }
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("message", bigtypeList);
        return R.ok(map);
    }

}
