package com.proxzone.cloud.event.api.http.route;

import com.google.common.base.Strings;
import com.google.inject.Injector;
import com.proxzone.cloud.event.api.common.ApplicationInjector;
import com.proxzone.cloud.event.api.common.Json;
import com.proxzone.cloud.event.api.http.exception.*;
import com.proxzone.cloud.event.api.http.route.json.BasicResponseJsonEntity;
import com.proxzone.cloud.event.api.http.route.json.ResponseJsonMessageEntity;
import spark.Request;
import spark.Route;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/8/9
 */
public abstract class BasicSparkRoute implements Route {
    private static final String DEFAULT_DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    protected Injector injector;
    private Json json;

    public BasicSparkRoute() {
        injector = ApplicationInjector.getInjector().getGuiceInjector();
        json = injector.getInstance(Json.class);
    }

    protected String getQueryParameter(Request request, String name, boolean require) throws QueryParameterException {
        String parameter = request.queryParams(name);
        if (require)
            if (Strings.isNullOrEmpty(request.queryParams(name))) {
                throw new QueryParameterException(name);
            }
        return parameter == null ? "" : parameter;
    }

    protected String getResponseJson(BasicResponseJsonEntity obj) throws Exception {
        return json.toJson(obj);
    }

    protected <T> String getJson(Request request, T obj)
            throws Exception {
        return getResponseJson(new ResponseJsonMessageEntity<>(request.uri(), obj));
    }

    protected <T> String getSuccessed(Request request) throws Exception {
        return getJson(request, "successed");
    }

    protected <T> String getJson(Request request, List<T> list) throws
            Exception {
        ResponseJsonMessageEntity entity =
                new ResponseJsonMessageEntity<>(request.uri(), list);
        entity.setListSize(list.size());
        return getResponseJson(entity);
    }

    protected String getResponseJson(List<? extends BasicResponseJsonEntity> obj) throws Exception {
        return json.toJson(obj);
    }

    protected <T> T getBodyJsonEntity(Class<T> classType, Request request) throws Exception {
        T entity = json.fromJson(classType, request.body());
        if (entity == null) {
            throw new RequestBodyException("json format error");
        }
        return entity;
    }

    protected Date getQueryDateParameter(Request request, String name, boolean require)
            throws QueryParameterException {
        String date = getQueryParameter(request, name, require);
        if (Strings.isNullOrEmpty(date))
            return null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT_PATTERN);
        try {
            return simpleDateFormat.parse(date);
        } catch (Exception ex) {
            throw new QueryParameterException(name, ex.getMessage());
        }
    }

    protected <T> List<T> decodeJsonBodyToList(Class<T> type, Request request) throws Exception {
        String contentType = request.headers("Content-Type");
        if (Strings.isNullOrEmpty(contentType))
            throw new RequestHeaderException("Content-Type");
        if (!contentType.equals("application/json"))
            throw new RequestHeaderException("Content-Type", "unsupported content type [" + contentType + "]");
        String jsonContent = request.body();
        if (Strings.isNullOrEmpty(jsonContent))
            throw new BasicSparkRequestException(400, "request body is null");
        try {
            List<T> list = json.fromJsonToList(type, jsonContent);
            if (list == null || list.size() == 0)
                throw new BodyFormatException("json");
            return list;
        } catch (Exception e) {
            throw new BodyFormatException("json");
        }
    }
}
