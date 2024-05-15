package com.yu.springbootinit.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 全局跨域配置
 *
 * 该类用于配置全局跨域访问策略，允许跨域请求的域名、方法、请求头等信息。
 */
@Configuration // 声明该类是一个配置类
public class CorsConfig implements WebMvcConfigurer {

    /**
     * 添加跨域映射规则
     *
     * @param registry CorsRegistry对象，用于配置跨域访问策略
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 覆盖所有请求路径
        registry.addMapping("/**")
                // 允许发送 Cookie
                .allowCredentials(true)
                // 允许跨域请求的域名，使用通配符 * 表示允许所有域名
                .allowedOriginPatterns("*")
                // 允许跨域请求的方法
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                // 允许跨域请求的请求头
                .allowedHeaders("*")
                // 允许在响应头中暴露的请求头，使用通配符 * 表示暴露所有请求头
                .exposedHeaders("*");
    }
}