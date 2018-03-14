package org.misha.loggers;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.misha.client.LoggingClient.Kind;

/**
 * author: misha
 * date: 3/11/18
 * time: 2:48 PM
 */
@Ignore//run it after deploy
public class ViewTest {
    
    @Test
    public void testView() {
        for (Kind kind : Kind.values()) {
            assertNotNull(kind.getView());
        }
    }
}
