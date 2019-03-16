package org.misha;

import org.misha.event.Event;
import org.misha.event.EventType;

import java.util.List;

/**
 * author: misha
 * date: 1/3/18
 * time: 2:52 AM
 */
public interface EventLogger {
    
    void logEvent(Event event);
    
    boolean suitableFor(EventType type);
    
    String getDetails() throws Throwable;
    
    List<String> getEvents();
}
