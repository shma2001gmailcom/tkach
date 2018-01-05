package org.misha.loggers;

import org.misha.event.Event;
import org.misha.EventLogger;
import org.misha.event.EventType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

/**
 * author: misha
 * date: 1/4/18
 * time: 12:29 PM
 */
@Named("dbLogger")
public class DbLogger implements EventLogger {
    @SuppressWarnings({"unused", "field injection"})
    @Inject
    private DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;

    @Inject
    public DbLogger(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void logEvent(Event event) {
        jdbcTemplate.update("insert into EVENT(EVENT_ID, EVENT_TIME, MESSAGE) values (?, ?, ?)", event.getId(),
                            Calendar.getInstance(), event.getMsg());
    }

    @Override
    public boolean suitableFor(EventType type) {
        return true;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<String> getEvents() {
        return jdbcTemplate.query("select * from EVENT", new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString("EVENT_ID") + "\n"
                        + resultSet.getTimestamp("EVENT_TIME") + "\n"
                        + resultSet.getString("MESSAGE");
            }
        });
    }
}
