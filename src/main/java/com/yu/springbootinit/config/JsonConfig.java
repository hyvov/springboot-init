package com.yu.springbootinit.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * Spring MVC JSON 配置类
 *
 * 该类用于配置 Spring MVC 的 JSON 转换器，以解决 Long 类型数据在转换为 JSON 时精度丢失的问题。
 */
@JsonComponent // 声明该类是一个 JSON 组件
public class JsonConfig {

    /**
     * 配置 ObjectMapper 对象
     *
     * @param builder Jackson2ObjectMapperBuilder 对象，用于构建 ObjectMapper
     * @return 返回一个配置好的 ObjectMapper 对象
     */
    @Bean // 声明该方法返回一个 Bean 对象，Spring 将会管理该对象的生命周期
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {

        // 创建一个 ObjectMapper 对象
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();

        // 创建一个 SimpleModule 对象，用于配置 JSON 序列化器
        SimpleModule module = new SimpleModule();

        // 将 Long 类型数据转换为字符串的序列化器注册到 ObjectMapper 中
        module.addSerializer(Long.class, ToStringSerializer.instance);// 注册将 Long 类型对象序列化为字符串的序列化器
        module.addSerializer(Long.TYPE, ToStringSerializer.instance);// 注册将 long 基本数据类型的值序列化为字符串的序列化器

        objectMapper.registerModule(module); // 将配置好的 SimpleModule 模块注册到 ObjectMapper 中
        return objectMapper; // 返回配置好的 ObjectMapper 对象
    }
}