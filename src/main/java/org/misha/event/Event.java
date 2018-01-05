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
public class Event {
    private final int id;
    private final FastDateFormat df;
    final private Calendar date;
    private String msg;
    private EventType type;

    @Inject
    private Event(@Named final Calendar calendar, final FastDateFormat df) {
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
    public void setMsg(String msg) {
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

    public void setType(EventType type) {
        this.type = type;
    }
}
