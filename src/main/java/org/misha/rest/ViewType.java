package org.misha.rest;

/**
 * author: misha
 * date: 2/25/18
 * time: 12:24 PM
 */
public enum ViewType {
    LIST("list", "list"),
    FILE("FileEventLogger", "file"),
    CACHED("CachedEventLogger", "cached"),
    DB("DbLogger", "data-base");

    private final String type;
    private final String view;

    ViewType(String type, String view) {
        this.type = type;
        this.view = view;
    }

    public String getType() {
        return type;
    }

    public String getView() {
        return view;
    }

    public static ViewType getByType(String type) {
        for (ViewType vt : values()) {
            if (vt.type.equals(type)) {
                return vt;
            }
        }
        throw new IllegalStateException("no such type");
    }

    public static ViewType getByView(String view) {
        for (ViewType vt : values()) {
            if (vt.type.equals(view)) {
                return vt;
            }
        }
        throw new IllegalStateException("no such type");
    }
}
