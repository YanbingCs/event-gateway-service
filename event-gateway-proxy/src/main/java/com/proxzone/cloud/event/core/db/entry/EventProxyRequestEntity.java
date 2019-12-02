package com.proxzone.cloud.event.core.db.entry;

import com.google.gson.annotations.SerializedName;

/**
 * @author mayanbin@proxzone.com
 * @version 1.0
 * @date 19-7-3 下午5:06
 */
public class EventProxyRequestEntity<T> {
    @SerializedName("service_id")
    private String serviceId;
    @SerializedName("content")
    private T params;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public T getParams() {
        return params;
    }

    public void setParams(T params) {
        this.params = params;
    }
}
