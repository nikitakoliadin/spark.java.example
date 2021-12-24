package com.qthegamep.spark.java.example;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.qthegamep.spark.java.example.adapter.IsoDateJsonModuleAdapter;
import com.qthegamep.spark.java.example.config.ApplicationConfig;
import com.qthegamep.spark.java.example.config.LogConfig;
import com.qthegamep.spark.java.example.config.ShutdownHookConfig;
import com.qthegamep.spark.java.example.controller.SuccessController;
import com.qthegamep.spark.java.example.controller.SuccessControllerImpl;
import com.qthegamep.spark.java.example.exception.ApplicationConfigInitializationException;
import com.qthegamep.spark.java.example.filter.*;
import com.qthegamep.spark.java.example.service.ConverterService;
import com.qthegamep.spark.java.example.service.ConverterServiceImpl;
import com.qthegamep.spark.java.example.service.GenerationService;
import com.qthegamep.spark.java.example.service.GenerationServiceImpl;
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
        ConverterService converterService = buildConverterService(objectMapper);
        RequestIdRequestFilter requestIdRequestFilter = buildRequestIdRequestFilter(generationService);
        DurationRequestFilter durationRequestFilter = buildDurationRequestFilter();
        RequestIdResponseFilter requestIdResponseFilter = buildRequestIdResponseFilter();
        DurationResponseFilter durationResponseFilter = buildDurationResponseFilter();
        SuccessController successController = buildSuccessController(converterService);
        int port = Integer.parseInt(System.getProperty("application.port", "8080"));
        port(port);
        int maxThreads = Integer.parseInt(System.getProperty("application.server.max.threads"));
        int minThreads = Integer.parseInt(System.getProperty("application.server.min.threads"));
        int idleTimeout = Integer.parseInt(System.getProperty("application.server.idle.timeout"));
        threadPool(maxThreads, minThreads, idleTimeout);
        init();
        awaitInitialization();
        requestIdRequestFilter.initRequestIdRequestFilter();
        durationRequestFilter.initDurationRequestFilter();
        requestIdResponseFilter.initRequestIdResponseFilter();
        durationResponseFilter.initDurationResponseFilter();
        successController.initSuccessController();
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

    private static ConverterService buildConverterService(ObjectMapper objectMapper) {
        return new ConverterServiceImpl(objectMapper);
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

    private static SuccessController buildSuccessController(ConverterService converterService) {
        return new SuccessControllerImpl(converterService);
    }
}
