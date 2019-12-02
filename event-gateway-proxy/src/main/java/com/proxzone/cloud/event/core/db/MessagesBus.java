package com.proxzone.cloud.event.core.db;
import io.nats.client.Message;
import io.nats.client.MessageHandler;

/**
 * @author mayanbin@proxzone.com
 * @version 1.0
 * @date 18-11-30 上午10:11
 */
public interface MessagesBus {
    /**
     *
     * @param subject
     * @param message
     * @throws Exception
     */
    void publish(String subject, String message) throws Exception;

    /**
     *
     * @param subject
     * @param message
     * @throws Exception
     */
    void publish(String subject, Object message) throws Exception;

    /**
     *
     * @param subject
     * @param type
     * @param request
     * @throws Exception
     */
    void publish(String subject, String type, Object request) throws Exception;

    /**
     *
     * @param subject
     * @param message
     * @return
     * @throws Exception
     */
    Message request(String subject, String message) throws Exception;

    /**
     *
     * @param subject
     * @param message
     * @return
     * @throws Exception
     */
    Message request(String subject, Object message) throws Exception;

    /**
     *
     * @param subject
     * @param type
     * @param request
     * @param reply
     * @param <T>
     * @return
     * @throws Exception
     */
    <T> T request(String subject, String type, Object request, Class<T> reply) throws Exception;

    /**
     *
     * @param subject
     * @param messageHandler
     */
    void subscibe(String subject, MessageHandler messageHandler);
}
