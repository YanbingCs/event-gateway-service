package com.proxzone.cloud.event.api.http;

import com.google.inject.AbstractModule;
import com.google.inject.BindingAnnotation;
import com.proxzone.cloud.event.api.http.route.DefaultSparkRoutesBuilder;
import com.proxzone.cloud.event.api.http.route.SparkRoutesBuilder;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/8/8
 */
public class HTTPAPIServerModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(APIServer.class).annotatedWith(HTTP_API_SERVER.class).to(DefaultSparkServer.class);
        bind(SparkRoutesBuilder.class).to(DefaultSparkRoutesBuilder.class);
    }

    @BindingAnnotation
    @Target({FIELD, PARAMETER, METHOD})
    @Retention(RUNTIME)
    public @interface HTTP_API_SERVER {
    }


}
