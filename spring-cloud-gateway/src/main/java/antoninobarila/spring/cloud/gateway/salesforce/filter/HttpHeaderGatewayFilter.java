package antoninobarila.spring.cloud.gateway.salesforce.filter;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import antoninobarila.spring.cloud.gateway.salesforce.credential.SalesforceCredentialsJWT;
import antoninobarila.spring.cloud.gateway.salesforce.helper.Constants;
import antoninobarila.spring.cloud.gateway.salesforce.provider.SalesforceOauthSessionProvider;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class HttpHeaderGatewayFilter extends AbstractGatewayFilterFactory<HttpHeaderGatewayFilter.Config> {


	@Autowired
	SalesforceCredentialsJWT jwt;

	@Autowired
	SalesforceOauthSessionProvider provider;

	public HttpHeaderGatewayFilter() {
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {

		return (exchange, chain) -> {

//			HttpHeaders hh = exchange.getRequest().getHeaders();

			log.info("Body " + exchange.getRequest().getBody());
			log.info("Request " + exchange.getRequest());

			exchange.getRequest().mutate().headers(h -> {
				try {
					log.info("*******************************************************************************");
					log.info("***************************** manageHeader        *****************************");
					log.info("*******************************************************************************");
					manageHeader(h);
					log.info("HttpHeaders :" + h);
					log.info("*******************************************************************************");
					log.info("***************************** manageHeader        *****************************");
					log.info("*******************************************************************************");
				} catch (Exception e) {
					log.error(e.getMessage());
					exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
				}
			});
			return chain.filter(exchange);

		};
	}

	private void manageHeader(HttpHeaders h) throws Exception {

		String user = (h.containsKey(Constants.SALESFORCE_SESSIONID)
				? provider.getUser(jwt.getCredential().getLoginUrl(), h.get(Constants.SALESFORCE_SESSIONID).get(0))
				: jwt.getCredential().getTechnicalUser());
		String bearer = provider.getBearer(user, jwt);
		log.trace("TOKEN {}",bearer);
		h.add(HttpHeaders.AUTHORIZATION, "Bearer ".concat(bearer));

//		if (h.getFirst("x-plt-session-id") == null)
//			h.add("x-plt-session-id", UUID.randomUUID().toString());

		if (h.getFirst("Content-Type") == null)
			h.add("Content-Type", "application/json");

	}

	public static class Config {
		public Config() throws ClientProtocolException {
		}
	}
}
