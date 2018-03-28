package org.misha.loggers;

import org.misha.EventLogger;
import org.misha.event.Event;
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
import java.util.Objects;

/**
 * author: misha
 * date: 1/4/18
 * time: 12:29 PM
 */
@Named("dbLogger")
public final class DbLogger implements EventLogger {
    private static final String INSERT_EVENT = "insert into EVENT(EVENT_ID, EVENT_TIME, MESSAGE) values (?, ?, ?)";
    private static final String SELECT_EVENTS = "select * from EVENT";
    private final JdbcTemplate jdbcTemplate;
    @SuppressWarnings({"unused", "field injection"})
    @Inject
    private DataSource dataSource;
    
    @Inject
    private DbLogger(@Named final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    public void logEvent(final Event event) {
        jdbcTemplate.update(INSERT_EVENT, event.getId(),
                Calendar.getInstance(), event.getMsg()
        );
    }
    
    @Override
    public boolean suitableFor(final EventType type) {
        return true;
    }
    
    @Override
    public String getDetails() throws Exception {
        return Objects.toString(getEvents()).replaceAll("\\[", "")
                .replaceAll("],", "<br/>").replaceAll("]]", "");
    }
    
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    public List<String> getEvents() {
        return jdbcTemplate.query(SELECT_EVENTS, new RowMapper<String>() {
            
            @Override
            public String mapRow(final ResultSet resultSet, final int i) throws SQLException {
                return '\n' + resultSet.getString("EVENT_ID")
                        + '\n' + resultSet.getTimestamp("EVENT_TIME")
                        + '\n' + resultSet.getString("MESSAGE");
            }
        });
    }
}
