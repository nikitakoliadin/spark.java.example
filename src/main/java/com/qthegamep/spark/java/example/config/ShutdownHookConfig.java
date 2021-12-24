package com.qthegamep.spark.java.example.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;

public class ShutdownHookConfig extends Thread {

    private static final Logger LOG = LoggerFactory.getLogger(ShutdownHookConfig.class);

    @Override
    public void run() {
        shutdownMainServer();
    }

    private void shutdownMainServer() {
        LOG.warn("Shutting down main server...");
        Spark.stop();
        Spark.awaitStop();
        LOG.info("Main server stopped!");
    }
}
