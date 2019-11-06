package com.store.config;

import io.jaegertracing.Configuration;
import io.jaegertracing.internal.JaegerTracer;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class JaegerConfig {

    public static final String SERVICE_NAME = "store-service";

    @Bean
    public static JaegerTracer getTracer() {
        Configuration.SamplerConfiguration samplerConfig = Configuration.SamplerConfiguration.fromEnv().withType("probabilistic").withParam(1);



        Configuration.ReporterConfiguration reporterConfig = Configuration.ReporterConfiguration.fromEnv().withLogSpans(true);
        Configuration config = new Configuration(SERVICE_NAME).withSampler(samplerConfig).withReporter(reporterConfig);
        return config.getTracer();
    }

}
