package antoninobarila.spring.cloud.gateway.salesforce.helper;

public class Constants {
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String CLIENT_ID = "client_id";
	public static final String CLIENT_SECRET = "client_secret";
	public static final String ASSERTION = "assertion";
	public static final String GRANT_TYPE = "grant_type";
	public static final String GRANT_TYPE_SAML_VALUE = "urn:ietf:params:oauth:grant-type:saml2-bearer";
	public static final String GRANT_TYPE_JWT_BEARER_VALUE = "urn:ietf:params:oauth:grant-type:jwt-bearer";
	public static final String GRANT_TYPE_PASSWORD_VALUE = "password";
	public static final String CLIENT_CREDENTIAL = "grant_type=client_credentials";
	public static final String CONNECTION = "Connection";
	public static final String CONNECTION_KEEP_ALIVE = "Keep-Alive";
	public static final String AUTH_SEPARATOR = ":";
	public static final String HEADER_AUTHORIZATION = "Authorization";
	public static final String HEADER_AUTH_TYPE = "Basic ";
	public static final String HEADER_CONTENT_TYPE = "Content-Type";
	public static final String HEADER_CONTENT_APP_JSON = "application/json";
	public static final String HEADER_CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";
	public static final String ACCESS_TOKEN = "access_token";
	public static final String FORMAT = "format";
	public static final String SALESFORCE_SESSIONID = "x-plt-session-id";
	public static final int HTTP_STATUS_200 = 200;
	public static final int HTTP_STATUS_300 = 300;
}
