package org.misha.loggers;

import org.apache.commons.lang3.concurrent.ConcurrentException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.misha.client.Kind;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.Executors.newFixedThreadPool;
import static org.junit.Assert.assertNotNull;
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
    private final Map<Kind, Callable<String>> map = new HashMap<>();
    private final ExecutorService service = newFixedThreadPool(20);
    
    {
        for (final Kind kind : Kind.values()) {
            map.put(kind, new CallableView(kind));
        }
    }
    
    private static void assertRendered() {
        for (Kind kind : Kind.values()) {
            String view = kind.getView();
            if (view == null) {
                synchronized (errorCount) {
                    errorCount.incrementAndGet();
                }
            }
        }
        assertTrue(errorCount.get() == 0);
    }
    
    @Test
    public void testView() throws ConcurrentException, ExecutionException, InterruptedException {
        for (int i = 0; i < 10; ++i) {
            for (final Kind kind : Kind.values()) {
                assertNotNull(service.submit(map.get(kind)).get());
            }
        }
        service.shutdown();
        assertRendered();
    }
    
    @Test
    public void testViewAnotherWay() {
        for (int i = 0; i < 10; ++i) {
            new Thread(new Runnable() {
                
                @Override
                public void run() {
                    assertRendered();
                }
            }).start();
        }
    }
    
    private static class CallableView implements Callable<String> {
        private final Kind kind;
        
        private CallableView(final Kind kind) {
            this.kind = kind;
        }
        
        @Override
        public String call() throws Exception {
            try {
                return kind.getView();
            } catch (Exception e) {
                synchronized (errorCount) {
                    errorCount.incrementAndGet();
                }
            }
            return null;
        }
    }
}

