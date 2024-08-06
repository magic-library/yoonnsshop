package com.example.yoonnsshop.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
//@Component
public class LazyLoadingAspect {

    private static final Logger log = LoggerFactory.getLogger(LazyLoadingAspect.class);

    @Pointcut("execution(* org.hibernate.collection.spi.PersistentCollection+.forceInitialization())")
    public void lazyLoadingPointcut() {
    }

    @Around("lazyLoadingPointcut()")
    public Object logLazyLoading(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();

        String collectionName = joinPoint.getTarget().getClass().getSimpleName();
        log.debug("Lazy loading !! occurred for {}. Time taken: {} ms", collectionName, (endTime - startTime));

        return result;
    }
}
