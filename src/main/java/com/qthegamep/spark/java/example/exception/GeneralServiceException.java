package com.qthegamep.spark.java.example.exception;

import com.qthegamep.spark.java.example.model.ErrorType;

public abstract class GeneralServiceException extends Exception implements ServiceException {

    private final ErrorType errorType;

    GeneralServiceException(ErrorType errorType) {
        this.errorType = errorType;
    }

    @Override
    public ErrorType getErrorType() {
        return errorType;
    }
}
