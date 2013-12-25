package org.feathercoin.monitoring.dto;

import com.google.gson.annotations.SerializedName;

public class Status {
    @SerializedName("STATUS") private String status;
    @SerializedName("When") private Long when;
    @SerializedName("Code") private  Integer code;
    @SerializedName("Msg") private  String message;
    @SerializedName("Description") private  String description;



    public String getStatus() {
        return status;
    }

    public Long getWhen() {
        return when;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Status{" +
                "status='" + status + '\'' +
                ", when=" + when +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
