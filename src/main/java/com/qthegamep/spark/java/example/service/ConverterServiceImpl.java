package com.qthegamep.spark.java.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qthegamep.spark.java.example.exception.JsonConverterRuntimeException;
import com.qthegamep.spark.java.example.model.ErrorType;

public class ConverterServiceImpl implements ConverterService {

    private ObjectMapper json;

    public ConverterServiceImpl(ObjectMapper json) {
        this.json = json;
    }

    @Override
    public String toJson(Object model) {
        try {
            return json.writeValueAsString(model);
        } catch (JsonProcessingException e) {
            throw new JsonConverterRuntimeException(e, ErrorType.JSON_CONVERTER_ERROR);
        }
    }
}
