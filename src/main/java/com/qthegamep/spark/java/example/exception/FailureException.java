package com.qthegamep.spark.java.example.exception;

import com.qthegamep.spark.java.example.model.ErrorType;

public class FailureException extends GeneralServiceException {

    public FailureException(ErrorType errorType) {
        super(errorType);
    }
}
