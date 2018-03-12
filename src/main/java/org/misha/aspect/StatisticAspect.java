package org.misha.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * author: misha
 * date: 1/3/18
 * time: 3:00 PM
 */
@Aspect
@Named("statistic")
public class StatisticAspect {
    private final Map<Class<?>, AtomicInteger> counter;
    private final Logger log;
    
    @Inject
    private StatisticAspect(final Logger log) {
        this.log = log;
        this.counter = new HashMap<>();
    }
    
    @Pointcut("execution(* *.logEvent(..))")
    private void allLogEvents() {
    }
    
    @AfterReturning("allLogEvents()")
    private void count(JoinPoint joinPoint) {
        final Class<?> c = joinPoint.getTarget().getClass();
        if (!counter.containsKey(c)) {
            counter.put(c, new AtomicInteger(0));
        }
        counter.get(c).incrementAndGet();
    }
    
    public void statistic(final Map<String, Object> map) {
        final Map<String, Integer> view = new HashMap<>();
        for (final Map.Entry<Class<?>, AtomicInteger> e : counter.entrySet()) {
            final Class<?> c = e.getKey();
            view.put(c.getSimpleName(), counter.get(c).intValue());
            log.debug(c.getSimpleName() + " = " + counter.get(c).intValue());
        }
        map.put("logs", view);
    }
}
