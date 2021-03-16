package antoninobarila.spring.cloud.gateway.credential;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.Data;

@Data
public abstract class Credential<T> {
	
	@Autowired
	protected T credential;
	
}
