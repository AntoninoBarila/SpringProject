package antoninobarila.spring.cloud.gateway.salesforce.helper.jwt;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import antoninobarila.spring.cloud.gateway.salesforce.credential.SalesforceCredentialsOAuth;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TokenUtility {

	public String getJwt(SalesforceCredentialsOAuth oauth) throws Exception {
		String token = "";
		Algorithm algorithm;
		try {
			KeyStore keystore;
			keystore = KeyStore.getInstance("JKS");

			keystore.load(new FileInputStream("C:\\tmp\\proxyTest.jks"),
					oauth.getCredential().getCertPassword().toCharArray());
			RSAPrivateKey privateKey = (RSAPrivateKey) keystore.getKey(oauth.getCredential().getCertAlias(),
					oauth.getCredential().getCertPassword().toCharArray());
			Certificate certificate = keystore.getCertificate(oauth.getCredential().getCertAlias());
			RSAPublicKey publicKey = (RSAPublicKey) certificate.getPublicKey();
			algorithm = Algorithm.RSA256(publicKey, privateKey);
			token = JWT.create().withIssuer(oauth.getCredential().getClientID())
					.withAudience(oauth.getCredential().getLoginUrl()).withSubject(oauth.getCredential().getUsername())
					.withExpiresAt(new Date(System.currentTimeMillis()))
					.sign(algorithm);
			log.trace("JWT {}", token);

		} catch (Exception e) {
			log.error("Token generation error.", e);
			throw new Exception("Token generation error.", e);
		}
		return token;
	}
	
	
	
	//(System.currentTimeMillis() / 1000) + 1000)

	/*
	 * public String getJwtSalesforce(SalesforceCredentialsOAuth oauth) {
	 * 
	 * String header = "{\"alg\":\"RS256\"}"; String claimTemplate =
	 * "'{'\"iss\": \"{0}\", \"sub\": \"{1}\", \"aud\": \"{2}\", \"exp\": \"{3}\", \"jti\": \"{4\"'}'"
	 * ;
	 * 
	 * try { StringBuffer token = new StringBuffer();
	 * 
	 * //Encode the JWT Header and add it to our string to sign
	 * token.append(Base64.encodeBase64URLSafeString(header.getBytes("UTF-8")));
	 * 
	 * //Separate with a period token.append(".");
	 * 
	 * //Create the JWT Claims Object String[] claimArray = new String[4];
	 * claimArray[0] = oauth.getCredential().getClientID(); claimArray[1] =
	 * oauth.getCredential().getUsername(); claimArray[2] =
	 * "https://login.salesforce.com"; claimArray[3] = Long.toString( (
	 * System.currentTimeMillis()/1000 ) + 300); claimArray[4]=<JTI> MessageFormat
	 * claims; claims = new MessageFormat(claimTemplate); String payload =
	 * claims.format(claimArray);
	 * 
	 * //Add the encoded claims object
	 * token.append(Base64.encodeBase64URLSafeString(payload.getBytes("UTF-8")));
	 * 
	 * //Load the private key from a keystore KeyStore keystore =
	 * KeyStore.getInstance("JKS"); keystore.load(new
	 * FileInputStream("./path/to/keystore.jks"), "keystorepassword".toCharArray());
	 * PrivateKey privateKey = (PrivateKey) keystore.getKey("certalias",
	 * "privatekeypassword".toCharArray());
	 * 
	 * //Sign the JWT Header + "." + JWT Claims Object Signature signature =
	 * Signature.getInstance("SHA256withRSA"); signature.initSign(privateKey);
	 * signature.update(token.toString().getBytes("UTF-8")); String signedPayload =
	 * Base64.encodeBase64URLSafeString(signature.sign());
	 * 
	 * //Separate with a period token.append(".");
	 * 
	 * //Add the encoded signature token.append(signedPayload);
	 * 
	 * System.out.println(token.toString());
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } }
	 */

}
