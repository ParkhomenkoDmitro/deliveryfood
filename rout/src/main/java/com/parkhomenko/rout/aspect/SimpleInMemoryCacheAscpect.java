package com.parkhomenko.rout.aspect;

import com.parkhomenko.rout.util.CacheUtil;
import com.parkhomenko.service.impl.SimpleInMemoryCache;
import org.apache.commons.lang3.BooleanUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author Dmytro Parkhomenko
 *         Created on 30.09.16.
 */

@Aspect
@Component
public class SimpleInMemoryCacheAscpect {

    @Pointcut("within(com.parkhomenko.rout.controller..*)")
    private void logAnyController() {
    }

    @Pointcut("execution(public * getById(..))")
    protected void getMethod() {
    }

    @AfterReturning(pointcut = "logAnyController() && getMethod()", returning = "result")
    public void doAfter(JoinPoint joinPoint, Object result) {

        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String parameter = req.getParameter("apptx");

        if(Objects.isNull(parameter)) {
            return;
        }

        Boolean isApplicationTransaction = BooleanUtils.toBoolean(Integer.valueOf(parameter));

        if (Boolean.TRUE.equals(isApplicationTransaction)) {
            SimpleInMemoryCache.put(CacheUtil.getCacheKey(req), result);
        }
    }
}
