package org.misha.loggers;

import org.misha.EventLogger;
import org.misha.event.Event;
import org.misha.event.EventType;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * author: misha
 * date: 1/3/18
 * time: 5:52 AM
 */
@SuppressWarnings("unused")
@Named("combinedEventLogger")
public final class CombinedEventLogger implements EventLogger {
    private final List<EventLogger> loggers;
    private final EventLogger defaultLogger;
    
    @Inject
    private CombinedEventLogger(final List<EventLogger> loggers,
                                @Named("cachedEventLogger") final EventLogger defaultLogger) {
        this.loggers = loggers;
        this.defaultLogger = defaultLogger;
    }
    
    @Override
    public void logEvent(final Event event) {
        for (final EventLogger logger : loggers) {
            if (logger.suitableFor(event.getType())) {
                logger.logEvent(event);
            } else {
                defaultLogger.logEvent(event);
            }
        }
    }
    
    @Override
    public boolean suitableFor(final EventType type) {
        return true;
    }
    
    @Override
    public String getDetails() throws Exception {
        return defaultLogger.getDetails();
    }
}
