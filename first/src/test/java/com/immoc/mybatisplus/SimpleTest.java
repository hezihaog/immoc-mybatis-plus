package com.immoc.mybatisplus;

import com.imooc.mybatisplus.Application;
import com.imooc.mybatisplus.dao.UserMapper;
import com.imooc.mybatisplus.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

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
public class SimpleTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void select() {
        //查询，查询条件传null，代表查询所有
        List<User> list = userMapper.selectList(null);
        Assert.assertEquals(5, list.size());
        for (User user : list) {
            System.out.println(user);
        }
    }
}