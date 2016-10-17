package com.parkhomenko.rout.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author Dmytro Parkhomenko
 *         Created on 30.09.16.
 */

@Aspect
@Component
public class RestControllerLoggingAspect {

    @Pointcut("within(com.parkhomenko.rout.controller..*)")
    private void logAnyController() {
    }

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void restController() {
    }

    @Pointcut("execution(public * *(..))")
    protected void allMethod() {
    }

    @Before("logAnyController() && restController() && allMethod()")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("Entering in Method :  " + joinPoint.getSignature().getName());
        System.out.println("Class Name :  " + joinPoint.getSignature().getDeclaringTypeName());
    }
}
