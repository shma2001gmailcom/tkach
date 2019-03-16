package org.misha;

import org.misha.rest.ViewType;

import java.io.Serializable;
import java.util.Map;

/**
 * author: misha
 * date: 1/5/18
 * time: 9:07 PM
 */
public interface LoggingService extends Serializable {
    
    void logEvents(Map<String, Object> map);
    
    String getDetails(ViewType viewType) throws Throwable;
}
