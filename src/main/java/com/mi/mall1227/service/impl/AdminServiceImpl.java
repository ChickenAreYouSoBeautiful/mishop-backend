package com.mi.mall1227.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mi.mall1227.entity.Admin;
import com.mi.mall1227.mapper.AdminMapper;
import com.mi.mall1227.service.AdminService;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
}
