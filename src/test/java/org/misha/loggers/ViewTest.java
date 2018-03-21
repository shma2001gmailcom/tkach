package org.misha.loggers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.misha.client.Kind;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertTrue;

/**
 * author: misha
 * date: 3/11/18
 * time: 2:48 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml")
@ContextConfiguration("file:src/main/webapp/WEB-INF/application-context.xml")
public class ViewTest {
    private static final AtomicInteger errorCount = new AtomicInteger(0);
    
    @Test
    public void testView() {
        for (int i = 0; i < 3; ++i) {
            assertRendered();
        }
    }
    
    private void assertRendered() {
        for (Kind kind : Kind.values()) {
            final String view = kind.getView();
            if (view == null) errorCount.incrementAndGet();
        }
        assertTrue(errorCount.get() == 0);
    }
}
