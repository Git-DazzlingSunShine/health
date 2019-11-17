package com.yanglei.aop;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yanglei.annotation.SystemWebLog;
import com.yanglei.pojo.OperationLog;
import com.yanglei.service.SystemWebLogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 系统日志
 */
@Aspect
@Component
public class SystemWebLogAspect {
    @Reference
    SystemWebLogService systemWebLogService;

    /**
     * 切点
     */
    @Pointcut("@annotation(com.yanglei.annotation.SystemWebLog)")
    public void webLogPoint() {
    }

    @Around("webLogPoint()")
    public Object aroundService(ProceedingJoinPoint pjo) {
        //从session中获取用户
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //方法签名对象
        MethodSignature methodSignature = (MethodSignature) pjo.getSignature();
        Method targetMethod = methodSignature.getMethod();

        //获取注解
        SystemWebLog annotation = targetMethod.getAnnotation(SystemWebLog.class);
        String orderType = annotation.description();
        OperationLog operationLog = new OperationLog();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        operationLog.setOperationDate(simpleDateFormat.format(new Date()));

        try {
            operationLog.setOperationIp (InetAddress.getLocalHost().toString().substring(InetAddress.getLocalHost().toString().lastIndexOf("/")+1));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        operationLog.setOperationName(user.getUsername());
        try {
            Object proceed = pjo.proceed();//继续执行目标方法
            operationLog.setOrderType(orderType);
            return proceed;
        } catch (Throwable throwable) {
            operationLog.setOrderType("error");
        }finally {
            systemWebLogService.logging(operationLog);//插入日志
            System.out.println(user.getUsername()+targetMethod);
        }
        return null;
    }

}
