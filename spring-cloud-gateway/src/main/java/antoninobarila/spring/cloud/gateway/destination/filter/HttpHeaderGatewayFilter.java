package antoninobarila.spring.cloud.gateway.destination.filter;

import java.io.IOException;
import java.util.UUID;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import antoninobarila.spring.cloud.gateway.destination.credential.OAuthCredentials;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class HttpHeaderGatewayFilter extends AbstractGatewayFilterFactory<HttpHeaderGatewayFilter.Config> {

	
	@Autowired
	private OAuthCredentials oauthCredentials; 
	
	public HttpHeaderGatewayFilter() {
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {
		
        return (exchange, chain) -> {
        	
        	
        	HttpHeaders hh = exchange.getRequest().getHeaders();
        	

    		log.info("Body "+exchange.getRequest().getBody());
    		log.info("Request "+exchange.getRequest());
        	
        	exchange.getRequest().mutate().headers( h -> {
				try {
					log.info("*******************************************************************************");
					log.info("***************************** manageHeader        *****************************");
					log.info("*******************************************************************************");
					manageHeader(h);
					log.info("HttpHeaders :"+ h);
					log.info("*******************************************************************************");
					log.info("***************************** manageHeader        *****************************");
					log.info("*******************************************************************************");
				} catch (IOException e) {
					exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} );
        	return chain.filter(exchange);
			
        };
	}
	

	private void manageHeader(HttpHeaders h) throws IOException {
		
		if( h.getFirst("x-plt-session-id") == null )
			h.add("x-plt-session-id", UUID.randomUUID().toString());
		
		if( h.getFirst("x-plt-user-id") == null )
			h.add("x-plt-user-id", "proxy-mulesoft");

		if( h.getFirst("x-plt-solution-user") == null )
			h.add("x-plt-solution-user", "proxy-mulesoft");
		
		if( h.getFirst("client_id") == null )
			h.add("client_id", oauthCredentials.getCredential().getClientId());
		
		if( h.getFirst("client_secret") == null )
			h.add("client_secret", oauthCredentials.getCredential().getClientSecret());
		
		if( h.getFirst("Content-Type") == null )
			h.add("Content-Type", "application/json");

	}

	public static class Config {
		public Config() throws ClientProtocolException {}
	}
}
