package com.proxzone.cloud.event.core.db.entry;

import com.google.gson.annotations.SerializedName;

public class EventGatewayReplyTaskEntity<T> {
    @SerializedName("code")
    private String result;
    @SerializedName("content")
    private T content;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}