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
        for (int i = 0; i < 1000; doTest(), ++i) ;
    }
    
    private void doTest() {
        int i = 0;
        while (i < Kind.values().length) {
            final int j = i;
            new Thread(new Runnable() {
                
                @Override
                public void run() {
                    if (Kind.values()[j].getView() == null) errorCount.incrementAndGet();
                }
            }).start();
            ++i;
        }
        assertTrue(errorCount.get() == 0);
    }
}
