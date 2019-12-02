package com.proxzone.cloud.event.core.db.entity;

import com.iciql.Iciql;
import java.io.Serializable;

/**
 * @author mayanbin@proxzone.com
 * @version 1.0
 * @date 19-5-8 下午5:34
 */
@Iciql.IQTable(name = "job_entity")
public class JobEntity {
    @Iciql.IQColumn(primaryKey = true, name = "id")
    public int id;
    @Iciql.IQColumn(name = "name", length = 255)
    public String name;
    @Iciql.IQColumn(name = "job_group", length = 255)
    public String grop;
    @Iciql.IQColumn(name = "cron", length = 255)
    public String cron;
    @Iciql.IQColumn(name = "parameter", length = 255)
    public String parameter;
    @Iciql.IQColumn(name = "description", length = 255)
    public String description;
    @Iciql.IQColumn(name = "vm_param", length = 255)
    public String vParam;
    @Iciql.IQColumn(name = "service_id", length = 255)
    public String serviceId;
    @Iciql.IQColumn(name = "status", length = 255)
    public String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrop() {
        return grop;
    }

    public void setGrop(String grop) {
        this.grop = grop;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getvParam() {
        return vParam;
    }

    public void setvParam(String vParam) {
        this.vParam = vParam;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
