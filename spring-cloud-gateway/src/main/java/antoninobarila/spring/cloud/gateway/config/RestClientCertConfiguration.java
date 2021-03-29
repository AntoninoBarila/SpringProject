package antoninobarila.spring.cloud.gateway.config;

import javax.net.ssl.SSLContext;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import antoninobarila.spring.cloud.gateway.salesforce.credential.SalesforceCredentialsOAuth;

//@Configuration
public class RestClientCertConfiguration {

	@Autowired
	SalesforceCredentialsOAuth oauth;

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) throws Exception {

		SSLContext sslContext = SSLContextBuilder.create()
				.loadKeyMaterial(ResourceUtils.getFile("classpath:keystore.jks"),
						oauth.getCredential().getCertPassword().toCharArray(),
						oauth.getCredential().getCertPassword().toCharArray())
				.loadTrustMaterial(ResourceUtils.getFile("classpath:truststore.jks"),
						oauth.getCredential().getCertPassword().toCharArray())
				.build();

		HttpClient client = HttpClients.custom().setSSLContext(sslContext).build();

		return builder.requestFactory(new HttpComponentsClientHttpRequestFactory(client).getClass()).build();
	}

}
