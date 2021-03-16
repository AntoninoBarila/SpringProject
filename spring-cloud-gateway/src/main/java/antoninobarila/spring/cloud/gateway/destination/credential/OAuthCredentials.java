package antoninobarila.spring.cloud.gateway.destination.credential;

import org.springframework.stereotype.Component;

import antoninobarila.spring.cloud.gateway.credential.Credential;
import antoninobarila.spring.cloud.gateway.destination.credential.type.OAuth;

@Component
public class OAuthCredentials extends Credential<OAuth> {

}
