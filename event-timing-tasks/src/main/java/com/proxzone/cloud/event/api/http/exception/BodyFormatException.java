package com.proxzone.cloud.event.api.http.exception;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/8/19
 */
public class BodyFormatException extends BasicSparkRequestException {
    public BodyFormatException(String bodyType) {
        super(400, buildMessage(bodyType));
    }

    private static String buildMessage(String bodyType) {
        return "request body format to [" + bodyType + "] error";
    }
}
