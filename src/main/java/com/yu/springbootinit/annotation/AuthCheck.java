package com.yu.springbootinit.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限校验注解
 * 该注解用于标记需要进行权限校验的方法。
 */
@Target(ElementType.METHOD) // 自定义注解，表示该注解只能用于方法(METHOD)上
@Retention(RetentionPolicy.RUNTIME) // 表示注解在运行时保留，可以通过反射等机制获取到注解的信息。
public @interface AuthCheck {// 定义一个权限校验注解

    /**
     * 必须具备的角色
     * 用于指定被标记方法需要具备的角色。
     * @return 角色名
     */
    String mustRole() default "";
    //定义一个名为mustRole的属性，表示必须的角色信息，默认为空字符串

}

