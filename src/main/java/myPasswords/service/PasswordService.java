package myPasswords.service;

import java.util.List;

import lombok.extern.log4j.Log4j;
import myPasswords.data.PasswordRepository;
import myPasswords.model.PasswordEntry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Business service for password related processing
 * 
 * @author anselm.ringleben
 *
 */
@Service
@Log4j
public class PasswordService {

	@Autowired
	private PasswordRepository repository;

	public List<PasswordEntry> getPasswordList(String category) {
		log.debug("Method: getPasswordList called: " + category);

		List<PasswordEntry> results = repository.getList(category);

		return results;
	}
	
	public PasswordEntry getPasswordEntry(String id) {
		log.debug("Method: getPasswordEntry called: " + id);
		
		return repository.get(id);
	}
}
