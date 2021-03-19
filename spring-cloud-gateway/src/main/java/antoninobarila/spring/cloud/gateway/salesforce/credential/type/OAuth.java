package antoninobarila.spring.cloud.gateway.salesforce.credential.type;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class OAuth {

	@Value("${credential.oauth.username}")
	private String username;
	@Value("${credential.oauth.password}")
	private String password;

	@Value("${credential.oauth.clientID}")
	private String clientId;
	@Value("${credential.oauth.clientSecret}")
	private String clientSecret;

}
