package antoninobarila.spring.cloud.gateway.salesforce.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import antoninobarila.spring.cloud.gateway.salesforce.credential.SalesforceCredentialsBasic;
import antoninobarila.spring.cloud.gateway.salesforce.credential.SalesforceCredentialsOAuth;
import antoninobarila.spring.cloud.gateway.salesforce.helper.Constants;
import antoninobarila.spring.cloud.gateway.salesforce.helper.jwt.TokenUtility;

@Service
public class SalesforceOauthSessionProvider {
	
	@Autowired
	TokenUtility tokenService;
	public String getBearer(SalesforceCredentialsBasic basic) throws JsonMappingException, JsonProcessingException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add(Constants.USERNAME, basic.getCredential().getUsername());
		params.add(Constants.PASSWORD, basic.getCredential().getPassword());
		params.add(Constants.CLIENT_SECRET, basic.getCredential().getClientSecret());
		params.add(Constants.CLIENT_ID, basic.getCredential().getClientId());
		params.add(Constants.GRANT_TYPE, Constants.GRANT_TYPE_PASSWORD_VALUE);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(params,
				headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<AuthenticationResponse> response = restTemplate.postForEntity(
				basic.getCredential().getLoginUrl() + "/services/oauth2/token", request, AuthenticationResponse.class);

		return response.getBody().getAccess_token();
	}

	public String getBearer(SalesforceCredentialsOAuth oauth) throws Exception{
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add(Constants.ASSERTION, tokenService.getJwt(oauth));
		params.add(Constants.GRANT_TYPE, Constants.GRANT_TYPE_JWT_BEARER_VALUE);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(params,
				headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<AuthenticationResponse> response = restTemplate.postForEntity(
				oauth.getCredential().getLoginUrl() + "/services/oauth2/token", request, AuthenticationResponse.class);

		return response.getBody().getAccess_token();
	}

}
