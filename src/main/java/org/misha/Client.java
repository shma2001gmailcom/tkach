package org.misha;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * author: misha
 * date: 1/3/18
 * time: 2:27 AM
 */
@Named
public class Client {
    private String greeting;
    private String id;
    private String fullName;

    @Inject
    @Named
    private Client(final String id, final String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    @SuppressWarnings("unused")
    String getId() {

        return id;
    }

    @SuppressWarnings("unused")
    public void setId(String id) {
        this.id = id;
    }

    @SuppressWarnings("unused")
    String getFullName() {
        return fullName;
    }

    @SuppressWarnings("unused")
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    public String getGreeting() {
        return greeting;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("greeting", greeting)
                .append("id", id)
                .append("fullName", fullName).toString();
    }
}
