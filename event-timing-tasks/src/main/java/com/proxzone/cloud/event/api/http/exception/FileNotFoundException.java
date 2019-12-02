package com.proxzone.cloud.event.api.http.exception;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/8/15
 */
public class FileNotFoundException extends BasicSparkRequestException {
    public FileNotFoundException(String cameraId, String timestemp, String suffix) {
        super(400, buildMessage(cameraId, timestemp, suffix));
    }

    private static String buildMessage(String cameraId, String timestemp, String suffix) {
        return "not found camera [" + cameraId + "] file [" + timestemp + "." + suffix + "]";
    }
}
