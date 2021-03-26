package antoninobarila.spring.cloud.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;

import antoninobarila.spring.cloud.gateway.salesforce.interceptor.DestinationInterceptor;

@SpringBootApplication
//@EnableSwagger2
//@OpenAPIDefinition(info = @Info(title = "Proxy Salesforce API", version = "1.0", description = "Documentation Salesforce PROXY API v1.0"))
public class SprinCloudGatewayApplication {

	@Autowired
	RouteDefinitionLocator locator;

	@Bean
	@Scope("singleton")
	public RestTemplate restTemplate(DestinationInterceptor interceptor) {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(interceptor);
		return restTemplate;
	}

//	@Bean
//	public List<GroupedOpenApi> apis() {
//		List<GroupedOpenApi> groups = new ArrayList<>();
//		List<RouteDefinition> definitions = locator.getRouteDefinitions().collectList().block();
//		definitions.stream().filter(routeDefinition -> routeDefinition.getId().matches(".*-service"))
//				.forEach(routeDefinition -> {
//					String name = routeDefinition.getId().replaceAll("-service", "");
//					GroupedOpenApi.builder().pathsToMatch("/" + name + "/**").setGroup(name).build();
//				});
//		return groups;
//	}

	public static void main(String[] args) {
		SpringApplication.run(SprinCloudGatewayApplication.class, args);
	}

}
