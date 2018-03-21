package org.misha.client;

import org.springframework.web.client.RestTemplate;

/**
 * author: misha
 * date: 3/11/18
 * time: 1:09 PM
 */
public enum Kind {
    LIST("list"), CACHED("cached"), COMBINED("combined"), CONSOLE("console"), FILE("file"), DB("db");
    
    //string-value for kind
    private final String name;
    private static final RestTemplate restTemplate = new RestTemplate();
    
    Kind(String kind) {
        name = kind;
    }
    
    private static String getLogs(final String kind) {
        return restTemplate.getForObject("http://localhost:8080/tkach/rest/" + kind, String.class);
    }
    
    public String getView() {
        return getLogs(name);
    }
}

