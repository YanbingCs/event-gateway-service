package com.proxzone.cloud.event.core.db;

import com.proxzone.cloud.event.core.db.entity.JobEntity;

import java.util.List;


/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/8/8
 */
public interface RelationalDatabase extends Database {
    String VALIDATION_SQL = "select 1=1";
    //int DEFAULT_INIT_SIZE = 10;
    int DEFAULT_INIT_SIZE = 2;
    int DEFAULT_MAX_SIZE = 2;
    //int DEFAULT_MAX_SIZE = 50;
    int DEFAULT_VALIDATION_TIMEOUT = 60 * 10;
    String DB_PARAMS = "useSSL=false&useUnicode=true&characterEncoding=utf8";
    /**
     * 通过任务Id获取任务
     *
     * @return
     */
    JobEntity getJobInfo(int Jid) throws Exception;

    /**
     * 任务工作列表
     * @return
     */
    List<JobEntity> getAll() throws  Exception;
}
