package com.qthegamep.spark.java.example.filter;

import com.qthegamep.spark.java.example.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.after;

public class RequestIdResponseFilterImpl implements RequestIdResponseFilter {

    private static final Logger LOG = LoggerFactory.getLogger(RequestIdResponseFilterImpl.class);

    @Override
    public void initRequestIdResponseFilter() {
        after((request, response) -> {
            String requestId = request.attribute(Constants.REQUEST_ID_HEADER);
            LOG.debug("RequestId: {}", requestId);
            response.header(Constants.REQUEST_ID_HEADER, requestId);
        });
    }
}
