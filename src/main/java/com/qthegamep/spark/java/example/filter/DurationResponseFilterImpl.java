package com.qthegamep.spark.java.example.filter;

import com.qthegamep.spark.java.example.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.after;

public class DurationResponseFilterImpl implements DurationResponseFilter {

    private static final Logger LOG = LoggerFactory.getLogger(DurationResponseFilterImpl.class);

    @Override
    public void initDurationResponseFilter() {
        after((request, response) -> {
            String requestId = request.attribute(Constants.REQUEST_ID_HEADER);
            String startTime = request.attribute(Constants.START_TIME_HEADER);
            long duration = System.currentTimeMillis() - Long.parseLong(startTime);
            LOG.debug("Duration: {} RequestId: {}", duration, requestId);
            response.header(Constants.DURATION_HEADER, String.valueOf(duration));
        });
    }
}
