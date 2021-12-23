package com.qthegamep.spark.java.example.adapter;

import com.fasterxml.jackson.databind.module.SimpleModule;

import java.util.Date;

public class IsoDateJsonModuleAdapter {

    public SimpleModule buildModule() {
        return new SimpleModule()
                .addSerializer(Date.class, new IsoDateJsonSerializer())
                .addDeserializer(Date.class, new IsoDateJsonDeserializer());
    }
}
