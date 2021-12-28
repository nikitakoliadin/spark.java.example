package com.qthegamep.spark.java.example.mapper;

import com.qthegamep.spark.java.example.dto.ErrorResponseDTO;
import com.qthegamep.spark.java.example.exception.ServiceException;
import com.qthegamep.spark.java.example.model.ErrorType;
import com.qthegamep.spark.java.example.service.ConverterService;
import com.qthegamep.spark.java.example.util.Constants;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.http.MimeTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.exception;

public class GeneralExceptionMapperImpl implements GeneralExceptionMapper {

    private static final Logger LOG = LoggerFactory.getLogger(GeneralExceptionMapperImpl.class);

    private ConverterService converterService;

    public GeneralExceptionMapperImpl(ConverterService converterService) {
        this.converterService = converterService;
    }

    @Override
    public void initGeneralExceptionMapper() {
        exception(Exception.class, (exception, request, response) -> {
            String path = request.url();
            String requestId = request.attribute(Constants.REQUEST_ID_HEADER);
            String clientIp = request.ip();
            String startTime = request.attribute(Constants.START_TIME_HEADER);
            String duration = String.valueOf(System.currentTimeMillis() - Long.parseLong(startTime));
            ErrorType errorType = getErrorType(exception);
            ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
            errorResponseDTO.setErrorCode(errorType.getErrorCode());
            String errorResponse = converterService.toJson(errorResponseDTO);
            response.body(errorResponse);
            response.status(HttpStatus.BAD_REQUEST_400);
            response.type(MimeTypes.Type.APPLICATION_JSON_UTF_8.asString());
            response.header(Constants.REQUEST_ID_HEADER, requestId);
            response.header(Constants.DURATION_HEADER, duration);
            LOG.error("Error. Path: {} RequestId: {} Client IP: {} Duration: {} Error response DTO: {}", path, requestId, clientIp, duration, errorResponseDTO, exception);
        });
    }

    private ErrorType getErrorType(Exception exception) {
        if (exception instanceof ServiceException) {
            return ((ServiceException) exception).getErrorType();
        } else {
            return ErrorType.INTERNAL_ERROR;
        }
    }
}
