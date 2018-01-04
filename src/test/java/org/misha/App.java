package org.misha;

import org.misha.aspect.StatisticAspect;
import org.misha.event.EventLogger;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * author: misha
 * date: 1/3/18
 * time: 2:27 AM
 */
@Named
public class App {
    final EventLogger mainLogger;
    final StatisticAspect statistic;
    final EventLogger dbLogger;

    @Inject
    App(final EventLogger mainLogger,
               @Named("statistic") final StatisticAspect statistic,
               @Named("dbLogger") final EventLogger dbLogger
    ) {
        this.mainLogger = mainLogger;
        this.statistic = statistic;
        this.dbLogger = dbLogger;
    }
}
