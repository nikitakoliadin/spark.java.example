package com.qthegamep.spark.java.example.filter;

import com.qthegamep.spark.java.example.service.GenerationService;
import com.qthegamep.spark.java.example.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.before;

public class RequestIdRequestFilterImpl implements RequestIdRequestFilter {

    private static final Logger LOG = LoggerFactory.getLogger(RequestIdRequestFilterImpl.class);

    private GenerationService generationService;

    public RequestIdRequestFilterImpl(GenerationService generationService) {
        this.generationService = generationService;
    }

    @Override
    public void initRequestIdRequestFilter() {
        before((request, response) -> {
            String requestIdHeader = request.headers(Constants.REQUEST_ID_HEADER);
            if (requestIdHeader == null || requestIdHeader.isEmpty()) {
                String xRequestIdHeader = request.headers(Constants.X_REQUEST_ID_HEADER);
                String requestId = xRequestIdHeader == null || xRequestIdHeader.isEmpty()
                        ? generationService.generateUniqueId(10L)
                        : xRequestIdHeader;
                LOG.debug("RequestId: {}", requestId);
                request.attribute(Constants.REQUEST_ID_HEADER, requestId);
            } else {
                LOG.debug("RequestId header: {}", requestIdHeader);
            }
        });
    }
}
