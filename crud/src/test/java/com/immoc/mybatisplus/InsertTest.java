package com.immoc.mybatisplus;

import com.imooc.mybatisplus.Application;
import com.imooc.mybatisplus.dao.UserMapper;
import com.imooc.mybatisplus.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

/**
 * @author hezihao
 * @version 1.0
 * <p>
 * @date 2020/7/29 8:49 下午
 * <p>
 */
//指定可以在Spring环境下使用Junit测试
@RunWith(SpringRunner.class)
//标识该类是SpringBoot测试类，并指定启动类
@SpringBootTest(classes = Application.class)
public class InsertTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void insert() {
        //插入
        User user = new User();
        user.setReadName("刘明强");
        user.setAge(31);
        user.setManagerId(1088248166370832385L);
        user.setCreateTime(LocalDateTime.now());
        int rows = userMapper.insert(user);
        System.out.println("影响记录数：" + rows);
    }

    @Test
    public void insert2() {
        //插入
        User user = new User();
        user.setReadName("向中");
        user.setAge(25);
        user.setManagerId(1088248166370832385L);
        user.setEmail("xd@baomidou.com");
        user.setCreateTime(LocalDateTime.now());
        user.setRemark("我是一个备注信息");
        int rows = userMapper.insert(user);
        System.out.println("影响记录数：" + rows);
    }
}