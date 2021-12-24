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
import com.qthegamep.spark.java.example.service.ConverterService;
import com.qthegamep.spark.java.example.service.ConverterServiceImpl;
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
        ObjectMapper objectMapper = buildObjectMapper();
        ConverterService converterService = buildConverterService(objectMapper);
        SuccessController successController = buildSuccessController(converterService);
        int port = Integer.parseInt(System.getProperty("application.port", "8080"));
        port(port);
        int maxThreads = Integer.parseInt(System.getProperty("application.server.max.threads"));
        int minThreads = Integer.parseInt(System.getProperty("application.server.min.threads"));
        int idleTimeout = Integer.parseInt(System.getProperty("application.server.idle.timeout"));
        threadPool(maxThreads, minThreads, idleTimeout);
        init();
        awaitInitialization();
        successController.initSuccessController();
        Runtime.getRuntime().addShutdownHook(new ShutdownHookConfig());
        LOG.info("Application started");
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

    private static SuccessController buildSuccessController(ConverterService converterService) {
        return new SuccessControllerImpl(converterService);
    }
}
