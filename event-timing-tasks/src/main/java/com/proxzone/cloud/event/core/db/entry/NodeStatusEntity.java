package com.proxzone.cloud.event.core.db.entry;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * @author mayanbin@proxzone.com
 * @version 1.0
 * @date 18-11-20 下午4:32
 */
public class NodeStatusEntity {
    @SerializedName("node_id")
    private String nodeId;
    @SerializedName("node_type")
    private String nodeType;
    @SerializedName("uptime")
    private Date upTime;
    @SerializedName("data_center")
    private String dataCenter;
    @SerializedName("address")
    private String address;
    @SerializedName("http_port")
    private String httpPort;
    private transient long taskCount;
    public synchronized long getTaskCount() {
        return taskCount;
    }

    public synchronized void setTaskCount(long taskCount) {
        this.taskCount = taskCount;
    }
    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

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

    public String getDataCenter() {
        return dataCenter;
    }

    public void setDataCenter(String dataCenter) {
        this.dataCenter = dataCenter;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHttpPort() {
        return httpPort;
    }

    public void setHttpPort(String httpPort) {
        this.httpPort = httpPort;
    }

    @Override
    public String toString() {
        return "NodeStatusEntity{" +
                "nodeId='" + nodeId + '\'' +
                ", nodeType='" + nodeType + '\'' +
                ", upTime=" + upTime +
                ", dataCenter='" + dataCenter + '\'' +
                ", address='" + address + '\'' +
                ", httpPort='" + httpPort + '\'' +
                ", taskCount=" + taskCount +
                '}';
    }
}
