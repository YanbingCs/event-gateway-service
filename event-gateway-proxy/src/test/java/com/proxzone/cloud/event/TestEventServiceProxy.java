package com.proxzone.cloud.event;

import com.proxzone.cloud.event.core.db.entry.EventProxyReplyEntity;
import com.proxzone.cloud.event.core.service.EventServiceProxy;
import org.junit.Before;
import org.junit.Test;

/**
 * @author mayanbin@proxzone.com
 * @version 1.0
 * @date 19-11-25 下午5:37
 */
public class TestEventServiceProxy extends BaseTest {
    private EventServiceProxy eventServiceProxy;

    @Before
    public void befor() {
        eventServiceProxy = injector.getInstance(EventServiceProxy.class);
    }

    @Test
    public void testServiceProxy() throws Exception {
       EventProxyReplyEntity replyEntity =eventServiceProxy.handleRequest(  "operate" );
       System.out.println("-----------------------"+replyEntity.getResult());
    }
}
