package org.misha.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * author: misha
 * date: 1/3/18
 * time: 2:22 PM
 */
@Aspect
@Named
public final class LogAspect {
    private final Logger log;
    
    @Inject
    private LogAspect(final Logger log) {
        this.log = log;
    }
    
    @Pointcut("execution(* *.logEvent(..))")
    private void allLogEvents() {
        //a pointcut
    }
    
    @Before("allLogEvents()")
    public void logBefore(final JoinPoint joinPoint) {
        log.info("BEFORE: " + joinPoint.getTarget().getClass().getSimpleName()
                + " " + joinPoint.getThis().getClass().getSimpleName()
                + " " + joinPoint.getSignature().getName());
    }
    
    @AfterReturning(pointcut = "allLogEvents()", returning = "retVal")
    public void logAfter(final Object retVal) {
        log.info("AFTER: retVal: " + retVal);
    }
}
