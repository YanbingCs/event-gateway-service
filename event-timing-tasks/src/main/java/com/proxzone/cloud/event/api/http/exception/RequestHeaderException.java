package com.proxzone.cloud.event.api.http.exception;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/8/9
 */
public class RequestHeaderException extends BasicSparkRequestException {

    public RequestHeaderException(String name, String message) {
        super(400, buildMessage(name, message));
    }

    public RequestHeaderException(String name) {
        super(400, buildMessage(name, "not define"));
    }

    private static String buildMessage(String name, String message) {
        return "bad request header[" + name + "],error message: " + message;
    }
}
