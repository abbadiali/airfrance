package com.airfrance.test.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {
private final Logger log = LoggerFactory.getLogger(this.getClass());


@Pointcut("within(@org.springframework.stereotype.Repository *)" +
                  " || within(@org.springframework.stereotype.Service *)" +
                  " || within(@org.springframework.web.bind.annotation.RestController *)")
public void springBeanPointcut() {
}


@Pointcut("within(com.airfrance.test..*)" +
                  " || within(com.airfrance.test.service..*)" +
                  " || within(com.airfrance.test.controller..*)")
public void applicationPackagePointcut() {
}

@AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut()", throwing = "e")
public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
    log.error("Exception in {}.{}() with cause = {}", joinPoint.getSignature().getDeclaringTypeName(),
            joinPoint.getSignature().getName(), e.getMessage() != null ? e.getMessage() : "NULL");
}

@Around("applicationPackagePointcut() && springBeanPointcut()")
public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
    if (log.isDebugEnabled()) {
        log.debug("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
    }
    try {
        Object result = joinPoint.proceed();
        if (log.isDebugEnabled()) {
            log.debug("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), result);
        }
        return result;
    } catch (IllegalArgumentException e) {
        log.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
                joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        throw e;
    }
}

}
