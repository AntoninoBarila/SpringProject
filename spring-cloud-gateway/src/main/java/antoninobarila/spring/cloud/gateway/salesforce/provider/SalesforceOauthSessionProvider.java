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

import antoninobarila.spring.cloud.gateway.salesforce.credential.SalesforceCredentialsJWT;
import antoninobarila.spring.cloud.gateway.salesforce.helper.Constants;
import antoninobarila.spring.cloud.gateway.salesforce.helper.jwt.TokenUtility;

@Service
public class SalesforceOauthSessionProvider {

	@Autowired
	TokenUtility tokenService;

	public String getBearer(String user, SalesforceCredentialsJWT jwt) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();

		params.add(Constants.ASSERTION, tokenService.getJwt(jwt, user));
		params.add(Constants.GRANT_TYPE, Constants.GRANT_TYPE_JWT_BEARER_VALUE);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(params,
				headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<AuthenticationResponse> response = restTemplate.postForEntity(
				jwt.getCredential().getLoginUrl() + "/services/oauth2/token", request, AuthenticationResponse.class);

		return response.getBody().getAccess_token();
	}

	public String getUser(String url, String sessionId) throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add(Constants.ACCESS_TOKEN, sessionId);
		params.add(Constants.FORMAT, Constants.HEADER_CONTENT_APP_JSON);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(params,
				headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<UserInfoResponse> response = restTemplate.postForEntity(url + "/services/oauth2/userinfo",
				request, UserInfoResponse.class);

		return response.getBody().getPreferred_username();
	}

}
