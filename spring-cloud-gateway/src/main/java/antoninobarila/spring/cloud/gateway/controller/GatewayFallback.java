package antoninobarila.spring.cloud.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import antoninobarila.spring.cloud.gateway.dto.FallbackResponseDTO;

@RestController
@RequestMapping("/fallback")
public class GatewayFallback {

    @GetMapping("/manage")
    public FallbackResponseDTO getFallBackBackendC() {

        FallbackResponseDTO fallbackResponseDTO = new FallbackResponseDTO();
        fallbackResponseDTO.setMsg("Fallback service");
        fallbackResponseDTO.setMsgCode(501);
        return fallbackResponseDTO;
    }
}
