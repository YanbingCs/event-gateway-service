package com.proxzone.cloud.event.api.http.exception;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/8/9
 */
public class ResourceNotFoundException extends BasicSparkRequestException {
    public ResourceNotFoundException(String name, String resource) {
        super(404, buildMessage(name, resource));
    }

    public ResourceNotFoundException(String message) {
        super(404, message);
    }

    private static String buildMessage(String name, String resource) {
        return "Not found resource [" + name + "] by [" + resource + "]";
    }
}
