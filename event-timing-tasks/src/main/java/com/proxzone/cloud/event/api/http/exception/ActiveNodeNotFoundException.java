package com.proxzone.cloud.event.api.http.exception;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/8/16
 */
public class ActiveNodeNotFoundException extends BasicSparkRequestException {
    public ActiveNodeNotFoundException(String type) {
        super(404, buildMessage(type));
    }

    public ActiveNodeNotFoundException(String type, String dc) {
        super(404, buildMessage(type, dc));
    }

    private static String buildMessage(String type) {
        return "not found active node by type [" + type + "]";
    }

    private static String buildMessage(String type, String dc) {
        return "not found active " + "[" + type + "]" + " node from data center [" + dc + "]";
    }
}
