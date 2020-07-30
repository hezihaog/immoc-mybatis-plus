package com.immoc.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.additional.update.impl.LambdaUpdateChainWrapper;
import com.imooc.mybatisplus.Application;
import com.imooc.mybatisplus.dao.UserMapper;
import com.imooc.mybatisplus.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author hezihao
 * @version 1.0
 * <p>
 * @date 2020/7/30 6:00 下午
 * <p>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UpdateTest {
    @Autowired
    private UserMapper userMapper;

    /**
     * 更新条件和更新信息放在实体，按主键Id更新
     */
    @Test
    public void updateById() {
        User user = new User();
        user.setUserId(1088248166370832385L);
        user.setAge(26);
        user.setEmail("wtf2@baomidou.com");
        int rows = userMapper.updateById(user);
        System.out.println("影响记录数：" + rows);
    }

    /**
     * 更新信息放在实体，更新条件使用条件构造器，进行更新
     */
    @Test
    public void updateByWrapper() {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("name", "李艺伟")
                .eq("age", 28);
        User user = new User();
        user.setEmail("lyw2019@baomidou.com");
        user.setAge(29);
        int rows = userMapper.update(user, updateWrapper);
        System.out.println("影响记录数：" + rows);
    }

    /**
     * UpdateWrapper创建时传入更新实体
     */
    @Test
    public void updateByWrapper2() {
        User whereUser = new User();
        whereUser.setReadName("李艺伟");
        whereUser.setEmail("lyw2019@baomidou.com");
        whereUser.setAge(29);
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>(whereUser);
        int rows = userMapper.update(whereUser, updateWrapper);
        System.out.println("影响记录数：" + rows);
    }

    /**
     * 通过UpdateWrapper进行条件设置，并且通过set方法设置新值
     */
    @Test
    public void updateByWrapper3() {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("name", "李艺伟")
                .eq("age", 29)
                .set("age", 30);
        int rows = userMapper.update(null, updateWrapper);
        System.out.println("影响记录数：" + rows);
    }

    /**
     * 通过UpdateWrapper进行条件设置，并且通过set方法设置新值
     */
    @Test
    public void updateByWrapperLambda() {
        LambdaUpdateWrapper<User> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.eq(User::getReadName, "李艺伟")
                .eq(User::getAge, 30)
                .set(User::getAge, 31);
        int rows = userMapper.update(null, updateWrapper);
        System.out.println("影响记录数：" + rows);
    }

    /**
     * 另外一种Lambda表达式方式
     */
    @Test
    public void updateByWrapperLambdaChain() {
        LambdaUpdateChainWrapper<User> updateWrapper = new LambdaUpdateChainWrapper<>(userMapper);
        boolean isUpdateSuccess = updateWrapper.eq(User::getReadName, "李艺伟")
                .eq(User::getAge, 31)
                .set(User::getAge, 32)
                .update();
        System.out.println("是否更新成功：" + isUpdateSuccess);
    }
}