package antoninobarila.spring.cloud.gateway.destination.credential.type;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;
@Data
@Component

public class OAuth {

	
	private String jwt;
	
	@Value("${credential.oauth.clientID}")
	private String clientId;
	@Value("${credential.oauth.clientSecret}")
	private String clientSecret;
	
}
