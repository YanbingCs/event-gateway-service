package com.proxzone.cloud.event.core.db.entry;

import com.google.gson.annotations.SerializedName;

/**
 * @author mayanbin@proxzone.com
 * @version 1.0
 * @date 19-7-3 下午5:06
 */
public class EventRequestEntity <T>{
    @SerializedName("service_id")
    private String serviceId;
    @SerializedName("params")
    private T content;
    @SerializedName("resourceType")
    private  String resourceType;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }
}
