package com.admin.server.adminserver;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import de.codecentric.boot.admin.server.notify.Notifier;
import de.codecentric.boot.admin.server.web.client.InstanceExchangeFilterFunction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

import static de.codecentric.boot.admin.server.web.client.InstanceExchangeFilterFunctions.ATTRIBUTE_ENDPOINT;

@EnableDiscoveryClient
@EnableAdminServer
@EnableScheduling
@Slf4j
@SpringBootApplication
public class AdminServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminServerApplication.class, args);
	}

	@Bean
	public Notifier customNotifier() {
		return event -> {
			log.info("Event {}", event);
			return Mono.empty();
		};
	}

	@Bean
	public InstanceExchangeFilterFunction preventRootLog() {
		return (instance, request, next) -> {
			if (HttpMethod.POST.equals(request.method())) {
				boolean isLogfile = request.attribute(ATTRIBUTE_ENDPOINT).map("loggers"::equals).orElse(false);
				boolean isRoot = request.url().getPath().endsWith("ROOT");
				if (isLogfile && isRoot) {
					return Mono.just(ClientResponse.create(HttpStatus.FORBIDDEN).build());
				}
			}
			return next.exchange(request);
		};
	}
}