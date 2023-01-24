package com.project.instagram.handler.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
@Slf4j
public class LogAop {

    @Pointcut("@annotation(com.project.instagram.handler.aop.annotation.Log)")
    public void logEnable(){}

    @Around("logEnable()")
    public Object logging(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Map<String, Object> argsMap = getArgsMap(proceedingJoinPoint);
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Method Call: {} {}", proceedingJoinPoint.getSignature().getName(), argsMap);

        Object result = proceedingJoinPoint.proceed();

        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Method End: {} {}", proceedingJoinPoint.getSignature().getName(), argsMap);

        return result;
    }

    private Map<String, Object> getArgsMap(ProceedingJoinPoint proceedingJoinPoint) {
        int index = 0;
        Map<String, Object> argsMap = new HashMap<>();
        CodeSignature codeSignature = (CodeSignature) proceedingJoinPoint.getSignature();

        for(Object arg : proceedingJoinPoint.getArgs()) {
            argsMap.put(codeSignature.getParameterNames()[index], arg);
            index++;
        }

        return argsMap;
    }
}