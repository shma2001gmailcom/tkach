package org.misha.loggers;

import org.apache.log4j.Logger;
import org.misha.EventLogger;
import org.misha.event.Event;
import org.misha.event.EventType;

import javax.inject.Inject;
import javax.inject.Named;

import static org.misha.event.EventType.ERROR;

/**
 * author: misha
 * date: 1/3/18
 * time: 2:28 AM
 */
@SuppressWarnings("unused")
@Named
public final class ConsoleEventLogger implements EventLogger {
    private Logger log;
    
    @Inject
    private ConsoleEventLogger(final Logger log) {
        this.log = log;
    }
    
    public void logEvent(final Event event) {
        log.error(event.toString());
    }
    
    @Override
    public boolean suitableFor(final EventType type) {
        return type == ERROR;
    }
    
    @Override
    public String getDetails() throws Exception {
        return "console details is unavailable";
    }
}
