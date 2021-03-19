package antoninobarila.spring.cloud.gateway.salesforce.provider;

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

import antoninobarila.spring.cloud.gateway.salesforce.credential.AuthCredentials;

@Service
public class SalesforceOauthSessionProvider {

	public String getBearer(AuthCredentials oauth) throws JsonMappingException, JsonProcessingException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("username", oauth.getCredential().getUsername());
		params.add("password", oauth.getCredential().getPassword());
		params.add("client_secret", oauth.getCredential().getClientSecret());
		params.add("client_id", oauth.getCredential().getClientId());
		params.add("grant_type", "password");

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(params,
				headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<AuthenticationResponse> response = restTemplate.postForEntity("https://test.salesforce.com/services/oauth2/token",
				request, AuthenticationResponse.class);
		
		return response.getBody().getAccess_token();
	}


}
