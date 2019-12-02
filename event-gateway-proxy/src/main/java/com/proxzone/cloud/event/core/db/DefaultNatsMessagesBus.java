package com.proxzone.cloud.event.core.db;

import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import com.proxzone.cloud.event.api.common.ApplicationArgs;
import com.proxzone.cloud.event.api.common.Json;
import io.nats.client.Connection;
import io.nats.client.ConnectionFactory;
import io.nats.client.Message;
import io.nats.client.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;

/**
 * @author mayanbin@proxzone.com
 * @version 1.0
 * @date 18-11-30 上午10:12
 */
public class DefaultNatsMessagesBus implements MessagesBus {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultNatsMessagesBus.class);
    private static final long NATS_TIME_OUT = 5000;
    private ApplicationArgs applicationArgs;
    private Connection connection;
    private Json json;

    @Inject
    public DefaultNatsMessagesBus(ApplicationArgs applicationArgs, Json json) throws Exception {
        this.applicationArgs = applicationArgs;
        this.json = json;
        String[] urls = applicationArgs.getNatsUrls().split(",");
        connection = new ConnectionFactory(urls).createConnection();
    }

    @Override
    public void publish(String subject, String message) throws Exception {
        connection.publish(subject, message.getBytes());
    }

    @Override
    public void publish(String subject, Object message) throws Exception {
        String jsonString = json.toJson(message);
        publish(subject, jsonString);
    }

    @Override
    public void publish(String subject, String type, Object request) throws Exception {
        String controlJson = generateJson(type, request);
        publish(subject, controlJson);
    }

    @Override
    public Message request(String subject, String message) throws Exception {
        return connection.request(subject, message.getBytes(), NATS_TIME_OUT);
    }

    @Override
    public Message request(String subject, Object message) throws Exception {
        String jsonString = json.toJson(message);
        return request(subject, jsonString);
    }

    @Override
    public <T> T request(String subject, String type, Object request, Class<T> reply) throws Exception {
        String controlJson = generateJson(type, request);
        Message message = request(subject, controlJson);
        if (message == null)
            return null;
        String jsonString = new String(message.getData());
        LOGGER.debug("response json -> {}", jsonString);
        return json.fromJson(reply, jsonString);
    }

    @Override
    public void subscibe(String subject, MessageHandler messageHandler) {
        connection.subscribe(subject, messageHandler);
    }

    private <T> String generateJson(String type, T request) {
        NatsControllerEntry<T> controllerEntry = new NatsControllerEntry<>();
        controllerEntry.setContent(request);
        controllerEntry.setOperate(type);
        Type jsonType = new TypeToken<NatsControllerEntry<T>>() {
        }.getType();
        String jsonString = json.toJson(controllerEntry, jsonType);
        LOGGER.debug("json -> {}", jsonString);
        return jsonString;
    }

    public static class NatsControllerEntry<T> {
        private String operate;
        private T content;

        public String getOperate() {
            return operate;
        }

        public void setOperate(String operate) {
            this.operate = operate;
        }

        public T getContent() {
            return content;
        }

        public void setContent(T content) {
            this.content = content;
        }
    }
}
