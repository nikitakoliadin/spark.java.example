package com.qthegamep.spark.java.example.mapper;

import com.qthegamep.spark.java.example.dto.ErrorResponseDTO;
import com.qthegamep.spark.java.example.model.ErrorType;
import com.qthegamep.spark.java.example.service.ConverterService;
import org.eclipse.jetty.http.MimeTypes;

import static spark.Spark.notFound;

public class NotFoundExceptionMapperImpl implements NotFoundExceptionMapper {

    private ConverterService converterService;

    public NotFoundExceptionMapperImpl(ConverterService converterService) {
        this.converterService = converterService;
    }

    @Override
    public void initNotFoundExceptionMapper() {
        notFound((request, response) -> {
            response.type(MimeTypes.Type.APPLICATION_JSON_UTF_8.asString());
            ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
            errorResponseDTO.setErrorCode(ErrorType.PAGE_NOT_FOUND_ERROR.getErrorCode());
            return converterService.toJson(errorResponseDTO);
        });
    }
}
