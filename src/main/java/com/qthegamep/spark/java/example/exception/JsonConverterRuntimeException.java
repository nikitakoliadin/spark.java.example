package com.qthegamep.spark.java.example.exception;

import com.qthegamep.spark.java.example.model.ErrorType;

public class JsonConverterRuntimeException extends GeneralServiceRuntimeException {

    public JsonConverterRuntimeException(Throwable cause, ErrorType errorType) {
        super(cause, errorType);
    }
}
