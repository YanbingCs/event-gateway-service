package com.proxzone.cloud.event.core.service.impl;

import com.google.inject.Inject;
import com.proxzone.cloud.event.api.common.Json;
import com.proxzone.cloud.event.api.http.exception.BasicSparkRequestException;
import com.proxzone.cloud.event.core.db.MessagesBus;
import com.proxzone.cloud.event.core.db.entry.NatsGetSchedulerReplyEntity;
import com.proxzone.cloud.event.core.db.entry.NodeStatusEntity;
import com.proxzone.cloud.event.core.service.ManagerService;
import com.proxzone.cloud.event.core.service.SchedulerMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author mayanbin@proxzone.com
 * @version 1.0
 * @date 19-7-16 下午4:57
 */
public class DefaultSchedulerMessage implements SchedulerMessage {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultSchedulerMessage.class);
    private static final String KEY_NODE_TYPE = "scheduling-server";
    private static final String NATS_GET_SESSION_OP_TYPE = "scheduler-get-sessions";
    private ManagerService managerService;
    private MessagesBus messagesBus;
    private Json json;


    @Inject
    public DefaultSchedulerMessage(ManagerService managerService, MessagesBus messagesBus, Json json) {
        this.managerService = managerService;
        this.messagesBus=messagesBus;
        this.json=json;

    }

    @Override
    public NatsGetSchedulerReplyEntity getSchedulerSessionReply(String params) throws Exception {
        List<NodeStatusEntity> nodeStatusEntities = managerService.getNodeStatus(KEY_NODE_TYPE);
        if (nodeStatusEntities == null || nodeStatusEntities.size() == 0)
            throw new BasicSparkRequestException(404, "not found  [" + KEY_NODE_TYPE + "] acvite node");
        String subject = "" + nodeStatusEntities.get(0).getNodeId();
        LOGGER.info("subject------------------------------"+subject);
        NatsGetSchedulerReplyEntity replyEntity=messagesBus.request(""+subject,NATS_GET_SESSION_OP_TYPE,params,NatsGetSchedulerReplyEntity.class);
      //  LOGGER.info("reply------------------------------"+json.toJson(replyEntity));
        if (replyEntity == null) {
            LOGGER.error("message format error");
            return null;
        }
        return replyEntity;

    }
}
