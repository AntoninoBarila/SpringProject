package antoninobarila.spring.cloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;

import antoninobarila.spring.cloud.gateway.salesforce.interceptor.DestinationInterceptor;

@SpringBootApplication
public class SprinCloudGatewayApplication {
	
	@Bean
	@Scope("singleton")
	public RestTemplate restTemplate(DestinationInterceptor interceptor) {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(interceptor);
	    return restTemplate;
	}

	public static void main(String[] args) {
		SpringApplication.run(SprinCloudGatewayApplication.class, args);
	}

}
