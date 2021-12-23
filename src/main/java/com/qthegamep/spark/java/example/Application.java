package com.qthegamep.spark.java.example;

import com.qthegamep.spark.java.example.config.ApplicationConfig;
import com.qthegamep.spark.java.example.config.LogConfig;
import com.qthegamep.spark.java.example.exception.ApplicationConfigInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws ApplicationConfigInitializationException {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.init();
        LogConfig logConfig = new LogConfig();
        logConfig.configureLogLevels();
        // TODO
        LOG.info("Hello world!");
    }
}
