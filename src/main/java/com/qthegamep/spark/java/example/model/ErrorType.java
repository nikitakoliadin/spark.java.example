package com.qthegamep.spark.java.example.model;

public enum ErrorType {

    PAGE_NOT_FOUND_ERROR(404),
    INTERNAL_ERROR(500),
    FAILURE_ERROR(501),
    JSON_CONVERTER_ERROR(511);

    private int errorCode;

    ErrorType(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
