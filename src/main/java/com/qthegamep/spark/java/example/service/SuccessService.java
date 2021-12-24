package com.qthegamep.spark.java.example.service;

import com.qthegamep.spark.java.example.dto.SuccessResponseDTO;

@FunctionalInterface
public interface SuccessService {

    SuccessResponseDTO success(String requestId);
}
