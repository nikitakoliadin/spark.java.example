package com.qthegamep.spark.java.example;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.qthegamep.spark.java.example.adapter.IsoDateJsonModuleAdapter;
import com.qthegamep.spark.java.example.config.ApplicationConfig;
import com.qthegamep.spark.java.example.config.LogConfig;
import com.qthegamep.spark.java.example.config.ShutdownHookConfig;
import com.qthegamep.spark.java.example.controller.FailureController;
import com.qthegamep.spark.java.example.controller.FailureControllerImpl;
import com.qthegamep.spark.java.example.controller.SuccessController;
import com.qthegamep.spark.java.example.controller.SuccessControllerImpl;
import com.qthegamep.spark.java.example.exception.ApplicationConfigInitializationException;
import com.qthegamep.spark.java.example.filter.*;
import com.qthegamep.spark.java.example.mapper.GeneralExceptionMapper;
import com.qthegamep.spark.java.example.mapper.GeneralExceptionMapperImpl;
import com.qthegamep.spark.java.example.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.*;

public class Application {

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws ApplicationConfigInitializationException {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.init();
        LogConfig logConfig = new LogConfig();
        logConfig.configureLogLevels();
        GenerationService generationService = buildGenerationService();
        ObjectMapper objectMapper = buildObjectMapper();
        SuccessService successService = buildSuccessService();
        FailureService failureService = buildFailureService();
        ConverterService converterService = buildConverterService(objectMapper);
        GeneralExceptionMapper generalExceptionMapper = buildGeneralExceptionMapper(converterService);
        RequestIdRequestFilter requestIdRequestFilter = buildRequestIdRequestFilter(generationService);
        DurationRequestFilter durationRequestFilter = buildDurationRequestFilter();
        RequestIdResponseFilter requestIdResponseFilter = buildRequestIdResponseFilter();
        DurationResponseFilter durationResponseFilter = buildDurationResponseFilter();
        ResponseLogFilter responseLogFilter = buildResponseLogFilter();
        SuccessController successController = buildSuccessController(successService, converterService);
        FailureController failureController = buildFailureController(failureService);
        int port = Integer.parseInt(System.getProperty("application.port", "8080"));
        port(port);
        int maxThreads = Integer.parseInt(System.getProperty("application.server.max.threads"));
        int minThreads = Integer.parseInt(System.getProperty("application.server.min.threads"));
        int idleTimeout = Integer.parseInt(System.getProperty("application.server.idle.timeout"));
        threadPool(maxThreads, minThreads, idleTimeout);
        init();
        awaitInitialization();
        generalExceptionMapper.initGeneralExceptionMapper();
        requestIdRequestFilter.initRequestIdRequestFilter();
        durationRequestFilter.initDurationRequestFilter();
        requestIdResponseFilter.initRequestIdResponseFilter();
        durationResponseFilter.initDurationResponseFilter();
        responseLogFilter.initResponseLogFilter();
        successController.initSuccessController();
        failureController.initFailureController();
        Runtime.getRuntime().addShutdownHook(new ShutdownHookConfig());
        LOG.info("Application started");
    }

    private static GenerationService buildGenerationService() {
        return new GenerationServiceImpl();
    }

    private static ObjectMapper buildObjectMapper() {
        return new ObjectMapper()
                .enable(SerializationFeature.INDENT_OUTPUT)
                .configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true)
                .registerModule(new IsoDateJsonModuleAdapter().buildModule());
    }

    private static SuccessService buildSuccessService() {
        return new SuccessServiceImpl();
    }

    private static FailureService buildFailureService() {
        return new FailureServiceImpl();
    }

    private static ConverterService buildConverterService(ObjectMapper objectMapper) {
        return new ConverterServiceImpl(objectMapper);
    }

    private static GeneralExceptionMapper buildGeneralExceptionMapper(ConverterService converterService) {
        return new GeneralExceptionMapperImpl(converterService);
    }

    private static RequestIdRequestFilter buildRequestIdRequestFilter(GenerationService generationService) {
        return new RequestIdRequestFilterImpl(generationService);
    }

    private static DurationRequestFilter buildDurationRequestFilter() {
        return new DurationRequestFilterImpl();
    }

    private static RequestIdResponseFilter buildRequestIdResponseFilter() {
        return new RequestIdResponseFilterImpl();
    }

    private static DurationResponseFilter buildDurationResponseFilter() {
        return new DurationResponseFilterImpl();
    }

    private static ResponseLogFilter buildResponseLogFilter() {
        return new ResponseLogFilterImpl();
    }

    private static SuccessController buildSuccessController(SuccessService successService,
                                                            ConverterService converterService) {
        return new SuccessControllerImpl(
                successService,
                converterService);
    }

    private static FailureController buildFailureController(FailureService failureService) {
        return new FailureControllerImpl(failureService);
    }
}
