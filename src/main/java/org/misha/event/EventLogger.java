package org.misha.event;

/**
 * author: misha
 * date: 1/3/18
 * time: 2:52 AM
 */
public interface EventLogger {

    void logEvent(Event event);

    boolean suitableFor(EventType type);
}
