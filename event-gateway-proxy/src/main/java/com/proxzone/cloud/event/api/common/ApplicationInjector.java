package com.proxzone.cloud.event.api.common;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

/**
 * @author mayanbin@proxzone.com
 * @version 1.0
 * @date 19-5-5 上午11:43
 */
public class ApplicationInjector {
    private static ApplicationInjector INJECTOR = null;
    private Injector guiceInjector;

    private ApplicationInjector() {
    }

    public static ApplicationInjector getInjector() {
        synchronized (ApplicationInjector.class) {
            if (INJECTOR == null)
                INJECTOR = new ApplicationInjector();
        }
        return INJECTOR;
    }

    public static ApplicationInjector initInjector(Module... modules) {
        ApplicationInjector applicationInjector = getInjector();
        applicationInjector.guiceInjector = Guice.createInjector(modules);
        return applicationInjector;
    }

    public Injector getGuiceInjector() {
        return guiceInjector;
    }
}
