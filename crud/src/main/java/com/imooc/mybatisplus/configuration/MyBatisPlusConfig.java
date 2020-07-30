package com.imooc.mybatisplus.configuration;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author hezihao
 * @version 1.0
 * <p>
 * @date 2020/7/30 5:06 下午
 * <p>
 */
@Configuration
public class MyBatisPlusConfig {
    /**
     * MyBatisPlus的分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}