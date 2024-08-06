package com.example.yoonnsshop.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
//@Component
public class TestAspect {

    @Around("execution(* com.example.yoonnsshop..*.*(..))")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("AOP is working: " + joinPoint.getSignature().toShortString());
        return joinPoint.proceed();
    }
}
