package org.misha.event;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.time.FastDateFormat;

import javax.inject.Inject;
import javax.inject.Named;
import java.security.SecureRandom;
import java.util.Calendar;

/**
 * author: misha
 * date: 1/3/18
 * time: 3:22 AM
 */
@Named
public final class Event {
    private final int id;
    private final FastDateFormat df;
    private final Calendar date;
    private String msg;
    private EventType type;
    
    @Inject
    @Named
    private Event(final Calendar calendar, final FastDateFormat df) {
        this.date = calendar;
        this.df = df;
        this.id = new SecureRandom().nextInt();
    }
    
    @SuppressWarnings("unused")
    public int getId() {
        return id;
    }
    
    @SuppressWarnings("unused")
    public String getMsg() {
        return msg;
    }
    
    @SuppressWarnings("unused")
    public void setMsg(final String msg) {
        this.msg = msg;
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("msg", msg)
                .append("date", df.format(date)).toString();
    }
    
    public EventType getType() {
        return type;
    }
    
    public void setType(final EventType type) {
        this.type = type;
    }
}
