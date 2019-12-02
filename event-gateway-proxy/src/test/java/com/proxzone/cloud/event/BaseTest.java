package com.proxzone.cloud.event;

import com.google.inject.Injector;
import com.proxzone.cloud.event.api.common.ApplicationInjector;
import com.proxzone.cloud.event.api.common.EnvironmentModule;
import com.proxzone.cloud.event.core.db.DatabaseModule;
import com.proxzone.cloud.event.core.service.ServicesModule;
import org.junit.Assert;
import org.junit.Before;

/**
 * @author mayanbin@proxzone.com
 * @version 1.0
 * @date 19-11-25 下午5:39
 */
public class BaseTest extends Assert {
    Injector injector;

    @Before
    public void init() throws Exception {
        String[] args = new String[]{
                "--mysql-addr", "127.0.0.1",
                "--etcd-addr", "http://192.168.139.245:2379",
                "--nats-addr", "nats://192.168.139.245:4222",
                "--bind-address","192.168.139.208"


        };
        injector = ApplicationInjector.initInjector(
                new EnvironmentModule(args),
                new DatabaseModule(),
                new ServicesModule()
        ).getGuiceInjector();
    }
}
