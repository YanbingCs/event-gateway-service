package com.proxzone.cloud.event.api.http.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Filter;
import spark.Request;
import spark.Response;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/8/8
 */
public class AccessMonitorFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccessMonitorFilter.class);

    @Override
    public void handle(Request request, Response response) throws Exception {
        LOGGER.debug("Received api call [{}]", request.pathInfo());
    }
}
