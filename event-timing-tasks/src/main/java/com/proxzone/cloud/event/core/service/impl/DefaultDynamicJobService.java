package com.proxzone.cloud.event.core.service.impl;
import com.google.inject.Inject;
import com.proxzone.cloud.event.core.db.RelationalDatabase;
import com.proxzone.cloud.event.core.db.entity.JobEntity;
import com.proxzone.cloud.event.core.service.DynamicJob;
import com.proxzone.cloud.event.core.service.DynamicJobService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


public class DefaultDynamicJobService implements DynamicJobService {


    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultDynamicJobService.class);

    private RelationalDatabase relationalDatabase;

    @Inject
    public DefaultDynamicJobService(RelationalDatabase relationalDatabase) {
        this.relationalDatabase = relationalDatabase;

    }

    @Override
    public JobEntity getJobInfo(int id) throws Exception {
        return relationalDatabase.getJobInfo(id);
    }

    @Override
    public List<JobEntity> getAll() throws Exception {
        List<JobEntity> list = new ArrayList<>();
        relationalDatabase.getAll().forEach(list::add);
        return list;
    }

    @Override
    public JobDataMap getJobDataMap(JobEntity job) {
        JobDataMap map = new JobDataMap();
        map.put("name", job.getName());
        map.put("jobGroup", job.getGrop());
        map.put("cronExpression", job.getCron());
        map.put("parameter", job.getParameter());
        map.put("jobDescription", job.getDescription());
        map.put("vmParam", job.getvParam());
        map.put("serviceId", job.getServiceId());
        map.put("status", job.getStatus());
        return map;
    }

    @Override
    public JobDetail getJobDetail(JobKey jobKey, String description, JobDataMap map) {

        return JobBuilder.newJob(DynamicJob.class)
                .withIdentity(jobKey)
                .withDescription(description)
                .setJobData(map)
                .storeDurably()
                .build();
    }

    @Override
    public Trigger getTrigger(JobEntity job) {
        return TriggerBuilder.newTrigger()
                .withIdentity(job.getName(), job.getGrop())
                .withSchedule(CronScheduleBuilder.cronSchedule(job.getCron()))
                .build();
    }

    @Override
    public JobKey getJobKey(JobEntity job) {
        return JobKey.jobKey(job.getName(), job.getGrop());
    }
}
