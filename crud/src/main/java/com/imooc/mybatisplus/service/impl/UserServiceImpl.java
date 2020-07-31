package com.imooc.mybatisplus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.mybatisplus.dao.UserMapper;
import com.imooc.mybatisplus.entity.User;
import com.imooc.mybatisplus.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author hezihao
 * @version 1.0
 * <p>
 * @date 2020/7/31 8:56 上午
 * <p>
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}