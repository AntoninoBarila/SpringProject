package antoninobarila.spring.cloud.gateway.config;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.core.registry.EntryAddedEvent;
import io.github.resilience4j.core.registry.EntryRemovedEvent;
import io.github.resilience4j.core.registry.EntryReplacedEvent;
import io.github.resilience4j.core.registry.RegistryEventConsumer;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@Log4j2
public class CircuitBreakerConfig {
	
	@Value("${spring.cloud.gateway.routes[0].id}")
	private String proxyService;
	
	@Bean
	public ReactiveResilience4JCircuitBreakerFactory reactiveResilience4JCircuitBreakerFactory(CircuitBreakerRegistry circuitBreakerRegistry, TimeLimiterRegistry timeLimiterRegistry) {
		ReactiveResilience4JCircuitBreakerFactory reactiveResilience4JCircuitBreakerFactory = new ReactiveResilience4JCircuitBreakerFactory();
		
		reactiveResilience4JCircuitBreakerFactory.configureCircuitBreakerRegistry(circuitBreakerRegistry);
		reactiveResilience4JCircuitBreakerFactory.configure(
				builder -> builder
						.timeLimiterConfig(
								timeLimiterRegistry
								.getConfiguration("internalService")
								.orElse(TimeLimiterConfig.custom().timeoutDuration(Duration.ofMillis(300)).build())
						 )
						.circuitBreakerConfig(
								circuitBreakerRegistry
								.getConfiguration("internalService")
								.orElse(circuitBreakerRegistry.getDefaultConfig())
						),proxyService);
		
		return reactiveResilience4JCircuitBreakerFactory;

	}
	
	@Bean
	public RegistryEventConsumer<CircuitBreaker> myRegistryEventConsumer() {

	    return new RegistryEventConsumer<CircuitBreaker>() {
	        @Override
	        public void onEntryAddedEvent(EntryAddedEvent<CircuitBreaker> entryAddedEvent) {
	        	entryAddedEvent.getAddedEntry().getEventPublisher().onEvent(event -> 
	            	log.info(String.format("onEntryAddedEvent => %s", event.toString())));
	        }

	        @Override
	        public void onEntryRemovedEvent(EntryRemovedEvent<CircuitBreaker> entryRemoveEvent) {
	        	entryRemoveEvent.getRemovedEntry().getEventPublisher().onEvent(event -> 
	        		log.info(String.format("onEntryRemovedEvent => %s", event.toString())));
	        }

	        @Override
	        public void onEntryReplacedEvent(EntryReplacedEvent<CircuitBreaker> entryReplacedEvent) {
	        	entryReplacedEvent.getNewEntry().getEventPublisher().onEvent(event -> 
	        		log.info(String.format("onEntryReplacedEvent => %s", event.toString())));
	        }

	    };
	}

}
