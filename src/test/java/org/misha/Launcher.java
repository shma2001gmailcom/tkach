package org.misha;

import org.apache.log4j.Logger;
import org.misha.event.Event;
import org.misha.event.EventType;
import org.misha.loggers.DbLogger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Random;

/**
 * author: misha
 * date: 1/4/18
 * time: 4:22 PM
 */
public class Launcher {
    private static final String CONFIG_LOCATION = "test-application-context.xml";
    private static final ConfigurableApplicationContext context =
            new ClassPathXmlApplicationContext(CONFIG_LOCATION);

    public static void main(final String[] args) {
        final App app = getApp();
        for (int i = 0; i < 25; ++i) {
            final Random random = new Random();
            int j = random.nextInt();
            while (j < 0) j = random.nextInt();
            final Event event = fireEvent(j);
            logEvent(event);
            app.dbLogger.logEvent(event);
        }
        for(final String s : getDbLogger().getEvents()) getLog4j().debug(s);
        app.statistic.statistic();
        context.close();
    }

    private static App getApp() {
        return context.getBean(App.class);
    }

    private static Event fireEvent(final int randomTypeIndex) {
        final Event event = getEvent();
        final EventType[] values = EventType.values();
        event.setType(values[randomTypeIndex % values.length]);
        event.setMsg(getClient().toString());
        return event;
    }

    private static Client getClient() {
        return context.getBean(Client.class);
    }

    private static Event getEvent() {
        return context.getBean(Event.class);
    }

    private static void logEvent(final Event event) {
        getApp().mainLogger.logEvent(event);
    }

    private static Logger getLog4j() {
        return context.getBean(org.apache.log4j.Logger.class);
    }

    private static DbLogger getDbLogger() {
        return new DbLogger(context.getBean(JdbcTemplate.class));
    }

}
