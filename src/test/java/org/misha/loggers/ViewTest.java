package org.misha.loggers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertNotNull;
import static org.misha.client.LoggingClient.Kind;

/**
 * author: misha
 * date: 3/11/18
 * time: 2:48 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml")
@ContextConfiguration("file:src/main/webapp/WEB-INF/application-context.xml")

public class ViewTest {
    
    @Test
    public void testView() throws Exception {
        for (Kind kind : Kind.values()) assertNotNull(kind.getView());
    }
}
