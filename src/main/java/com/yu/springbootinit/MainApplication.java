package com.yu.springbootinit;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Spring Boot 应用程序的主类
 * 该类使用了 Spring Boot 注解来配置和启动应用程序。
 **/
// 声明该类是 Spring Boot 应用程序的主类，并排除了 Redis 的自动配置,要使用redis，删除括号及内容
@SpringBootApplication(exclude = {RedisAutoConfiguration.class})
// 扫描 Mapper 接口所在的包，用于自动配置 MyBatis 的 Mapper 接口实现
@MapperScan("com.yu.springbootinit.mapper")
// 启用 Spring 的定时任务功能
@EnableScheduling
// 启用 Spring 的 AspectJ 注解驱动，使用 CGLIB 代理方式，并暴露代理对象，用于在 AOP 中获取当前代理对象
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
public class MainApplication {
    /**
     * Spring Boot 应用程序的入口方法
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        // 启动 Spring Boot 应用程序
        SpringApplication.run(MainApplication.class, args);
    }

}