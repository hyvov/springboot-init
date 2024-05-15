package com.yu.springbootinit.utils;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Spring 上下文获取工具，用于获取 Spring 容器中的 Bean
 *
 */
@Component
public class SpringContextUtils implements ApplicationContextAware {

    /** Spring 应用上下文对象 */
    private static ApplicationContext applicationContext;

    /**
     * 设置应用上下文
     *
     * @param applicationContext Spring 应用上下文对象
     * @throws BeansException 当无法设置应用上下文时抛出异常
     */
    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        SpringContextUtils.applicationContext = applicationContext;
    }

    /**
     * 通过名称获取 Bean
     *
     * @param beanName Bean 的名称
     * @return 对应名称的 Bean 对象
     */
    public static Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }

    /**
     * 通过 class 获取 Bean
     *
     * @param beanClass Bean 的类对象
     * @param <T>       Bean 的类型
     * @return 对应类型的 Bean 对象
     */
    public static <T> T getBean(Class<T> beanClass) {
        return applicationContext.getBean(beanClass);
    }

    /**
     * 通过名称和类型获取 Bean
     *
     * @param beanName  Bean 的名称
     * @param beanClass Bean 的类对象
     * @param <T>       Bean 的类型
     * @return 对应名称和类型的 Bean 对象
     */
    public static <T> T getBean(String beanName, Class<T> beanClass) {
        return applicationContext.getBean(beanName, beanClass);
    }
}