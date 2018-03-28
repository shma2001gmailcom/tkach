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
import java.util.concurrent.locks.ReentrantLock;

/**
 * author: misha
 * date: 1/3/18
 * time: 3:00 PM
 */
@Aspect
@Named("statistic")
public final class StatisticAspect {
    private final Map<Class<?>, AtomicInteger> counter;
    private final ReentrantLock lock;
    private final Logger log;
    
    @Inject
    public StatisticAspect(@Named final Logger log) {
        this.log = log;
        this.counter = new HashMap<>();
        lock = new ReentrantLock();
    }
    
    @Pointcut("execution(* *.logEvent(..))")
    private void allLogEvents() {
        //a pointcut
    }
    
    @AfterReturning("allLogEvents()")
    private void count(JoinPoint joinPoint) {
        final Class<?> c = joinPoint.getTarget().getClass();
        if (lock.tryLock()) {
            try {
                if (!counter.containsKey(c)) {
                    counter.put(c, new AtomicInteger(0));
                }
                counter.get(c).incrementAndGet();
            } finally {
                lock.unlock();
            }
        }
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
