package com.proxzone.cloud.event.core.db;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.iciql.Db;
import com.proxzone.cloud.event.api.common.ApplicationArgs;
import com.proxzone.cloud.event.core.db.entry.EventServiceEntity;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/8/8
 */
@Singleton
public class DefaultIciqlMySQLDatabase implements RelationalDatabase {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultIciqlMySQLDatabase.class);
    private ApplicationArgs applicationArgs;
    private Db db;
    private ScheduledExecutorService queryTestService;

    @Inject
    public DefaultIciqlMySQLDatabase(@Nullable ApplicationArgs applicationArgs) throws Exception {
        this.applicationArgs = applicationArgs;
        queryTestService = Executors.newSingleThreadScheduledExecutor();
         init();
    }

    private void init() throws Exception {
        String uri = "jdbc:mysql://" + applicationArgs.getMysqlAddress() + ":" + applicationArgs.getMysqlPort()
                + "/" + applicationArgs.getDbName() + "?" + DB_PARAMS;
        LOGGER.info("mysql connect string: {}", uri);
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
       // ds.setDriverClassName("com.p6spy.engine.spy.P6SpyDriver");
        ds.setValidationQuery(VALIDATION_SQL);
        ds.setTestOnBorrow(true);
        ds.setTestWhileIdle(true);
        ds.setTestOnCreate(true);
        ds.setValidationQueryTimeout(DEFAULT_VALIDATION_TIMEOUT);
        ds.setInitialSize(DEFAULT_INIT_SIZE);
        ds.setMaxTotal(DEFAULT_MAX_SIZE);
        ds.setUrl(uri);
        ds.setUsername(applicationArgs.getMysqlUser());
        ds.setPassword(applicationArgs.getMysqlPwd());
        ds.setTestWhileIdle(true);
        db = Db.open(ds);
        db.activateConsoleLogger();
//        queryTestService.scheduleWithFixedDelay(() -> db.executeQuery(ds.getValidationQuery()),
//                ds.getValidationQueryTimeout(),
//                ds.getValidationQueryTimeout(), TimeUnit.SECONDS);
    }


    @Override
    public EventServiceEntity getServiceInfo(String serviceId) throws Exception {
        EventServiceEntity  serviceEntity=new EventServiceEntity();
        return db.from(serviceEntity).where(serviceEntity.getServiceId()).is(serviceId).selectFirst();
    }
}
