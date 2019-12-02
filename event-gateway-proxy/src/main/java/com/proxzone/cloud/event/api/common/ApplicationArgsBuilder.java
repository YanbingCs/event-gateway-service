package com.proxzone.cloud.event.api.common;

/**
 * @author mayanbin@proxzone.com
 * @version 1.0
 * @date 19-5-5 上午11:56
 */
public interface ApplicationArgsBuilder {
    /**
     * 构建程序启动参数
     *
     * @return args 启动参数
     * @throws Exception 构建异常
     */
    ApplicationArgs build() throws Exception;
}
