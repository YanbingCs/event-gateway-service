package com.proxzone.cloud.event.core.service;

import com.google.inject.AbstractModule;
import com.proxzone.cloud.event.core.db.DefaultNatsMessagesBus;
import com.proxzone.cloud.event.core.db.MessagesBus;
import com.proxzone.cloud.event.core.service.impl.DefaultEventServiceProxy;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/8/9
 */
public class ServicesModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(MessagesBus.class).to(DefaultNatsMessagesBus.class);
        bind(EventServiceProxy.class).to(DefaultEventServiceProxy.class);

    }
}
