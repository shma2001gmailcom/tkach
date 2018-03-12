package org.misha.rest;

/**
 * author: misha
 * date: 2/25/18
 * time: 12:24 PM
 */
public enum ViewType {
    FILE("FileEventLogger"),
    CACHED("CachedEventLogger"),
    DB("DbLogger");
    
    private final String type;
    
    ViewType(String type) {
        this.type = type;
    }
    
    public String getType() {
        return type;
    }
}
