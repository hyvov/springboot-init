package com.yu.springbootinit.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis Plus 配置类
 *
 * 该类用于配置 MyBatis Plus 框架的相关功能，包括分页插件等。
 * 可以通过该类配置拦截器等功能。
 *
 */
@Configuration // 声明该类是一个配置类
@MapperScan("com.yu.springbootinit.mapper") // 扫描 Mapper 接口所在的包，用于自动配置 MyBatis 的 Mapper 接口实现
public class MyBatisPlusConfig {

    /**
     * 配置 MyBatis Plus 拦截器
     *
     * @return 返回一个 MybatisPlusInterceptor 对象，用于配置 MyBatis Plus 的拦截器
     */
    @Bean // 声明该方法返回一个 Bean 对象，Spring 将会管理该对象的生命周期
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor(); // 创建一个 MybatisPlusInterceptor 对象
        // 添加分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor; // 返回配置好的拦截器对象
    }
}