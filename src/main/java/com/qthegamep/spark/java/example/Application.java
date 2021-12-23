package com.qthegamep.spark.java.example;

import com.qthegamep.spark.java.example.config.ApplicationConfig;
import com.qthegamep.spark.java.example.config.LogConfig;
import com.qthegamep.spark.java.example.exception.ApplicationConfigInitializationException;
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
        int port = Integer.parseInt(System.getProperty("application.port", "8080"));
        port(port);
        int maxThreads = Integer.parseInt(System.getProperty("application.server.max.threads"));
        int minThreads = Integer.parseInt(System.getProperty("application.server.min.threads"));
        int idleTimeout = Integer.parseInt(System.getProperty("application.server.idle.timeout"));
        threadPool(maxThreads, minThreads, idleTimeout);
        init();
        // TODO
        LOG.info("Hello world!");
    }
}
