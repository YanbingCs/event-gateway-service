package com.proxzone.cloud.event.core.service;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.proxzone.cloud.event.api.common.ApplicationInjector;
import com.proxzone.cloud.event.api.common.EnvironmentModule;
import com.proxzone.cloud.event.api.http.HTTPAPIServerModule;
import com.proxzone.cloud.event.core.db.DatabaseModule;
import com.proxzone.cloud.event.core.db.entry.EventGatewayReplyTaskEntity;
import com.proxzone.cloud.event.core.db.entry.EventRequestEntity;
import com.proxzone.cloud.event.core.service.impl.DefaultEventGatewayGetEventService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.utils.StringUtils;


/**
 * @author mayanbin@proxzone.com
 * @version 1.0
 * @date 19-6-11 上午11:51
 */

public class DynamicJob implements Job {
    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicJob.class);
    private EventGatewayGetEventService eventGatewayGetEventService;



    @Override
    public void execute(JobExecutionContext jobExecutionContext ) throws JobExecutionException  {
        JobDataMap map = jobExecutionContext.getMergedJobDataMap();
        String serviceId = map.getString("serviceId");
        String vmParam=map.getString("vmParam");
        LOGGER.info("Running Job name : {} ", map.getString("name"));
        LOGGER.info("Running Job description : {}", map.getString("jobDescription"));
        LOGGER.info("Running Job group: {} ", map.getString("jobGroup"));
        LOGGER.info(String.format("Running Job cron : %s", map.getString("cronExpression")));
        LOGGER.info("Running Job service Id : {} ", map.getString("serviceId"));
        LOGGER.info("Running Job parameter : {} ", map.getString("parameter"));
        LOGGER.info("Running Job vmParam : {} ", map.getString("vmParam"));
        long startTime = System.currentTimeMillis();
        EventRequestEntity requestEntity=new EventRequestEntity();
        Injector injector = Guice.createInjector(new ServicesModule());
        DefaultEventGatewayGetEventService eventGatewayGetEventService = injector.getInstance(DefaultEventGatewayGetEventService.class);
        if (!StringUtils.isEmpty(serviceId)) {
            try {
                requestEntity.setResourceType("event");
                requestEntity.setServiceId(serviceId);
                requestEntity.setContent(vmParam);
                EventGatewayReplyTaskEntity eventGatewayReplyTaskEntity=eventGatewayGetEventService.getEventResult(requestEntity);
                if(eventGatewayReplyTaskEntity.getResult().equals("true"))
                    LOGGER.info("Running Job Service Success ");
                else   LOGGER.info("Running Job Service Fail ");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        long endTime = System.currentTimeMillis();
        LOGGER.info(">>>>>>>>>>>>> Running Job has been completed , cost time :  " + (endTime - startTime) + "ms\n");
    }

}