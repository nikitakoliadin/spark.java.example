package com.qthegamep.spark.java.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.Objects;

public class SuccessResponseDTO {

    @JsonProperty(
            value = "now",
            required = true)
    private Date now;

    public Date getNow() {
        return now;
    }

    public void setNow(Date now) {
        this.now = now;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SuccessResponseDTO that = (SuccessResponseDTO) o;
        return Objects.equals(now, that.now);
    }

    @Override
    public int hashCode() {
        return Objects.hash(now);
    }

    @Override
    public String toString() {
        return "SuccessResponseDTO{" +
                "now=" + now +
                '}';
    }
}
