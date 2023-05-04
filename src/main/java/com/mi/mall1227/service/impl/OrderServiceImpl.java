package com.mi.mall1227.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mi.mall1227.entity.Order;
import com.mi.mall1227.service.OrderService;
import com.mi.mall1227.mapper.OrderMapper;
import org.springframework.stereotype.Service;

/**
 * @author mi11
 * @description 针对表【t_order】的数据库操作Service实现
 * @createDate 2022-12-28 20:09:54
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
        implements OrderService {

}




