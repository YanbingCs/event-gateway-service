package com.proxzone.cloud.event.api.http.exception;

/**
 * @author mayanbin@proxzone.com
 * @version 1.0
 * @date 18-8-13 下午4:39
 */
public class RequestBodyException extends BasicSparkRequestException {
    public RequestBodyException(String message) {
        super(400, buildMessage(message));
    }

    private static String buildMessage(String message) {
        return "post request body error message: " + message;
    }
}
