package antoninobarila.spring.cloud.gateway.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.extern.log4j.Log4j2;

@Log4j2
//@RestController
//@RequestMapping("/subscriber")
public class EventSubScriberController {
	
	//@Autowired	private RestTemplate restTemplate;
	
	//@Value("${subscriber.endpoint}")
	//private String subscriberScpEndpoint;
	
	//@PostMapping(value = "/redirectEvent", consumes = MediaType.APPLICATION_JSON_VALUE)
    //@ResponseBody
//    public String redirectEvent(@RequestBody String body) throws IOException {
//		HttpEntity<String> e = new HttpEntity<String>(body);
//		log.debug("Body {}",body);
//        return restTemplate.exchange(subscriberScpEndpoint,HttpMethod.POST,e, String.class).getBody();
//    }
}
