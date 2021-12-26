package com.qthegamep.spark.java.example.controller;

import com.qthegamep.spark.java.example.dto.SuccessResponseDTO;
import com.qthegamep.spark.java.example.service.ConverterService;
import com.qthegamep.spark.java.example.service.SuccessService;
import com.qthegamep.spark.java.example.util.Constants;
import com.qthegamep.spark.java.example.util.Paths;
import org.eclipse.jetty.http.MimeTypes;

import static spark.Spark.*;

public class SuccessControllerImpl implements SuccessController {

    private SuccessService successService;
    private ConverterService converterService;

    public SuccessControllerImpl(SuccessService successService,
                                 ConverterService converterService) {
        this.successService = successService;
        this.converterService = converterService;
    }

    @Override
    public void initSuccessController() {
        get(Paths.SUCCESS_PATH, (request, response) -> {
            String requestId = request.attribute(Constants.REQUEST_ID_HEADER);
            SuccessResponseDTO successResponseDTO = successService.success(requestId);
            response.type(MimeTypes.Type.APPLICATION_JSON_UTF_8.asString());
            return successResponseDTO;
        }, converterService::toJson);
    }
}
