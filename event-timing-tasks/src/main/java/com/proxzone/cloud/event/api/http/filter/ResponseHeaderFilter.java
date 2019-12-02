package com.proxzone.cloud.event.api.http.filter;
import com.proxzone.cloud.event.api.http.exception.QueryParameterException;
import org.elasticsearch.common.Strings;
import spark.Filter;
import spark.Request;
import spark.Response;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/8/15
 */
public class ResponseHeaderFilter implements Filter {
    @Override
    public void handle(Request request, Response response) throws Exception {
        if (!Strings.isNullOrEmpty(response.raw().getHeader("Content-Type"))) {
            return;
        }
        String pathInfo = request.pathInfo();
        if (pathInfo.equals("/") || pathInfo.equals("/version"))
            return;
        String parameter = request.headers("Accept");
        if (Strings.isNullOrEmpty(parameter)
                || parameter.contains("application/json")
                || parameter.equals("*/*")) {
            response.header("Content-Type", "application/json;charset=utf-8");
        } else {
            throw new QueryParameterException("format", "unsupported format accept header value [" + parameter + "]");
        }
    }
}
