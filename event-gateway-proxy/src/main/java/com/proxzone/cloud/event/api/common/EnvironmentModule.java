package com.proxzone.cloud.event.api.common;

import com.google.inject.AbstractModule;

/**
 * @author mayanbin@proxzone.com
 * @version 1.0
 * @date 19-5-5 上午11:54
 */
public class EnvironmentModule extends AbstractModule {
    private String[] args;

    public EnvironmentModule(String[] args) {
        this.args = args;
    }

    @Override
    protected void configure() {
        ApplicationArgsBuilder argsBuilder = new DefaultApplicationArgsBuilder(args);
        ApplicationArgs applicationArgs = null;
        try {
            applicationArgs = argsBuilder.build();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        bind(ApplicationArgs.class).toInstance(applicationArgs);
        bind(Json.class).to(DefaultGsonJson.class);
    }
}
