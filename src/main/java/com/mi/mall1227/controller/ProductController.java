package com.mi.mall1227.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mi.mall1227.common.R;
import com.mi.mall1227.entity.Product;
import com.mi.mall1227.entity.ProductSwiperImage;
import com.mi.mall1227.service.ProductService;
import com.mi.mall1227.service.ProductSwiperImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * @author mi11
 */
@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    private final ProductSwiperImageService productSwiperImageService;

    @GetMapping("/findSwiper")
    public R findSwiper() {
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Product::getIsswiper, true)
                .orderByAsc(Product::getSwiperpic);
        List<Product> list = productService.list(wrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("message", list);
        return R.ok(map);
    }

    /**
     * 查询热卖商品  根据isHot查询    根据hotDateTime倒序
     *
     * @return 数据
     */
    @GetMapping("/findHot")
    public R findHot() {
        Page<Product> page = new Page<>(0, 8);
        Page<Product> productPage = productService.page(page, new QueryWrapper<Product>().eq("isHot", true).orderByDesc("hotDateTime"));
        List<Product> list = productPage.getRecords();
        HashMap<String, Object> map = new HashMap<>();
        map.put("message", list);
        return R.ok(map);
    }

    @GetMapping("detail")
    public R detail(Integer id) {
        //获取商品数据
        Product product = productService.getById(id);
        //获取商品轮播图数据
        List<ProductSwiperImage> productSwiperImages = productSwiperImageService.list(new QueryWrapper<ProductSwiperImage>()
                .eq("productId", product.getId()).orderByAsc("sort"));
        //封装进商品
        product.setProductSwiperImageList(productSwiperImages);
        HashMap<String, Object> map = new HashMap<>();
        map.put("message", product);
        return R.ok(map);
    }

    @GetMapping("/search")
    public R search(String q) {
        System.out.println(q);
        List<Product> productList = productService.list(new QueryWrapper<Product>().like("name", q));
        HashMap<String, Object> map = new HashMap<>();
        map.put("message", productList);
        return R.ok(map);
    }
}
