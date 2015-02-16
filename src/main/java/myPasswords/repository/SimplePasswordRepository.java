package myPasswords.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import lombok.extern.log4j.Log4j;
import myPasswords.model.PasswordEntry;

/**
 * 
 * @author anselm.ringleben
 *
 */
@Repository
@Log4j
public class SimplePasswordRepository implements PasswordRepository {
	HashMap<Long, PasswordEntry> entries = new HashMap<Long, PasswordEntry>();

	@Override
	public boolean put(PasswordEntry password) {
		log.debug("Method: put called: " + password);

		if (null == password)
			return false;

		entries.put(password.getId(), password);

		return true;
	}

	@Override
	public PasswordEntry get(String id) {
		log.debug("Method: get called: " + id);
		
		if (null == id)
			return null;

		return entries.get(new Long(id));
	}

	@Override
	public boolean delete(String id) {
		log.debug("Method: delete called: " + id);
		
		if (null == id)
			return false;

		entries.remove(new Long(id));

		return true;
	}

	@Override
	public List<PasswordEntry> getList(String category) {
		log.debug("Method: getList called: " + category);

		List<PasswordEntry> results = new ArrayList<PasswordEntry>();

		for (PasswordEntry entry : entries.values()) {
			if (entry.getCategory().equals(category) || category.equals(""))
				results.add(entry);
		}

		return results;
	}

	@Override
	public int size() {
		log.debug("Method: size called");
		
		return entries.size();
	}
}