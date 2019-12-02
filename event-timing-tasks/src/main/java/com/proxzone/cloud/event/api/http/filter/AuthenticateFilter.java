package com.proxzone.cloud.event.api.http.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Filter;
import spark.Request;
import spark.Response;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/8/9
 */
public class AuthenticateFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticateFilter.class);

    @Override
    public void handle(Request request, Response response) throws Exception {
        LOGGER.debug("authenticate api call [{}]", request.pathInfo());
//        halt(401, "not authenticated");
    }
}
