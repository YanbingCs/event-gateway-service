package com.proxzone.cloud.event.core.db;
import com.proxzone.cloud.event.core.db.entry.EventServiceEntity;


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
     * 通过服务Id获取服务详细
     *
     * @return
     */
    EventServiceEntity getServiceInfo(String serviceId ) throws Exception;


}
