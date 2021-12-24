package com.qthegamep.spark.java.example.filter;

import com.qthegamep.spark.java.example.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.after;

public class ResponseLogFilterImpl implements ResponseLogFilter {

    private static final Logger LOG = LoggerFactory.getLogger(ResponseLogFilterImpl.class);

    @Override
    public void initResponseLogFilter() {
        after((request, response) -> {
            String path = request.url();
            String requestId = request.attribute(Constants.REQUEST_ID_HEADER);
            String clientIp = request.ip();
            String duration = request.attribute(Constants.DURATION_HEADER);
            LOG.info("Request processed. Path: {} RequestId: {} Client IP: {} Duration: {}", path, requestId, clientIp, duration);
        });
    }
}
