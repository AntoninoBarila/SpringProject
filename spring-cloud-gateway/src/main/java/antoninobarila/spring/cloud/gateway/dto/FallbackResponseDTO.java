package antoninobarila.spring.cloud.gateway.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FallbackResponseDTO {

	private Integer msgCode;
	private String msg;
}
