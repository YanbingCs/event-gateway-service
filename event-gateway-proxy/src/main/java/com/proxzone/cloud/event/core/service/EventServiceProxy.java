package com.proxzone.cloud.event.core.service;

import com.proxzone.cloud.event.core.db.entry.EventProxyReplyEntity;
import com.proxzone.cloud.event.core.db.entry.EventProxyRequestEntity;

public interface EventServiceProxy {
    /***
     * 处理代理请求
     * @param
     * @return
     */
    EventProxyReplyEntity handleRequest(String gatewayBody) throws Exception;
}
