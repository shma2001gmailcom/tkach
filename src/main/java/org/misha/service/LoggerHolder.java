package org.misha.service;

import org.apache.log4j.Logger;
import org.misha.Client;
import org.misha.EventLogger;
import org.misha.LoggingService;
import org.misha.aspect.StatisticAspect;
import org.misha.event.Event;
import org.misha.event.EventType;
import org.misha.loggers.DbLogger;
import org.misha.rest.ViewType;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;
import java.util.Random;

/**
 * author: misha
 * date: 1/3/18
 * time: 2:27 AM
 */
@Named
public class LoggerHolder implements LoggingService {
    private final EventLogger mainLogger;
    private final StatisticAspect statistic;
    private final EventLogger dbLogger;
    private final ApplicationContext context;
    
    @Inject
    @Named
    private LoggerHolder(
            final EventLogger mainLogger,
            final StatisticAspect statistic,
            final EventLogger dbLogger,
            final ApplicationContext context
    ) {
        this.mainLogger = mainLogger;
        this.statistic = statistic;
        this.dbLogger = dbLogger;
        this.context = context;
    }
    
    /**
     * add data for view
     *
     * @param map model-map
     */
    public void logEvents(final Map<String, Object> map) {
        for (int i = 0; i < 25; ++i) {
            final Random random = new Random();
            int j = random.nextInt();
            while (j < 0) {
                j = random.nextInt();
            }
            final Event event = fireEvent(j);
            mainLogger.logEvent(event);
            dbLogger.logEvent(event);
        }
        for (final String s : getDbLogger().getEvents()) {
            getLog4j().debug(s);
        }
        statistic.statistic(map);
    }
    
    @Override
    public String getDetails(ViewType viewType) throws Exception {
        switch (viewType) {
            case DB:
                return dbLogger.getDetails();
            default:
                return mainLogger.getDetails();
        }
    }
    
    private Event fireEvent(final int randomTypeIndex) {
        final Event event = getEvent();
        final EventType[] values = EventType.values();
        event.setType(values[randomTypeIndex % values.length]);
        event.setMsg(context.getBean(Client.class).toString());
        return event;
    }
    
    private Event getEvent() {
        return context.getBean(Event.class);
    }
    
    private Logger getLog4j() {
        return context.getBean(org.apache.log4j.Logger.class);
    }
    
    private DbLogger getDbLogger() {
        return new DbLogger(context.getBean(JdbcTemplate.class));
    }
}
