package org.misha.client;

import org.springframework.web.client.RestTemplate;

/**
 * author: misha
 * date: 3/11/18
 * time: 1:09 PM
 */
public class LoggingClient {
    public enum Kind {
        LIST("list"), CACHED("cached"), COMBINED("combined"), CONSOLE("console"), FILE("file"), DB("db");
        
        private final String kind;
        
        Kind(String kind) {
            this.kind = kind;
        }
        
        private static String getLogs(String kind) {
            return new RestTemplate().getForObject("http://localhost:8080/tkach/rest/" + kind, String.class);
        }
        
        public String getView() {
            return getLogs(kind);
        }
    }
}
