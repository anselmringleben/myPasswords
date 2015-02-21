package myPasswords.rest;

import java.util.List;

import lombok.extern.log4j.Log4j;
import myPasswords.model.PasswordEntry;
import myPasswords.service.PasswordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST interface for password related requests
 * 
 * @author anselm.ringleben
 * 
 */
@RestController
@RequestMapping("/passwords")
@Log4j
public class PasswordController {

	@Autowired
	private PasswordService service;

	@RequestMapping(method = RequestMethod.GET)
	public List<PasswordEntry> getList(
			@RequestParam(value = "category", defaultValue = "") String category) {
		log.debug("Method: getList called: " + category);

		List<PasswordEntry> results = service.getPasswordList(category);
		log.debug("Result: " + results);

		return results;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public PasswordEntry getPassword(@PathVariable String id) {
		log.debug("Method: getPassword called: " + id);

		PasswordEntry result = service.getPasswordEntry(id);
		log.debug("Result: " + result);

		return result;
	}

	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public void postNewPassword(@RequestBody PasswordEntry password) {
		log.debug("Method: postNewPassword called: " + password);

	}
}
