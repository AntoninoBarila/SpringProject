package antoninobarila.spring.cloud.gateway.salesforce.helper.jwt;

import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import antoninobarila.spring.cloud.gateway.salesforce.credential.SalesforceCredentialsJWT;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TokenUtility {

	private static final String KEYSTORE_JKS = "JKS";
	private static final String KEYSTORE_PATH = "/keystore/proxy.jks";

	public String getJwt(SalesforceCredentialsJWT jwt, String user) throws Exception {
		String token = "";
		Algorithm algorithm;
		try {
			KeyStore keystore;
			keystore = KeyStore.getInstance(KEYSTORE_JKS);

			keystore.load(TokenUtility.class.getResourceAsStream(KEYSTORE_PATH),
					jwt.getCredential().getCertPassword().toCharArray());
			RSAPrivateKey privateKey = (RSAPrivateKey) keystore.getKey(jwt.getCredential().getCertAlias(),
					jwt.getCredential().getCertPassword().toCharArray());
			Certificate certificate = keystore.getCertificate(jwt.getCredential().getCertAlias());
			RSAPublicKey publicKey = (RSAPublicKey) certificate.getPublicKey();
			algorithm = Algorithm.RSA256(publicKey, privateKey);
			token = JWT.create().withIssuer(jwt.getCredential().getIssuer())
					.withAudience(jwt.getCredential().getAudience()).withSubject(user)
					.withExpiresAt(new Date(System.currentTimeMillis() + 3000)).sign(algorithm);
			log.trace("JWT {}", token);

		} catch (Exception e) {
			log.error("Token generation error.", e);
			throw new Exception("Token generation error.", e);
		}
		return token;
	}

}
