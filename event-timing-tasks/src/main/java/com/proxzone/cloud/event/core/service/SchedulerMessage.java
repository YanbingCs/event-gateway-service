package com.proxzone.cloud.event.core.service;

import com.proxzone.cloud.event.core.db.entry.NatsGetSchedulerReplyEntity;

/**
 * @author mayanbin@proxzone.com
 * @version 1.0
 * @date 19-7-16 下午4:26
 */
public interface SchedulerMessage {
    /**
     * 通过Nats获取中央调度器处理结果
     */
    NatsGetSchedulerReplyEntity getSchedulerSessionReply(String params) throws Exception;
}
