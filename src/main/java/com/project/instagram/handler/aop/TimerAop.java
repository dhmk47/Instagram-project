package com.project.instagram.handler.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Timer;

@Aspect
@Component
@Slf4j
public class TimerAop {

    @Pointcut("@annotation(com.project.instagram.handler.aop.annotation.Timer)")
    public void enableTimer(){}

    @Around("enableTimer()")
    public Object loggingTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();

        stopWatch.start();

        Object result = proceedingJoinPoint.proceed();

        stopWatch.stop();

        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> {}({}) 메서드 실행 시간: {}",
                proceedingJoinPoint.getSignature().getDeclaringType(),
                proceedingJoinPoint.getSignature().getName(),
                stopWatch.getTotalTimeSeconds());
        return result;
    }
}