package com.dlitv.storage.aspect;


import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class MetricsAspect {

    private final MeterRegistry meterRegistry;

    private Counter kafkaSentCounter;

    @After("execution(* org.springframework.kafka.core.KafkaTemplate.send(..))")
    public void countProcessedTransactions() {
        if (kafkaSentCounter == null) {
            kafkaSentCounter = meterRegistry.counter("kafka.sent.count");
        }
        kafkaSentCounter.increment();
    }

}
