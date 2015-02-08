package test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TempController {

	@RequestMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}
}
