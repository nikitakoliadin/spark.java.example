package com.qthegamep.spark.java.example.model;

public enum ErrorType {

    JSON_CONVERTER_ERROR(511);

    private int errorCode;

    ErrorType(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
