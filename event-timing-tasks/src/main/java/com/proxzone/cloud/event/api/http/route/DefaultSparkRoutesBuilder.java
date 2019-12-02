package com.proxzone.cloud.event.api.http.route;
import com.google.inject.Inject;
import com.proxzone.cloud.event.api.common.ApplicationArgs;

import static spark.Spark.*;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/8/8
 */
public class DefaultSparkRoutesBuilder implements SparkRoutesBuilder {
    private ApplicationArgs applicationArgs;

    @Inject
    public DefaultSparkRoutesBuilder(ApplicationArgs applicationArgs) {
        this.applicationArgs = applicationArgs;
    }




    @Override
    public void build() throws Exception {

    }
}
