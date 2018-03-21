package org.misha.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Calendar;

/**
 * author: misha
 * date: 1/3/18
 * time: 2:22 PM
 */
@Aspect
@Named
public final class LogAspect {
    private final Logger log;
    private volatile long duration;
    
    @Inject
    private LogAspect(final Logger log) {
        this.log = log;
    }
    
    @Pointcut("execution(* *.logEvent(..))")
    private void allLogEvents() {
        //a pointcut
    }
    
    @Pointcut("execution(* org.misha.rest.RestController.fileDetails(..))")
    private void details() {
        //a pointcut
    }
    
    @Before("allLogEvents()")
    public void logBeforeEvent(final JoinPoint joinPoint) {
        duration = Calendar.getInstance().getTimeInMillis();
        log.info("BEFORE: " + joinPoint.getTarget().getClass().getSimpleName()
                + " " + joinPoint.getThis().getClass().getSimpleName()
                + " " + joinPoint.getSignature().getName());
    }
    
    @AfterReturning(pointcut = "allLogEvents()", returning = "retVal")
    public void logAfterEvent(final Object retVal) {
        log.info("AFTER: retVal=" + retVal);
        log.info(" duration was " + (Calendar.getInstance().getTimeInMillis() - duration));
        duration = 0;
    }
    
    @Before("details()")
    public void beforeDetails(final JoinPoint jp) {
        duration = Calendar.getInstance().getTimeInMillis();
        log.info("BEFORE: " + jp.getTarget().getClass().getSimpleName()
                + " " + jp.getThis().getClass().getSimpleName()
                + " " + jp.getSignature().getName());
    }
    
    @AfterReturning(pointcut = "details()", returning = "o")
    public void afterDetails(final Object o) {
        log.info("AFTER: o=" + o);
        log.info(" duration was " + (Calendar.getInstance().getTimeInMillis() - duration));
        duration = 0;
    }
}
