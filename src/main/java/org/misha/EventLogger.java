package org.misha;

import org.misha.event.Event;
import org.misha.event.EventType;

/**
 * author: misha
 * date: 1/3/18
 * time: 2:52 AM
 */
public interface EventLogger {

    void logEvent(Event event);

    boolean suitableFor(EventType type);
}
