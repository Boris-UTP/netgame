package com.netgame.netgame.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by arkanay on 8/11/17.
 */

public class Base<T> {

    @SerializedName("data")
    private T data;

    @SerializedName("statusBody")
    private StatusBody statusBody;

    public T getData() {
        return data;
    }

    public Base setData(T data) {
        this.data = data;
        return this;
    }

    public StatusBody getStatusBody() {
        return statusBody;
    }

    public void setStatusBody(StatusBody statusBody) {
        this.statusBody = statusBody;
    }

    public class StatusBody {


        @SerializedName("cod")
        private String code;

        @SerializedName("message")
        private String message;

        public String getCode() {
            return code;
        }

        public StatusBody setCode(String code) {
            this.code = code;
            return this;
        }

        public String getMessage() {
            return message;
        }

        public StatusBody setMessage(String message) {
            this.message = message;
            return this;
        }
    }

}
