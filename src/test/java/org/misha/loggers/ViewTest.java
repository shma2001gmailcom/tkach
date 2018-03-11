package org.misha.loggers;

import org.junit.Test;
import org.misha.client.LoggingClient;

import static org.junit.Assert.assertNotNull;

/**
 * author: misha
 * date: 3/11/18
 * time: 2:48 PM
 */
public class ViewTest {

    @Test
    public void testView() {
        for (LoggingClient.Kind kind : LoggingClient.Kind.values()) {
            assertNotNull(kind.getView());
        }
    }
}
