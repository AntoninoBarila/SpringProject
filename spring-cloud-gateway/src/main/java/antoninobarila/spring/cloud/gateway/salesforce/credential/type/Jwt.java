package antoninobarila.spring.cloud.gateway.salesforce.credential.type;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class Jwt {

	@Value("${credential.jwt.loginUrl}")
	private String loginUrl;
	
	@Value("${credential.jwt.audience}")
	private String audience;
	
	@Value("${credential.jwt.issuer}")
	private String issuer;

	@Value("${credential.jwt.technicaluser}")
	private String technicalUser;
	
	@Value("${credential.jwt.certAlias}")
	private String certAlias;

	@Value("${credential.jwt.certPassword}")
	private String certPassword;

}
