package com.proxzone.cloud.event.core.service;

import com.proxzone.cloud.event.core.db.entity.JobEntity;
import org.quartz.*;

import java.util.ArrayList;
import java.util.List;

public interface DynamicJobService {

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    JobEntity getJobInfo (int id) throws Exception;
    /**
     * 获取所有任务工作
     * @return
     */
    List<JobEntity> getAll() throws Exception;

    //获取JobDataMap.(Job参数对象)
     JobDataMap getJobDataMap(JobEntity job) ;

    //获取JobDetail,JobDetail是任务的定义,而Job是任务的执行逻辑,JobDetail里会引用一个Job Class来定义
    JobDetail getJobDetail(JobKey jobKey, String description, JobDataMap map) ;


    //获取Trigger (Job的触发器,执行规则)
    Trigger getTrigger(JobEntity job) ;


    //获取JobKey,包含Name和Group
     JobKey getJobKey(JobEntity job) ;


}
