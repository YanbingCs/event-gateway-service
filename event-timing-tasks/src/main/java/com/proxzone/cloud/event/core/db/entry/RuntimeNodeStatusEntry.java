package com.proxzone.cloud.event.core.db.entry;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * @author mayanbin@proxzone.com
 * @version 1.0
 * @date 19-7-3 下午4:26
 */
public class RuntimeNodeStatusEntry {
    @SerializedName("node_type")
    private String nodeType = "event-gateway-server";
    @SerializedName("uptime")
    private Date upTime;
    @SerializedName("address")
    private String address;
    @SerializedName("http_port")
    private int httpPort;

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public Date getUpTime() {
        return upTime;
    }

    public void setUpTime(Date upTime) {
        this.upTime = upTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getHttpPort() {
        return httpPort;
    }

    public void setHttpPort(int httpPort) {
        this.httpPort = httpPort;
    }
}
