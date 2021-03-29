package antoninobarila.spring.cloud.gateway.salesforce.credential.type;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class OAuth {

	@Value("${credential.oauth.loginUrl}")
	private String loginUrl;

	@Value("${credential.oauth.username}")
	private String username;

	@Value("${credential.oauth.clientID}")
	private String clientID;
	
	@Value("${credential.oauth.certAlias}")
	private String certAlias;

	@Value("${credential.oauth.certPassword}")
	private String certPassword;

}
