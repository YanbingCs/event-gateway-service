package com.proxzone.cloud.event.api.http;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/8/8
 */
public interface APIServer {

    /**
     * 启动API服务器
     *
     * @throws Exception 启动失败异常信息
     */
    void start() throws Exception;

    /**
     * 关闭API服务器
     *
     * @throws Exception API服务器关闭异常
     */
    void stop() throws Exception;

    /**
     * 重启API服务器
     *
     * @throws Exception API服务器重启异常
     */
    void restart() throws Exception;
}
