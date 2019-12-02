package com.proxzone.cloud.event.core.service.impl;

import com.google.inject.Inject;
import com.proxzone.cloud.event.api.common.Json;
import com.proxzone.cloud.event.api.http.exception.BasicSparkRequestException;
import com.proxzone.cloud.event.core.db.MessagesBus;
import com.proxzone.cloud.event.core.db.entry.EventGatewayReplyTaskEntity;
import com.proxzone.cloud.event.core.db.entry.EventRequestEntity;
import com.proxzone.cloud.event.core.db.entry.NatsGetSchedulerReplyEntity;
import com.proxzone.cloud.event.core.service.EventGatewayGetEventService;
import com.proxzone.cloud.event.core.service.ManagerService;
import com.proxzone.cloud.event.core.service.SchedulerMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DefaultEventGatewayGetEventService   {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultEventGatewayGetEventService.class);
    private  final Json json;
    private final SchedulerMessage schedulerMessage;

    @Inject
    public DefaultEventGatewayGetEventService(Json json,SchedulerMessage schedulerMessage) {
        this.json = json;
        this.schedulerMessage=schedulerMessage;

    }



    public EventGatewayReplyTaskEntity getEventResult(EventRequestEntity requestEntity) throws Exception {
        EventGatewayReplyTaskEntity replyTaskEntity = new EventGatewayReplyTaskEntity();
        //replyTaskEntity.setResult("true");
       // replyTaskEntity.setContent("234");

        NatsGetSchedulerReplyEntity replyEntity = schedulerMessage.getSchedulerSessionReply(json.toJson(requestEntity));
          LOGGER.info("reply------------------------------"+json.toJson(replyEntity));
          replyTaskEntity.setResult(replyEntity.getResult());
          replyTaskEntity.setContent(replyEntity.getContent());

        return replyTaskEntity;

    }
}