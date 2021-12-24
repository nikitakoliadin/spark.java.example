package com.qthegamep.spark.java.example.service;

import com.qthegamep.spark.java.example.exception.FailureException;
import com.qthegamep.spark.java.example.model.ErrorType;

public class FailureServiceImpl implements FailureService {

    @Override
    public void failure() throws FailureException {
        throw new FailureException(ErrorType.FAILURE_ERROR);
    }
}
