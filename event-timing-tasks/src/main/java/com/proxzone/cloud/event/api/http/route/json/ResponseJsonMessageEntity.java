package com.proxzone.cloud.event.api.http.route.json;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/8/14
 */
public class ResponseJsonMessageEntity<T> implements BasicResponseJsonEntity {
    @SerializedName("uri")
    private String uri;
    @SerializedName("code")
    private int code;
    @SerializedName("content_type")
    private String contentType;
    @SerializedName("content_list_size")
    private int listSize;
    @SerializedName("content")
    private T content;

    public ResponseJsonMessageEntity(String uri, T content) {
        this.uri = uri;
        this.code = 200;
        this.content = content;
        if (content == null) {
            this.code = 404;
        } else {
            if (content instanceof List) {
                contentType = "list";
                if (((List) content).size() == 0) {
                    this.code = 404;
                }
            } else {
                contentType = "object";
            }
        }
    }

    public int getListSize() {
        return listSize;
    }

    public void setListSize(int listSize) {
        this.listSize = listSize;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
