package com.qthegamep.spark.java.example.service;

import com.qthegamep.spark.java.example.dto.SuccessResponseDTO;

import java.util.Date;

public class SuccessServiceImpl implements SuccessService {

    @Override
    public SuccessResponseDTO success(String requestId) {
        SuccessResponseDTO successResponseDTO = new SuccessResponseDTO();
        successResponseDTO.setNow(new Date());
        successResponseDTO.setRequestId(requestId);
        return successResponseDTO;
    }
}
