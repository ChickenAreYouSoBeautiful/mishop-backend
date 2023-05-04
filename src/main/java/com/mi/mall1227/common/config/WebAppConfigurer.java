package com.mi.mall1227.common.config;

import com.mi.mall1227.common.interceptor.SysInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {

    @Bean
    public SysInterceptor sysInterceptor() {
        return new SysInterceptor();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .maxAge(3600);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] patterns = new String[]{"/adminLogin",
                "/product/**",
                "/bigType/**",
                "/user/wxlogin",
                "/weixinpay/**"};
        registry.addInterceptor(sysInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(patterns);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //首页轮播图
        registry.addResourceHandler("/image/swiper/**")
                .addResourceLocations("file:D:\\java\\ideaspringboot\\mall-1227\\pic\\swiperImgs\\");
        //商品大类
        registry.addResourceHandler("/image/bigType/**")
                .addResourceLocations("file:D:\\java\\ideaspringboot\\mall-1227\\pic\\bigTypeImgs\\");
        //热卖商品
        registry.addResourceHandler("/image/hot/**")
                .addResourceLocations("file:D:\\java\\ideaspringboot\\mall-1227\\pic\\productImgs\\");
        //商品详情轮播图
        registry.addResourceHandler("/image/productSwiperImgs/**")
                .addResourceLocations("file:D:\\java\\ideaspringboot\\mall-1227\\pic\\productSwiperImgs\\");
        //详情页 商品介绍
        registry.addResourceHandler("/image/productIntroImgs/**")
                .addResourceLocations("file:D:\\java\\ideaspringboot\\mall-1227\\pic\\productIntroImgs\\");
        //详情页 规格参数
        registry.addResourceHandler("/image/productParaImgs/**")
                .addResourceLocations("file:D:\\java\\ideaspringboot\\mall-1227\\pic\\productParaImgs\\");
    }

}
