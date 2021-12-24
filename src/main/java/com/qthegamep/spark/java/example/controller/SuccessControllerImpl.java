package com.qthegamep.spark.java.example.controller;

import com.qthegamep.spark.java.example.dto.SuccessResponseDTO;
import com.qthegamep.spark.java.example.util.Paths;

import java.util.Date;

import static spark.Spark.*;

public class SuccessControllerImpl implements SuccessController {

    @Override
    public void initSuccessController() {
        get(Paths.SUCCESS_PATH, (request, response) -> {
            SuccessResponseDTO successResponseDTO = new SuccessResponseDTO();
            successResponseDTO.setNow(new Date());
            return successResponseDTO;
        });
    }
}
