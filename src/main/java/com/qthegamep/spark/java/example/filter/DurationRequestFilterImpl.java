package com.qthegamep.spark.java.example.filter;

import com.qthegamep.spark.java.example.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.before;

public class DurationRequestFilterImpl implements DurationRequestFilter {

    private static final Logger LOG = LoggerFactory.getLogger(DurationRequestFilterImpl.class);

    @Override
    public void initDurationRequestFilter() {
        before((request, response) -> {
            String requestId = request.attribute(Constants.REQUEST_ID_HEADER);
            String startTime = String.valueOf(System.currentTimeMillis());
            LOG.debug("StartTime: {} RequestId: {}", startTime, requestId);
            request.attribute(Constants.START_TIME_HEADER, startTime);
        });
    }
}
