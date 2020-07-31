package com.immoc.mybatisplus;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.imooc.mybatisplus.Application;
import com.imooc.mybatisplus.entity.User;
import com.imooc.mybatisplus.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * @author hezihao
 * @version 1.0
 * <p>
 * @date 2020/7/31 8:57 上午
 * <p>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ServiceTest {
    @Autowired
    private UserService userService;

    /**
     * 获取一个结果
     */
    @Test
    public void getOne() {
        //查询1个，如果有多个结果，会报错。
        //后面的throwEx属性代表如果查询出多个，是否抛出异常，默认为true，如果有多个就抛出异常
        //如果设置为false，则不抛出异常，打印警告，并且拿取第一个返回
        User result = userService.getOne(Wrappers.<User>lambdaQuery()
                .gt(User::getAge, 25), false);
        System.out.println(result);
    }

    /**
     * 批量插入
     */
    @Test
    public void batch() {
        User user1 = new User();
        user1.setReadName("徐丽1");
        user1.setAge(28);

        User user2 = new User();
        user2.setReadName("徐丽2");
        user2.setAge(29);
        List<User> userList = Arrays.asList(user1, user2);
        boolean isSuccess = userService.saveBatch(userList);
        System.out.println("是否批量插入成功：" + isSuccess);
    }

    /**
     * 批量操作，如果设置了id，则先查询，有则更新，无则做插入
     */
    @Test
    public void batch2() {
        User user1 = new User();
        user1.setReadName("徐丽3");
        user1.setAge(28);

        User user2 = new User();
        user2.setUserId(1289004540795330562L);
        user2.setReadName("徐力");
        user2.setAge(30);
        List<User> userList = Arrays.asList(user1, user2);
        boolean isSuccess = userService.saveOrUpdateBatch(userList);
        System.out.println("是否批量插入成功：" + isSuccess);
    }

    /**
     * 链式编程，查询
     */
    @Test
    public void chain() {
        List<User> userList = userService.lambdaQuery()
                .gt(User::getAge, 25)
                .like(User::getReadName, "雨")
                .list();
        for (User user : userList) {
            System.out.println(user);
        }
    }

    /**
     * 链式编程，更新
     */
    @Test
    public void chain2() {
        boolean isSuccess = userService.lambdaUpdate()
                .eq(User::getAge, 25)
                .set(User::getAge, 26)
                .update();
        System.out.println("是否更新成功：" + isSuccess);
    }

    /**
     * 链式编程，删除
     */
    @Test
    public void chain3() {
        boolean isSuccess = userService.lambdaUpdate()
                .eq(User::getAge, 24)
                .remove();
        System.out.println("是否删除成功：" + isSuccess);
    }
}