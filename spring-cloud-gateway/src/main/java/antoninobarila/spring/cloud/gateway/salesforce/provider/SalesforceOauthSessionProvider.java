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
import antoninobarila.spring.cloud.gateway.salesforce.helper.jwt.TokenUtility;

@Service
public class SalesforceOauthSessionProvider {
	
	@Autowired
	TokenUtility tokenService;

	private static final String JWT_BEARER = "urn:ietf:params:oauth:grant-type:jwt-bearer";
	private static final String PASSWORD = "password";

	public String getBearer(SalesforceCredentialsBasic basic) throws JsonMappingException, JsonProcessingException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("username", basic.getCredential().getUsername());
		params.add("password", basic.getCredential().getPassword());
		params.add("client_secret", basic.getCredential().getClientSecret());
		params.add("client_id", basic.getCredential().getClientId());
		params.add("grant_type", PASSWORD);

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
		params.add("assertion", tokenService.getJwt(oauth));
		params.add("grant_type", JWT_BEARER);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(params,
				headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<AuthenticationResponse> response = restTemplate.postForEntity(
				oauth.getCredential().getLoginUrl() + "/services/oauth2/token", request, AuthenticationResponse.class);

		return response.getBody().getAccess_token();
	}

}
