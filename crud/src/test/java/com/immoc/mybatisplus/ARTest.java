package com.immoc.mybatisplus;

import com.imooc.mybatisplus.Application;
import com.imooc.mybatisplus.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

/**
 * @author hezihao
 * @version 1.0
 * <p>
 * @date 2020/7/30 10:02 下午
 * <p>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ARTest {
    /**
     * 插入数据
     */
    @Test
    public void insert() {
        User user = new User();
        user.setReadName("刘花");
        user.setAge(29);
        user.setEmail("lh@baomidou.com");
        user.setManagerId(1088248166370832385L);
        user.setCreateTime(LocalDateTime.now());
        boolean isSuccess = user.insert();
        System.out.println("是否插入成功：" + isSuccess);
    }

    /**
     * 按主键进行查询
     */
    @Test
    public void selectById() {
        User user = new User();
        User result = user.selectById(1288460195017072641L);
        System.out.println(result);
    }

    /**
     * 按实体类上的主键属性进行查询
     */
    @Test
    public void selectById2() {
        User user = new User();
        user.setUserId(1288839790966910978L);
        User result = user.selectById();
        System.out.println(result);
    }

    /**
     * 更新，按实体类上的主键属性
     */
    @Test
    public void updateById() {
        User user = new User();
        user.setUserId(1288839790966910978L);
        user.setReadName("刘草");
        boolean isSuccess = user.updateById();
        System.out.println("是否更新成功：" + isSuccess);
    }

    /**
     * 按主键删除
     */
    @Test
    public void deleteById() {
        User user = new User();
        boolean isSuccess = user.deleteById(1288839790966910978L);
        System.out.println("是否删除成功：" + isSuccess);
    }

    /**
     * 按实体类上的主键属性进行删除
     */
    @Test
    public void deleteById2() {
        User user = new User();
        user.setUserId(1288839790966910978L);
        boolean isSuccess = user.deleteById();
        System.out.println("是否删除成功：" + isSuccess);
    }

    /**
     * 先查询主键Id的记录，如果有则更新，无则新增
     */
    @Test
    public void insertOrUpdate() {
        User user = new User();
        //设置了主键Id，则会先查询，有记录则更新，无则删除
        //user.setUserId(1288842439342780417L);
        user.setReadName("张强");
        user.setAge(29);
        user.setEmail("lh@baomidou.com");
        user.setManagerId(1088248166370832385L);
        user.setCreateTime(LocalDateTime.now());
        boolean isSuccess = user.insertOrUpdate();
        System.out.println("是否插入成功：" + isSuccess);
    }
}