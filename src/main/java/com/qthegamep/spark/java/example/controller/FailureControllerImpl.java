package com.qthegamep.spark.java.example.controller;

import com.qthegamep.spark.java.example.service.FailureService;
import com.qthegamep.spark.java.example.util.Paths;

import static spark.Spark.get;

public class FailureControllerImpl implements FailureController {

    private FailureService failureService;

    public FailureControllerImpl(FailureService failureService) {
        this.failureService = failureService;
    }

    @Override
    public void initFailureController() {
        get(Paths.FAILURE_PATH, (request, response) -> {
            failureService.failure();
            return null;
        });
    }
}
