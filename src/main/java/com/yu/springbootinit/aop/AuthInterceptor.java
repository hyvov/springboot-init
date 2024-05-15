package com.yu.springbootinit.aop;

import com.yu.springbootinit.annotation.AuthCheck;
import com.yu.springbootinit.common.ErrorCode;
import com.yu.springbootinit.exception.BusinessException;
import com.yu.springbootinit.model.entity.User;
import com.yu.springbootinit.model.enums.UserRoleEnum;
import com.yu.springbootinit.service.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 权限校验 AOP 切面类（权限拦截器），用于校验用户权限
 * 该切面类用于对带有 @AuthCheck 注解的方法进行权限校验。
 **/
@Aspect // 声明该类为切面类
@Component // 声明该类为Spring组件
public class AuthInterceptor {

    @Resource // 注入UserService对象
    private UserService userService; // 用户服务，用于获取当前登录用户的信息

    /**
     * 执行拦截
     * 在方法执行前后进行权限校验
     *
     * @param joinPoint 切入点对象，包含了被拦截方法的信息
     * @param authCheck 方法上的 @AuthCheck 注解
     * @return 被拦截方法的执行结果
     * @throws Throwable 如果方法执行时抛出异常，则抛出
     */
    @Around("@annotation(authCheck)") // 环绕通知，拦截带有 @AuthCheck 注解的方法
    public Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        // 获取必须具备的角色
        String mustRole = authCheck.mustRole();

        // 获取当前请求的通用属性
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        // 将通用的 RequestAttributes 对象转换为 HttpServletRequest 对象，以便获取更具体的 HTTP 请求信息
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

        // 获取当前登录用户
        User loginUser = userService.getLoginUser(request);

        // 获取必须具备的角色枚举
        UserRoleEnum mustRoleEnum = UserRoleEnum.getEnumByValue(mustRole);

        // 如果不需要权限，直接放行
        if (mustRoleEnum == null) {
            return joinPoint.proceed();
        }

        // 获取当前用户的角色枚举
        UserRoleEnum userRoleEnum = UserRoleEnum.getEnumByValue(loginUser.getUserRole());

        // 如果用户角色为空，或者被封号，则抛出无权限异常
        if (userRoleEnum == null || UserRoleEnum.BAN.equals(userRoleEnum)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }

        // 如果需要管理员权限，但当前用户不是管理员，则抛出无权限异常
        if (UserRoleEnum.ADMIN.equals(mustRoleEnum) && !UserRoleEnum.ADMIN.equals(userRoleEnum)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }

        // 通过权限校验，放行
        return joinPoint.proceed();
    }
}