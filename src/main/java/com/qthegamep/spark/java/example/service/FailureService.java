package com.qthegamep.spark.java.example.service;

import com.qthegamep.spark.java.example.exception.FailureException;

@FunctionalInterface
public interface FailureService {

    void failure() throws FailureException;
}
