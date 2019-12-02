package com.proxzone.cloud.event.core.service;


import com.proxzone.cloud.event.core.db.entry.EventGatewayReplyTaskEntity;
import com.proxzone.cloud.event.core.db.entry.EventRequestEntity;

/**
 * @author mayanbin@proxzone.com
 * @version 1.0
 * @date 19-7-8 上午11:29
 */
public  interface EventGatewayGetEventService {
    /**
     * 调用事件网关，返回任务执行结果
     * @param
     * @return
     * @throws Exception
     */
    EventGatewayReplyTaskEntity getEventResult(EventRequestEntity eventRequestEntity)throws Exception;

}
