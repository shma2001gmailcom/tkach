package org.misha.loggers;

import org.apache.log4j.Logger;
import org.misha.event.Event;
import org.misha.event.EventType;

import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

import static org.misha.event.EventType.DEFAULT;
import static org.misha.event.EventType.INFO;

/**
 * author: misha
 * date: 1/3/18
 * time: 4:46 AM
 */
@Named
public class CachedEventLogger extends FileEventLogger {
    private final Logger log;
    private final int size;
    private final List<Event> cache = new ArrayList<>();
    
    @Inject
    @Named
    private CachedEventLogger(final String fileName, final int size, final Logger log) {
        super(fileName, log);
        this.size = size;
        this.log = log;
    }
    
    @Override
    public void logEvent(final Event event) {
        if (cache.size() <= size) {
            cache.add(event);
            return;
        }
        reset();
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