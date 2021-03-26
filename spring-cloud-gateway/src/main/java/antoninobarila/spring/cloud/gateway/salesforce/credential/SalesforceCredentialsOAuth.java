package antoninobarila.spring.cloud.gateway.salesforce.credential;

import org.springframework.stereotype.Component;

import antoninobarila.spring.cloud.gateway.credential.Credential;
import antoninobarila.spring.cloud.gateway.salesforce.credential.type.OAuth;

@Component
public class SalesforceCredentialsOAuth extends Credential<OAuth> {

}
