package com.proxzone.cloud.event.core.service;

import com.google.inject.AbstractModule;
import com.proxzone.cloud.event.api.common.DefaultGsonJson;
import com.proxzone.cloud.event.api.common.Json;
import com.proxzone.cloud.event.core.db.*;
import com.proxzone.cloud.event.core.service.impl.DefaultDynamicJobService;
import com.proxzone.cloud.event.core.service.impl.DefaultEventGatewayGetEventService;
import com.proxzone.cloud.event.core.service.impl.DefaultManagerService;
import com.proxzone.cloud.event.core.service.impl.DefaultSchedulerMessage;
import org.quartz.Job;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/8/9
 */
public class ServicesModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(MessagesBus.class).to(DefaultNatsMessagesBus.class);
        bind(ManagerService.class).to(DefaultManagerService.class);
        bind(DynamicJobService.class).to(DefaultDynamicJobService.class);
       // bind(EventGatewayGetEventService.class).to(DefaultEventGatewayGetEventService.class);
        bind(SchedulerMessage.class).to(DefaultSchedulerMessage.class);
        bind(Json.class).to(DefaultGsonJson.class);
        bind(RelationalDatabase.class).to(DefaultIciqlMySQLDatabase.class);
        bind(MetaDataBase.class).to(DefaultEtcdMetaDataBase.class);



    }
}
