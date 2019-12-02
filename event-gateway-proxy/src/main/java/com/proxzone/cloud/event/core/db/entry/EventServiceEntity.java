package com.proxzone.cloud.event.core.db.entry;
import com.iciql.Iciql;

@Iciql.IQTable(name = "event_service", create = true)
public class EventServiceEntity {
    //主键
    @Iciql.IQColumn(name = "service_id", primaryKey = true)
    private String serviceId;
    @Iciql.IQColumn(name = "service_url", length = 100)
    private String serviceUrl;
    @Iciql.IQColumn(name = "vm_param", length = 100)
    private String vmParam;
    @Iciql.IQColumn(name = "parameter", length = 100)
    private String parameter;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public String getVmParam() {
        return vmParam;
    }

    public void setVmParam(String vmParam) {
        this.vmParam = vmParam;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }
}
