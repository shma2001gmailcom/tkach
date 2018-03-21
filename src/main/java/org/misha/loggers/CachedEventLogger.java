package org.misha.loggers;

import org.apache.log4j.Logger;
import org.misha.event.Event;
import org.misha.event.EventType;

import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import static org.misha.event.EventType.DEFAULT;
import static org.misha.event.EventType.INFO;

/**
 * author: misha
 * date: 1/3/18
 * time: 4:46 AM
 */
@Named
public final class CachedEventLogger extends FileEventLogger {
    private final int size;
    private final List<Event> cache = new ArrayList<>();
    private final ReentrantLock lock;
    
    @Inject
    @Named
    private CachedEventLogger(final String fileName, final int length, final Logger logger) {
        super(fileName, logger);
        size = length;
        lock = new ReentrantLock();
    }
    
    @Override
    public void logEvent(final Event event) {
        while (true) {
            if (lock.tryLock()) {
                break;
            }
        }
        try {
            if (cache.size() <= size) {
                cache.add(event);
                return;
            }
            reset();
        } finally {
            lock.unlock();
        }
        super.logEvent(event);
    }
    
    @Override
    public boolean suitableFor(final EventType type) {
        return type == INFO || type == DEFAULT;
    }
    
    @PreDestroy
    private void reset() {
        log.info("pre destroy");
        for (final Event e : cache) {
            super.logEvent(e);
        }
        cache.clear();
    }
}
