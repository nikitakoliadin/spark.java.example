package com.qthegamep.spark.java.example.config;

import com.qthegamep.spark.java.example.exception.ApplicationConfigInitializationException;
import com.qthegamep.spark.java.example.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class ApplicationConfig {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationConfig.class);

    public void init() throws ApplicationConfigInitializationException {
        try {
            loadServerIp();
            String configPath = System.getProperty("config.properties");
            if (configPath == null) {
                LOG.info("Load default config properties!");
                loadDefaultProperties();
            } else {
                LOG.info("Load custom config properties! config.properties: {}", configPath);
                loadCustomProperties(configPath);
            }
            loadDockerImageName();
        } catch (Exception e) {
            throw new ApplicationConfigInitializationException(e);
        }
    }

    private void loadServerIp() throws SocketException {
        Optional<String> serverIpOptional = getServerIp();
        if (serverIpOptional.isPresent()) {
            String serverIp = serverIpOptional.get();
            LOG.info("Server IP: {}", serverIp);
            System.setProperty(Constants.SERVER_IP_PROPERTY, serverIp);
        } else {
            LOG.warn("Server IP is not defined!");
        }
    }

    private Optional<String> getServerIp() throws SocketException {
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            LOG.debug("Interface display name: {}", networkInterface.getDisplayName());
            if (networkInterface.isLoopback()
                    || !networkInterface.isUp()
                    || networkInterface.isVirtual()
                    || networkInterface.isPointToPoint()
                    || networkInterface.getDisplayName().contains("docker")) {
                continue;
            }
            Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
            while (inetAddresses.hasMoreElements()) {
                InetAddress inetAddress = inetAddresses.nextElement();
                String hostAddress = inetAddress.getHostAddress();
                LOG.debug("Interface display name: {} host address: {}", networkInterface.getDisplayName(), hostAddress);
                if (Inet4Address.class == inetAddress.getClass()) {
                    return Optional.of(hostAddress);
                }
            }
        }
        return Optional.empty();
    }

    private void loadDefaultProperties() throws IOException {
        Properties applicationProperties = new Properties();
        try (InputStream inputStream = ApplicationConfig.class.getResourceAsStream(Constants.DEFAULT_CONFIG_PROPERTIES_PATH)) {
            applicationProperties.load(inputStream);
            load(applicationProperties);
        }
    }

    private void loadCustomProperties(String path) throws IOException {
        Properties applicationProperties = new Properties();
        try (InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8)) {
            applicationProperties.load(inputStreamReader);
            load(applicationProperties);
        }
    }

    private void load(Properties properties) {
        properties.stringPropertyNames()
                .stream()
                .sorted()
                .forEach(key -> {
                    String value = (String) properties.get(key);
                    LOG.info("SYSTEM PROPERTY: {}={}", key, value);
                    System.setProperty(key, value);
                });
    }

    private void loadDockerImageName() throws IOException {
        String dockerImageNameFilePath = System.getProperty("docker.image.name.file.path");
        File dockerImageNameFile = new File(dockerImageNameFilePath);
        if (dockerImageNameFile.exists() && !dockerImageNameFile.isDirectory()) {
            List<String> lines = Files.readAllLines(Paths.get(dockerImageNameFilePath), StandardCharsets.UTF_8);
            if (lines.isEmpty()) {
                LOG.warn("Docker image name file: {} is empty!", dockerImageNameFilePath);
            } else {
                String dockerImageName = lines.get(0);
                LOG.info("Docker image name: {}", dockerImageName);
                System.setProperty(Constants.DOCKER_IMAGE_NAME_PROPERTY, dockerImageName);
            }
        } else {
            LOG.warn("Docker image name file: {} not exists!", dockerImageNameFilePath);
        }
    }
}
