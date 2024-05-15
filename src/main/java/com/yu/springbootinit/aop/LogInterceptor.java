package com.yu.springbootinit.aop;

import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 请求响应日志 AOP 切面类
 * 该切面类用于拦截 Spring Boot 控制器（Controller）的方法执行，记录请求和响应日志。
 **/
@Aspect // 声明该类为切面类
@Component // 声明该类为Spring组件
@Slf4j // 使用Lombok的日志注解
public class LogInterceptor {

    /**
     * 执行拦截
     * @param point 切入点对象，包含了被拦截方法的信息
     * @return 被拦截方法的执行结果
     * @throws Throwable 异常信息
     */
    //定义拦截器的切入点，即拦截com.yu.springbootinit.controller包下的所有方法
    @Around("execution(* com.yu.springbootinit.controller.*.*(..))")
    public Object doInterceptor(ProceedingJoinPoint point) throws Throwable {
        // 计时器
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        // 获取当前请求的 HttpServletRequest 对象
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
        // 生成请求唯一 id
        String requestId = UUID.randomUUID().toString();
        // 获取请求路径
        String url = httpServletRequest.getRequestURI();
        // 获取请求参数
        Object[] args = point.getArgs();
        String reqParam = "[" + StringUtils.join(args, ", ") + "]";
        // 输出请求日志
        log.info("request start，id: {}, path: {}, ip: {}, params: {}", requestId, url,
                httpServletRequest.getRemoteHost(), reqParam);
        // 执行原方法
        Object result = point.proceed();

        stopWatch.stop();// 停止计时器
        long totalTimeMillis = stopWatch.getTotalTimeMillis(); // 获取执行时间
        log.info("request end, id: {}, cost: {}ms", requestId, totalTimeMillis);// 输出响应日志

        return result;// 返回被拦截方法的执行结果
    }
}
