package com.study.springboot.actuator.mertics;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class MyMetrics {
    private final List<String> words = new CopyOnWriteArrayList<>();

    public MyMetrics(MeterRegistry meterRegistry){
        meterRegistry.gaugeCollectionSize("dictionay.size", Tags.empty(),this.words);
    }
}
