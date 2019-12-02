package com.proxzone.cloud.event.api.http;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.proxzone.cloud.event.api.common.ApplicationArgs;
import com.proxzone.cloud.event.api.http.route.SparkRoutesBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;

import static spark.Spark.*;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/8/8
 */
@Singleton
public class DefaultSparkServer implements APIServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultSparkServer.class);
    private ApplicationArgs applicationArgs;
    private SparkRoutesBuilder sparkRoutesBuilder;

    @Inject
    public DefaultSparkServer(@Nullable ApplicationArgs applicationArgs,
                              @Nullable SparkRoutesBuilder sparkRoutesBuilder) {
        this.applicationArgs = applicationArgs;
        this.sparkRoutesBuilder = sparkRoutesBuilder;
    }

    @Override
    public void start() throws Exception {
        if (applicationArgs.isEnableHTTPS()) {
            //TODO HTTPS 证书设置
//            secure(keystoreFilePath, keystorePassword, truststoreFilePath, truststorePassword);
            LOGGER.info("setup HTTPS/SSL successed");
        }
        port(applicationArgs.getHttpPort());
        threadPool(applicationArgs.getHttpMaxThread());
        sparkRoutesBuilder.build();
       // awaitInitialization();
    }

    @Override
    public void stop() throws Exception {
        spark.Spark.stop();
    }

    @Override
    public void restart() throws Exception {
        stop();
        Thread.sleep(3000);
        start();
    }
}
