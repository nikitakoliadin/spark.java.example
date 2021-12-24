package com.qthegamep.spark.java.example.exception;

import com.qthegamep.spark.java.example.model.ErrorType;

public abstract class GeneralServiceRuntimeException extends RuntimeException implements ServiceException {

    private final ErrorType errorType;

    GeneralServiceRuntimeException(Throwable cause, ErrorType errorType) {
        super(cause);
        this.errorType = errorType;
    }

    @Override
    public ErrorType getErrorType() {
        return errorType;
    }
}
