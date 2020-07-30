package com.immoc.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.imooc.mybatisplus.Application;
import com.imooc.mybatisplus.dao.UserMapper;
import com.imooc.mybatisplus.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;

/**
 * @author hezihao
 * @version 1.0
 * <p>
 * @date 2020/7/30 9:49 下午
 * <p>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class DeleteTest {
    @Autowired
    private UserMapper userMapper;

    /**
     * 根据主键Id删除
     */
    @Test
    public void deleteById() {
        int rows = userMapper.deleteById(1288677289033801729L);
        System.out.println("影响记录数：" + rows);
    }

    /**
     * 提供一个Map，Map中记录删除条件，进行删除
     */
    @Test
    public void deleteByMap() {
        HashMap<String, Object> columnMap = new HashMap<>();
        columnMap.put("id", 1288676754557825026L);
        columnMap.put("age", 25);
        int rows = userMapper.deleteByMap(columnMap);
        System.out.println("影响记录数：" + rows);
    }

    /**
     * 传入多个id，进行批量删除
     */
    @Test
    public void deleteIds() {
        int rows = userMapper.deleteBatchIds(Arrays.asList(1288461659789660161L,
                1288671248736870402L, 1288676285735272450L));
        System.out.println("影响记录数：" + rows);
    }

    /**
     * 使用条件构造器，进行删除
     */
    @Test
    public void deleteByWrapper() {
        LambdaQueryWrapper<User> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(User::getAge, 27)
                .or()
                .gt(User::getAge, 41);
        int rows = userMapper.delete(lambdaQuery);
        System.out.println("影响记录数：" + rows);
    }
}