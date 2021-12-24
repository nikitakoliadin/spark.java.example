package com.qthegamep.spark.java.example.exception;

import com.qthegamep.spark.java.example.model.ErrorType;

@FunctionalInterface
public interface ServiceException {

    ErrorType getErrorType();
}
